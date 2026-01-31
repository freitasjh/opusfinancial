package br.com.systec.opusfinancial.aws.core.publisher;

import br.com.systec.opusfinancial.commons.exceptions.MessagingException;
import br.com.systec.opusfinancial.commons.messaging.EventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.SnsException;

@Component
@Profile("aws")
public class SnsEventPublisher implements EventPublisher {
    private static final Logger log = LoggerFactory.getLogger(SnsEventPublisher.class);

    private final SnsClient snsClient;
    private final ObjectMapper objectMapper;

    @Value("${opus.aws.sns.arn-prefix:arn:aws:sns:us-east-1:000000000000:}")
    private String arnPrefix;

    public SnsEventPublisher(SnsClient snsClient, ObjectMapper objectMapper) {
        this.snsClient = snsClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publish(String topicName, Object event) {
        try {
            String messageBody = objectMapper.writeValueAsString(event);
            String topicArn = topicName.startsWith("arn:") ? topicName : arnPrefix + topicName;

            log.info("Publicando evento no SNS [Topic: {}]", topicArn);

            PublishRequest request = PublishRequest.builder()
                    .topicArn(topicArn)
                    .message(messageBody)
                    .build();

            snsClient.publish(request);

            log.debug("Evento publicado com sucesso.");

        } catch (JsonProcessingException e) {
            log.error("Erro ao serializar evento para JSON", e);
            throw new MessagingException("Falha na serialização do evento", e);
        } catch (SnsException e) {
            log.error("Erro ao comunicar com AWS SNS", e);
            throw new MessagingException("Falha no envio para o SNS", e);
        }
    }
}
