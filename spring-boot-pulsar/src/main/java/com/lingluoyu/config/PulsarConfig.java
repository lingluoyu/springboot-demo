package com.lingluoyu.config;

import com.lingluoyu.model.User;
import org.apache.pulsar.client.api.Schema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.pulsar.core.DefaultSchemaResolver;
import org.springframework.pulsar.core.SchemaResolver;

@Configuration
public class PulsarConfig {

    @Bean
    public SchemaResolver.SchemaResolverCustomizer<DefaultSchemaResolver> schemaResolverCustomizer() {
        return (schemaResolver) -> {
            schemaResolver.addCustomSchemaMapping(User.class, Schema.JSON(User.class));
        };
    }
}
