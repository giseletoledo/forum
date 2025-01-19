package com.oracleone.forumhub.infra.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class APIConfigVersion {
    @Value("${api.version}")
    private String apiVersion;

    public String getApiVersion(){
        return apiVersion;
    }
}
