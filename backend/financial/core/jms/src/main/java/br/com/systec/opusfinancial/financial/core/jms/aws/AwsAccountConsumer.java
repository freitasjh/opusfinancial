package br.com.systec.opusfinancial.financial.core.jms.aws;


import br.com.systec.opusfinancial.commons.api.exceptions.BaseException;
import br.com.systec.opusfinancial.commons.jms.vo.EventPublisherVO;
import br.com.systec.opusfinancial.commons.jms.vo.factory.ConsumerAbstract;
import br.com.systec.opusfinancial.commons.jms.vo.factory.MessagingConstants;
import br.com.systec.opusfinancial.financial.api.service.AccountService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

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

            log.warn("@@@ [AWS] Criando a conta default para o tenantId {}", eventPublisher.tenantId());

            accountService.createDefaultAccount(eventPublisher.tenantId());
        } catch (Exception e) {
            log.error("[AWS] Erro ao tentar salvar as contas ", e);
            throw new BaseException(e);
        }
    }
}
