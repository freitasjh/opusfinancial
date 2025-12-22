package br.com.systec.opusfinancial.integration.test;

import br.com.systec.opusfinancial.integration.test.identity.web.v1.controller.AccountControllerV1IT;
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
        AccountControllerV1IT.class, LoginControllerV1IT.class
    }
)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class IntegrationSuite {
}
