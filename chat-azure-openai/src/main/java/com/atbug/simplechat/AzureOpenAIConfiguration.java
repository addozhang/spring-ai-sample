package com.atbug.simplechat;

import com.azure.core.credential.TokenCredential;
import org.springframework.ai.model.azure.openai.autoconfigure.AzureOpenAIClientBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureOpenAIConfiguration {
    @Bean
    public AzureOpenAIClientBuilderCustomizer azureOpenAIClientBuilderCustomizer(TokenCredential tokenCredential) {
        return clientBuilder -> clientBuilder.credential(tokenCredential);
    }
}
