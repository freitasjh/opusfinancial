package br.com.systec.opusfinancial.tenant.dbupdate.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class TenantDbUpdateConfig {

    @Primary
    @Bean(initMethod = "migrate")
    public Flyway flywayTenant(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration/tenant")
                .table("flyway_schema_history_tenant")
                .baselineOnMigrate(true)
                .load();
    }
}
