package br.com.systec.opusfinancial.aws.core.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "opus.aws")
public class AwsProperties {

    private String region;
    private Credentials credentials = new Credentials();
    private Endpoints endpoints = new Endpoints();

    public static class Credentials {
        private String accessKey;
        private String secretKey;

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }
    }

    public static class Endpoints {
        private String sqs;
        private String sns;

        public String getSqs() {
            return sqs;
        }

        public void setSqs(String sqs) {
            this.sqs = sqs;
        }

        public String getSns() {
            return sns;
        }

        public void setSns(String sns) {
            this.sns = sns;
        }
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public Endpoints getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(Endpoints endpoints) {
        this.endpoints = endpoints;
    }
}
