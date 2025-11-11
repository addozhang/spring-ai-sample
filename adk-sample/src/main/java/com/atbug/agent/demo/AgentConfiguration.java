package com.atbug.agent.demo;

import com.atbug.agent.demo.tool.HelloTime;
import com.azure.core.credential.TokenCredential;
import com.google.adk.agents.LlmAgent;
import com.google.adk.models.BaseLlm;
import com.google.adk.models.springai.SpringAI;
import com.google.adk.tools.FunctionTool;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.model.azure.openai.autoconfigure.AzureOpenAIClientBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgentConfiguration {
    /**
     * Azure OpenAI Client Builder Customizer for Token Credential
     *
     * @param tokenCredential
     * @return
     */
    @Bean
    public AzureOpenAIClientBuilderCustomizer azureOpenAIClientBuilderCustomizer(TokenCredential tokenCredential) {
        return clientBuilder -> clientBuilder.credential(tokenCredential);
    }

    @Bean
    public BaseLlm springAI(AzureOpenAiChatModel chatModel) {
        return new SpringAI(chatModel);
    }

    @Bean
    public LlmAgent llmAgent(BaseLlm springAI) {
        return LlmAgent.builder()
                .name("hello-time-agent")
                .description("Tell the current time")
                .instruction("""
                        You are a helpful assistant that tells the current date or time.
                        When the user asks for the current date or time, use the 'getCurrentDateAndTime' to get the relative information.
                        Then return the information to the user.
                        """)
                .tools(FunctionTool.create(HelloTime.class, "getCurrentDateAndTime"))
                .model(springAI)
                .build();
    }
}
