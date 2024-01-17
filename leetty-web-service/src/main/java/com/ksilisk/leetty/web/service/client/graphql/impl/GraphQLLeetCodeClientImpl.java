package com.ksilisk.leetty.web.service.client.graphql.impl;

import com.ksilisk.leetty.web.service.client.graphql.GraphQLLeetCodeClient;
import com.netflix.graphql.dgs.client.GraphQLClient;
import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.codegen.BaseSubProjectionNode;
import com.netflix.graphql.dgs.client.codegen.GraphQLQuery;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GraphQLLeetCodeClientImpl implements GraphQLLeetCodeClient {
    private final GraphQLClient client;

    @Override
    public <T> T execute(GraphQLQuery query, BaseSubProjectionNode<?, ?> projection, Class<T> clazz) {
        GraphQLQueryRequest request = new GraphQLQueryRequest(query, projection);
        GraphQLResponse response = client.executeQuery(request.serialize());
        return response.dataAsObject(clazz);
    }
}
