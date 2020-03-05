package number.listener;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Service;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

/**
 * Main App Configuration Class
 */
@SpringBootApplication
public class NumberListenerMain extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(NumberListenerMain.class, args);
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

        @Input(SampleStreamBinding.NUMBER_MESSAGE)
        SubscribableChannel receiveSampleMessage();
    }

    /* Stream Consumer */
    @Slf4j
    @EnableBinding(SampleStreamBinding.class)
    @Service
    public static class SampleStreamListener {

        @StreamListener(SampleStreamBinding.NUMBER_MESSAGE)
        public void receiveSampleMessage(NumberMessage numberMessage) {
            log.info("Received message: \n {}", numberMessage);
            assertNotNull("Something were wrong", String.valueOf(numberMessage));
        }
    }
}
