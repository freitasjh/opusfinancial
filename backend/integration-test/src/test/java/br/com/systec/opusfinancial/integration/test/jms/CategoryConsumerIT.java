package br.com.systec.opusfinancial.integration.test.jms;

import br.com.systec.opusfinancial.commons.jms.QueueConstants;
import br.com.systec.opusfinancial.integration.test.AbstractIT;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.QueueAttributeName;

import java.util.concurrent.TimeUnit;

public class CategoryConsumerIT extends AbstractIT {

    @Autowired
    private SqsTemplate sqsTemplate; // Para enviar a mensagem

    @Autowired
    private SqsAsyncClient sqsClient; 

    @Test
    void shouldSendToDlqWhenPayloadIsInvalid() {
        // 1. Enviar mensagem inválida
        sqsTemplate.send(QueueConstants.CREATE_CATEGORY, "Lixo");

        // 2. Pegar a URL da DLQ usando o cliente injetado (forma correta)
        String dlqUrl = sqsClient.getQueueUrl(r -> r.queueName(QueueConstants.CREATE_CATEGORY + "-dlq"))
                .join() // Necessário pois o cliente é Async (retorna CompletableFuture)
                .queueUrl();

        // 3. Asserção com Awaitility
        Awaitility.await()
                .atMost(10, TimeUnit.SECONDS)
                .pollInterval(1, TimeUnit.SECONDS)
                .untilAsserted(() -> {

                    var attributes = sqsClient.getQueueAttributes(r -> r
                            .queueUrl(dlqUrl)
                            .attributeNames(QueueAttributeName.APPROXIMATE_NUMBER_OF_MESSAGES)
                    ).join(); // .join() aqui também

                    Integer mensagensNaDlq = Integer.parseInt(attributes.attributes()
                            .get(QueueAttributeName.APPROXIMATE_NUMBER_OF_MESSAGES));

                    Assertions.assertThat(mensagensNaDlq).isEqualTo(1);
                });
    }

}
