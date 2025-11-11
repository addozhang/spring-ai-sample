package com.atbug.simplechat;

import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.azure.openai.AzureOpenAiChatOptions;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import reactor.core.publisher.Flux;

public class StaticClient {

    public static void main(String[] args) {
        String clientId = System.getenv("AZURE_CLIENT_ID");
        String clientSecret = System.getenv("AZURE_CLIENT_SECRET");
        String tenantId = System.getenv("AZURE_TENANT_ID");
        String endpoint = System.getenv("AZURE_OPENAI_ENDPOINT");
        String deploymentName = System.getenv("AZURE_OPENAI_DEPLOYMENT_NAME");
        ClientSecretCredential tokenCredential = new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .build();

        OpenAIClientBuilder openAIClientBuilder = new OpenAIClientBuilder()
                .endpoint(endpoint)
                .credential(tokenCredential);

        AzureOpenAiChatOptions azureOpenAiChatOptions = AzureOpenAiChatOptions.builder()
                .deploymentName(deploymentName)
                .build();

        AzureOpenAiChatModel chatModel = AzureOpenAiChatModel.builder()
                .openAIClientBuilder(openAIClientBuilder)
                .defaultOptions(azureOpenAiChatOptions)
                .build();
        Flux<String> fluxResp = chatModel
                .stream(new UserMessage("Tell me a joke about Mango"), new SystemMessage("Reply in Chinese."));

        fluxResp.doOnNext(System.out::print).blockLast();
    }
}
