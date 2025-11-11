package com.atbug.simplechat;

import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@SpringBootApplication
@RestController
public class SimpleChat {

    private final AzureOpenAiChatModel chatModel;

    public SimpleChat(AzureOpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleChat.class, args);
    }

    @GetMapping("/ai")
    public Flux<String> generation(@RequestParam String userInput) {
        return this.chatModel.stream(userInput);
    }

}
