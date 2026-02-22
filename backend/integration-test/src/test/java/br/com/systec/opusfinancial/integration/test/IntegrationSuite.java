package br.com.systec.opusfinancial.integration.test;

import br.com.systec.opusfinancial.integration.test.financial.catalog.web.v1.controller.BankControllerV1IT;
import br.com.systec.opusfinancial.integration.test.financial.catalog.web.v1.controller.CategoryControllerV1IT;
import br.com.systec.opusfinancial.integration.test.financial.core.web.v1.controller.AccountControllerV1IT;
import br.com.systec.opusfinancial.integration.test.financial.core.web.v1.controller.IncomingFinancialControllerV1IT;
import br.com.systec.opusfinancial.integration.test.identity.web.v1.controller.UserAccountControllerV1IT;
import br.com.systec.opusfinancial.integration.test.jms.AwsCategoryConsumerIT;
import br.com.systec.opusfinancial.integration.test.security.web.v1.controller.LoginControllerV1IT;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Suite
@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SelectClasses({
        AwsCategoryConsumerIT.class, UserAccountControllerV1IT.class,
        LoginControllerV1IT.class, CategoryControllerV1IT.class,
        BankControllerV1IT.class, AccountControllerV1IT.class,
        IncomingFinancialControllerV1IT.class
    }
)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class IntegrationSuite {
}
