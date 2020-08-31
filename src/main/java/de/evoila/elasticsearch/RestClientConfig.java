package de.evoila.elasticsearch;

import io.pivotal.cfenv.core.CfEnv;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {

    private static final Log logger = LogFactory.getLog(RestClientConfig.class);

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {

        CfEnv cfEnv = new CfEnv();
        String elasticsearchHost = cfEnv.findCredentialsByTag("Best Performance Elasticsearch").getHost();
        String elasticsearchUsername  = cfEnv.findCredentialsByTag("Best Performance Elasticsearch").getUsername();
        String elasticsearchPassword = cfEnv.findCredentialsByTag("Best Performance Elasticsearch").getPassword();
        logger.info("Connecting to osb-elasticsearch @ " + elasticsearchHost);
        logger.info("Using VCAP Credentials: username " + elasticsearchUsername + ", password: " + elasticsearchPassword);

        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(elasticsearchHost)
                .usingSsl()
                .withBasicAuth(elasticsearchUsername, elasticsearchPassword)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }
}