package com.ksilisk.leetty.web.service.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("leetty")
@NoArgsConstructor
public class LeettyProperties {
    private String leetcodeUrl;
    private String graphqlEndpoint;

    public String prepareGraphQLEndpoint() {
        return leetcodeUrl + graphqlEndpoint;
    }
}
