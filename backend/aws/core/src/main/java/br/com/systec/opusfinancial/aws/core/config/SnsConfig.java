package br.com.systec.opusfinancial.aws.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

import java.net.URI;

@Configuration
@Profile("aws")
public class SnsConfig {
    private final AwsProperties awsProperties;

    public SnsConfig(AwsProperties awsProperties) {
        this.awsProperties = awsProperties;
    }

    @Bean
    public SnsClient snsClient() {
        return SnsClient.builder()
                .region(Region.of(awsProperties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                awsProperties.getCredentials().getAccessKey(),
                                awsProperties.getCredentials().getSecretKey()
                        )
                ))
                .endpointOverride(URI.create(awsProperties.getEndpoints().getSns()))
                .build();
    }
}
