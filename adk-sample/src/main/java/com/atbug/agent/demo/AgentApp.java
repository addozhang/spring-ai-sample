package com.atbug.agent.demo;

import com.google.adk.agents.LlmAgent;
import com.google.adk.agents.RunConfig;
import com.google.adk.events.Event;
import com.google.adk.runner.InMemoryRunner;
import com.google.adk.runner.Runner;
import com.google.adk.sessions.Session;
import com.google.genai.types.Content;
import com.google.genai.types.Part;
import io.reactivex.rxjava3.core.Flowable;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.UUID;

@SpringBootApplication
public class AgentApp implements ApplicationRunner {

    private final Runner runner;
    private final String userId;

    public AgentApp(LlmAgent llmAgent) {
        this.runner = new InMemoryRunner(llmAgent, "adk-sample-app");
        userId = UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(AgentApp.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Session session = runner.sessionService().createSession(runner.appName(), this.userId).blockingGet();
        Content userMsg = Content.fromParts(
                Part.fromText("What can you do?"),
                Part.fromText("What's the time now?"),
                Part.fromText("What's the date today?")
        );

        Flowable<Event> events = runner.runAsync(session.userId(), session.id(), userMsg, RunConfig.builder().build());
        events.blockingForEach(event -> System.out.println(event.stringifyContent()));
    }
}
