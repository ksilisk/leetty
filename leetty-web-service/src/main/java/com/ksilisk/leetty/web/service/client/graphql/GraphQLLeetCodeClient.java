package com.ksilisk.leetty.web.service.client.graphql;

import com.ksilisk.leetty.web.service.client.LeetCodeClient;
import com.netflix.graphql.dgs.client.codegen.BaseSubProjectionNode;
import com.netflix.graphql.dgs.client.codegen.GraphQLQuery;

import java.util.Optional;

public interface GraphQLLeetCodeClient extends LeetCodeClient {
    <T> Optional<T> execute(GraphQLQuery query, BaseSubProjectionNode<?, ?> projection, Class<T> clazz);
}
