package com.atbug.simplechat;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.DefaultChatClientBuilder;
import org.springframework.ai.chat.client.observation.DefaultChatClientObservationConvention;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import reactor.core.publisher.Flux;

public class OllamaChat {

    public static void main(String[] args) throws InterruptedException {
        OllamaApi ollamaApi = OllamaApi.builder()
                .baseUrl("http://localhost:11434")
                .build();

        OllamaChatModel chatModel = OllamaChatModel.builder()
                .ollamaApi(ollamaApi)
                .defaultOptions(OllamaChatOptions.builder().model("llama3.1").build())
                .build();

        ChatClient.Builder builder = new DefaultChatClientBuilder(chatModel, ObservationRegistry.NOOP, new DefaultChatClientObservationConvention());
        ChatClient chatClient = builder.build();

        String resp = chatClient.prompt()
                .user("Tell me a joke about mango")
                .call()
                .content();
        System.out.println(resp);

        Flux<String> fluxResp = chatClient.prompt()
                .user("Tell me a joke about mango")
                .stream()
                .content();

        fluxResp.doOnNext(System.out::print).blockLast();

    }
}
