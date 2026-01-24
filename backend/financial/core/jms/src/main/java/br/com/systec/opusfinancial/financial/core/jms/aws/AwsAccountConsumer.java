package br.com.systec.opusfinancial.financial.core.jms.aws;

import br.com.systec.opusfinancial.commons.exceptions.MessagingException;
import br.com.systec.opusfinancial.commons.messaging.ConsumerAbstract;
import br.com.systec.opusfinancial.commons.messaging.MessagingConstants;
import br.com.systec.opusfinancial.commons.messaging.vo.EventPublisherVO;
import br.com.systec.opusfinancial.financial.api.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("aws")
public class AwsAccountConsumer implements ConsumerAbstract {
    private static final Logger log = LoggerFactory.getLogger(AwsAccountConsumer.class);

    private final AccountService accountService;
    private final ObjectMapper objectMapper;

    public AwsAccountConsumer(AccountService accountService, ObjectMapper objectMapper) {
        this.accountService = accountService;
        this.objectMapper = objectMapper;
    }

    @Override
    @SqsListener(MessagingConstants.CREATE_ACCOUNT)
    public void listen(String message) {
        try {
            if (message == null || message.isEmpty()) {
                throw new MessagingException("Não foi passado o message");
            }

            EventPublisherVO eventPublisher = objectMapper.readValue(message, EventPublisherVO.class);
            accountService.createDefaultAccount(eventPublisher.tenantId());
        } catch (Exception e) {
            log.error("[AWS] Erro ao tentar salvar as contas ", e);
            throw new MessagingException(e);
        }
    }
}
