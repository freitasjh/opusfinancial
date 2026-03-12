package br.com.systec.opusfinancial.financial.dbupdate.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class CatalogDbUpdateConfig {

    @Bean(initMethod = "migrate")
    public Flyway flywayCatalog(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration/catalog")
                .table("flyway_schema_history_catalog")
                .baselineOnMigrate(true)
                .load();
    }
}
