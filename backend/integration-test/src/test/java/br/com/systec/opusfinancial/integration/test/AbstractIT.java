package br.com.systec.opusfinancial.integration.test;

import br.com.systec.opusfinancial.OpusfinancialApplication;
import br.com.systec.opusfinancial.commons.jms.QueueConstants;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueAttributesRequest;
import software.amazon.awssdk.services.sqs.model.QueueAttributeName;

import java.util.HashMap;
import java.util.Map;

@ActiveProfiles("test")
@SpringBootTest(
        classes = OpusfinancialApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public abstract class AbstractIT {

    // 1. Container do LocalStack (Mensageria)
    @Container
    static LocalStackContainer localStack = new LocalStackContainer(
            DockerImageName.parse("localstack/localstack:3.2"))
            .withServices(LocalStackContainer.Service.SQS);

    // 2. Container do PostgreSQL (Banco de Dados)
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("opusfinancial")
            .withUsername("sfp_user")
            .withPassword("sfp_pass");

    @BeforeAll
    static void beforeAll() {
        // Inicia containers explicitamente para garantir ordem, se necessário
        // (O @Container já cuida disso, mas o start() manual permite setup pré-Spring)
        localStack.start();
        postgres.start();

        // Configura as filas no LocalStack antes do contexto do Spring subir
        createQueuesWithDlq();
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        // Configurações Dinâmicas do PostgreSQL
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        // Garante o driver correto
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");

        // Configurações Dinâmicas do SQS (LocalStack)
        registry.add("spring.cloud.aws.sqs.endpoint", () -> localStack.getEndpointOverride(LocalStackContainer.Service.SQS).toString());
        registry.add("spring.cloud.aws.region.static", () -> localStack.getRegion());
        registry.add("spring.cloud.aws.credentials.access-key", () -> localStack.getAccessKey());
        registry.add("spring.cloud.aws.credentials.secret-key", () -> localStack.getSecretKey());
    }

    /**
     * Cria as filas programaticamente com configuração de DLQ e Timeout curto.
     * Isso permite testar retries e dead-letter rapidamente.
     */
    private static void createQueuesWithDlq() {
        try (SqsClient sqsClient = SqsClient.builder()
                .endpointOverride(localStack.getEndpointOverride(LocalStackContainer.Service.SQS))
                .region(Region.of(localStack.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(localStack.getAccessKey(), localStack.getSecretKey())
                ))
                .build()) {

            // Defina aqui a fila que você está testando (use a constante do seu projeto)
            String mainQueueName = QueueConstants.CREATE_CATEGORY;
            String dlqName = mainQueueName + "-dlq";

            // A) Criar a DLQ
            sqsClient.createQueue(CreateQueueRequest.builder().queueName(dlqName).build());

            // B) Obter ARN da DLQ
            String dlqUrl = sqsClient.getQueueUrl(r -> r.queueName(dlqName)).queueUrl();
            String dlqArn = sqsClient.getQueueAttributes(GetQueueAttributesRequest.builder()
                    .queueUrl(dlqUrl)
                    .attributeNames(QueueAttributeName.QUEUE_ARN)
                    .build()).attributes().get(QueueAttributeName.QUEUE_ARN);

            // C) Criar Fila Principal com RedrivePolicy + VisibilityTimeout = 1s
            Map<QueueAttributeName, String> attributes = new HashMap<>();
            // 1 segundo para o teste falhar e retentar rápido
            attributes.put(QueueAttributeName.VISIBILITY_TIMEOUT, "1");
            // Política de DLQ: após 3 falhas, move para a DLQ
            attributes.put(QueueAttributeName.REDRIVE_POLICY,
                    String.format("{\"deadLetterTargetArn\":\"%s\",\"maxReceiveCount\":\"3\"}", dlqArn));

            sqsClient.createQueue(CreateQueueRequest.builder()
                    .queueName(mainQueueName)
                    .attributes(attributes)
                    .build());

            System.out.println("### [AbstractIT] Filas criadas: " + mainQueueName + " (c/ DLQ) no Postgres/LocalStack ###");

        } catch (Exception e) {
            throw new RuntimeException("Falha ao configurar filas no LocalStack de teste", e);
        }
    }
}