package br.com.systec.opusfinancial.integration.test;

import br.com.systec.opusfinancial.OpusfinancialApplication;
import br.com.systec.opusfinancial.commons.messaging.MessagingConstants;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueAttributesRequest;
import software.amazon.awssdk.services.sqs.model.QueueAttributeName;

import java.util.HashMap;
import java.util.Map;

@ActiveProfiles("aws")
@SpringBootTest(classes = OpusfinancialApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIT {

    private static final Logger log = LoggerFactory.getLogger(AbstractIT.class);

    // NOME DO TÓPICO (Deve ser igual ao usado no UserAccountServiceImpl)
    private static final String USER_EVENTS_TOPIC_NAME = "user-events-topic";

    @Container
    static LocalStackContainer localStack = new LocalStackContainer(DockerImageName.parse("localstack/localstack:3.2"))
            .withServices(LocalStackContainer.Service.SQS, LocalStackContainer.Service.SNS);

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("opusfinancial")
            .withUsername("sfp_user")
            .withPassword("sfp_pass");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
        localStack.start();
        configureMessagingInfrastructure();
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        // Database
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");

        // AWS - Configuração OPUS
        registry.add("opus.aws.region", localStack::getRegion);
        registry.add("opus.aws.credentials.access-key", localStack::getAccessKey);
        registry.add("opus.aws.credentials.secret-key", localStack::getSecretKey);
        registry.add("opus.aws.endpoints.sqs", () -> localStack.getEndpointOverride(LocalStackContainer.Service.SQS).toString());
        // Mapeamento do SNS (Vital para o Publisher funcionar)
        registry.add("opus.aws.endpoints.sns", () -> localStack.getEndpointOverride(LocalStackContainer.Service.SNS).toString());
    }

    private static void configureMessagingInfrastructure() {
        log.info("### Configurando Infraestrutura de Mensageria (SNS + SQS) no LocalStack... ###");

        // Criar Clientes Temporários para o Setup
        try (
                SqsClient sqsClient = SqsClient.builder()
                        .endpointOverride(localStack.getEndpointOverride(LocalStackContainer.Service.SQS))
                        .region(Region.of(localStack.getRegion()))
                        .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(localStack.getAccessKey(), localStack.getSecretKey())))
                        .build();

                SnsClient snsClient = SnsClient.builder()
                        .endpointOverride(localStack.getEndpointOverride(LocalStackContainer.Service.SNS))
                        .region(Region.of(localStack.getRegion()))
                        .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(localStack.getAccessKey(), localStack.getSecretKey())))
                        .build()
        ) {
            // -----------------------------------------------------------------------
            // 1. CRIAR O TÓPICO SNS
            // -----------------------------------------------------------------------
            log.info("Criando Tópico SNS: {}", USER_EVENTS_TOPIC_NAME);
            String topicArn = snsClient.createTopic(CreateTopicRequest.builder()
                    .name(USER_EVENTS_TOPIC_NAME)
                    .build()).topicArn();

            // -----------------------------------------------------------------------
            // 2. CRIAR AS FILAS SQS (Com DLQ)
            // -----------------------------------------------------------------------
            createQueueAndSubscribeInTopic(MessagingConstants.CREATE_CATEGORY, topicArn, snsClient, sqsClient);
            createQueueAndSubscribeInTopic(MessagingConstants.CREATE_ACCOUNT, topicArn, snsClient, sqsClient);

            log.info("### Infraestrutura de Teste Pronta! ###");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao configurar SNS/SQS no LocalStack", e);
        }
    }

    private static void createQueueAndSubscribeInTopic(String queueName, String topicArn, SnsClient snsClient, SqsClient sqsClient) {
        String dlqName = queueName + "-dlq";

        // Criar DLQ
        sqsClient.createQueue(CreateQueueRequest.builder().queueName(dlqName).build());
        String dlqUrl = sqsClient.getQueueUrl(r -> r.queueName(dlqName)).queueUrl();
        String dlqArn = sqsClient.getQueueAttributes(GetQueueAttributesRequest.builder().queueUrl(dlqUrl).attributeNames(QueueAttributeName.QUEUE_ARN).build())
                .attributes().get(QueueAttributeName.QUEUE_ARN);

        // Criar Fila Principal
        Map<QueueAttributeName, String> attributes = new HashMap<>();
        attributes.put(QueueAttributeName.VISIBILITY_TIMEOUT, "1");
        attributes.put(QueueAttributeName.REDRIVE_POLICY,
                String.format("{\"deadLetterTargetArn\":\"%s\",\"maxReceiveCount\":\"1\"}", dlqArn));

        log.info("Criando Fila SQS: {}", queueName);
        sqsClient.createQueue(CreateQueueRequest.builder()
                .queueName(queueName)
                .attributes(attributes)
                .build());

        String queueUrl = sqsClient.getQueueUrl(r -> r.queueName(queueName)).queueUrl();
        String queueArn = sqsClient.getQueueAttributes(GetQueueAttributesRequest.builder().queueUrl(queueUrl).attributeNames(QueueAttributeName.QUEUE_ARN).build())
                .attributes().get(QueueAttributeName.QUEUE_ARN);

        // -----------------------------------------------------------------------
        // 3. FAZER A ASSINATURA (SUBSCRIPTION) - O PULO DO GATO
        // -----------------------------------------------------------------------
        // Liga o Tópico (SNS) à Fila (SQS)
        log.info("Realizando Subscribe: Tópico [{}] -> Fila [{}]", topicArn, queueArn);

        SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                .topicArn(topicArn)
                .protocol("sqs")
                .endpoint(queueArn)
                .attributes(Map.of("RawMessageDelivery", "true")) // CRÍTICO: Garante que o JSON chegue limpo, sem envelope do SNS
                .build();

        snsClient.subscribe(subscribeRequest);
    }
}