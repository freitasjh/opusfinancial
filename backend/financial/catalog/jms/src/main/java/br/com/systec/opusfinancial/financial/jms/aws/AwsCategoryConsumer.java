package br.com.systec.opusfinancial.financial.jms.aws;

import br.com.systec.opusfinancial.api.service.CategoryService;
import br.com.systec.opusfinancial.commons.api.exceptions.MessagingException;
import br.com.systec.opusfinancial.commons.jms.vo.EventPublisherVO;
import br.com.systec.opusfinancial.commons.jms.vo.factory.ConsumerAbstract;
import br.com.systec.opusfinancial.commons.jms.vo.factory.MessagingConstants;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@Profile("aws")
public class AwsCategoryConsumer implements ConsumerAbstract {
    private static final Logger log = LoggerFactory.getLogger(AwsCategoryConsumer.class);

    private final CategoryService categoryService;
    private final tools.jackson.databind.ObjectMapper objectMapper;

    public AwsCategoryConsumer(CategoryService categoryService, ObjectMapper objectMapper) {
        this.categoryService = categoryService;
        this.objectMapper = objectMapper;
    }

    @Override
    @SqsListener(MessagingConstants.CREATE_CATEGORY)
    public void listen(String message) {
        try {
            if (message == null || message.isEmpty()) {
                throw new MessagingException("Message cannot be null or empty");
            }
            EventPublisherVO eventPublisherVO = objectMapper.readValue(message, EventPublisherVO.class);

            categoryService.createDefaultCategory(eventPublisherVO.tenantId());
        } catch (Exception e) {
            log.error("[AWS] Erro ao tentar salvar as categorias ", e);
            throw new MessagingException(e);
        }
    }
}
