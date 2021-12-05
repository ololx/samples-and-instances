package org.innopolis.university.java.team.spring.boot.custom.message.converting.instances.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.innopolis.university.java.team.spring.boot.custom.message.converting.instances.configuration.JSONTMessageConverter;

/**
 * @project spring-boot-custom-message-converting-instances
 * @created 2021-12-05 18:59
 * <p>
 * @author Alexander A. Kropotin
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@Data
public class TranslatedFizzBuzz implements JSONTMessageConverter.JsonTObject {

    @JsonProperty("физз")
    String fizz;

    @JsonProperty("бузз")
    String buzz;
}
