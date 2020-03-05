package number.publisher;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * Main App Configuration Class
 */
@SpringBootApplication
public class NumberPublisherMain extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(NumberPublisherMain.class, args);
    }

    @Bean
    public CommandLineRunner demo(SampleStreamPublisher publisher) {
        return (args) -> {
            for(int i = 1; i <= 10; i++) {
                publisher.sendSampleMessage(new NumberMessage(i));

                Thread.sleep(i * 765);
            }
        };
    }

    /** Payload */
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @FieldDefaults(
            level = AccessLevel.PRIVATE
    )
    public static class NumberMessage {

        @JsonProperty("number")
        Integer number;
    }

    /** Stream bingings */
    public interface SampleStreamBinding {

        String NUMBER_MESSAGE = "number_message";

        @Output(SampleStreamBinding.NUMBER_MESSAGE)
        MessageChannel sendSampleMessage();
    }

    /* Stream Producer */
    @Slf4j
    @RequiredArgsConstructor
    @FieldDefaults(
            level = AccessLevel.PRIVATE,
            makeFinal = true
    )
    @EnableBinding(SampleStreamBinding.class)
    @Service
    public static class SampleStreamPublisher {

        SampleStreamBinding outputSource;

        public void sendSampleMessage(NumberMessage numberMessage) {
            log.info("Sending message: \n {}", numberMessage);
            outputSource.sendSampleMessage().send(MessageBuilder.withPayload(numberMessage).build());
        }
    }
}
