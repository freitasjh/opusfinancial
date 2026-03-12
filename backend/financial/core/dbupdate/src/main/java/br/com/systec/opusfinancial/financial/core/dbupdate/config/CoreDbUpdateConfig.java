package br.com.systec.opusfinancial.financial.core.dbupdate.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class CoreDbUpdateConfig {

    @Bean(initMethod = "migrate")
    public Flyway flywayCore(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration/core")
                .table("flyway_schema_history_core")
                .baselineOnMigrate(true)
                .load();
    }
}
