package br.com.systec.opusfinancial.identity.dbupdate.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class IdentityDbUpdateConfig {

    @Bean(initMethod = "migrate")
    public Flyway flywayIdentity(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration/identity")
                .table("flyway_schema_history_identity")
                .baselineOnMigrate(true)
                .load();
    }
}
