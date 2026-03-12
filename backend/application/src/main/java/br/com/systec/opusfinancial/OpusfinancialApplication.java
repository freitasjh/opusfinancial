package br.com.systec.opusfinancial;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@EnableJpaRepositories
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@SecurityScheme(name = "Authorization", scheme = "bearer",
		type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER)
public class OpusfinancialApplication {

	static void main(String[] args) {
		SpringApplication.run(OpusfinancialApplication.class, args);
	}

}
