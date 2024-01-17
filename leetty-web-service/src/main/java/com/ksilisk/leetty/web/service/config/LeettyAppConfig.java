package com.ksilisk.leetty.web.service.config;

import com.ksilisk.leetty.web.service.client.graphql.GraphQLLeetCodeClient;
import com.ksilisk.leetty.web.service.client.graphql.impl.GraphQLLeetCodeClientImpl;
import com.netflix.graphql.dgs.client.RestClientGraphQLClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class LeettyAppConfig {
    @Bean
    public GraphQLLeetCodeClient graphQLLeetCodeClient(LeettyProperties leettyProperties) {
        RestClient restClient = RestClient.create(leettyProperties.prepareGraphQLEndpoint());
        RestClientGraphQLClient graphQLClient = new RestClientGraphQLClient(restClient);
        return new GraphQLLeetCodeClientImpl(graphQLClient);
    }
}
