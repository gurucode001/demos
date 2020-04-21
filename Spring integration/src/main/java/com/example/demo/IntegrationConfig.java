package com.example.demo;

import java.io.File;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowBuilder;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.messaging.MessageHandler;

@Configuration
public class IntegrationConfig {

    @Autowired private Transformer transformer;

    @Bean
    public IntegrationFlow integrateionFlow() {
        System.out.println("1. ################################### integrateionFlow start");
        return IntegrationFlows.from(fileReader(),
                (Consumer<SourcePollingChannelAdapterSpec>) sourcePollingChannelAdapterSpec -> sourcePollingChannelAdapterSpec
                        .poller(Pollers.fixedDelay(500)))
                .transform(transformer, "transform").handle(fileWriter()).get();
    }

    @Bean
    public MessageHandler fileWriter() {
        System.out.println("3. ################################### fileWriter start");
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("destination"));
        handler.setExpectReply(false);
        return handler;
    }

    @Bean
    public FileReadingMessageSource fileReader() {
        System.out.println("2. ################################### fileReader start");
        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setDirectory(new File("source"));
        return source;
    }

}
