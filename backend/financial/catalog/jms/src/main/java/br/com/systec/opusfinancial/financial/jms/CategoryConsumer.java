package br.com.systec.opusfinancial.financial.jms;

import br.com.systec.opusfinancial.api.service.CategoryService;
import br.com.systec.opusfinancial.api.vo.CategoryVO;
import br.com.systec.opusfinancial.commons.jms.QueueConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.UUID;

@Component
public class CategoryConsumer {
    private static final Logger log = LoggerFactory.getLogger(CategoryConsumer.class);
    private final CategoryService categoryService;

    public CategoryConsumer(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @SqsListener(QueueConstants.CREATE_CATEGORY)
    public void listen(String message) {
        try {
            if (message == null || message.isEmpty()) {
                throw new RuntimeException("Message cannot be null or empty");
            }

            UUID tenantId = UUID.fromString(message);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            try (InputStream inputStream = classLoader.getResourceAsStream("json/category-insert.json")) {
                ListCategory listOfCategories = objectMapper.readValue(inputStream, ListCategory.class);
                for (CategoryVO category : listOfCategories.getCategories()) {
                    category.setTenantId(tenantId);
                    categoryService.create(category);
                }
            }
        } catch (Exception e) {
            log.error("Erro ao tentar salvar as categorias", e);
            throw new RuntimeException("Erro ao tentar salvar as categorias", e);
        }
    }
}
