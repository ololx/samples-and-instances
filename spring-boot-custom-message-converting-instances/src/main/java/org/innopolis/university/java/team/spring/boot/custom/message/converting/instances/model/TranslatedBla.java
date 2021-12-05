package org.innopolis.university.java.team.spring.boot.custom.message.converting.instances.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.models.auth.In;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.innopolis.university.java.team.spring.boot.custom.message.converting.instances.configuration.JSONTMessageConverter;

import java.util.PrimitiveIterator;

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
public class TranslatedBla implements JSONTMessageConverter.JsonTObject {

    @JsonProperty("бла")
    String bla;
}
