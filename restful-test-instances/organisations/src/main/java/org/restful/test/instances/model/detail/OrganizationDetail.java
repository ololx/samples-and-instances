package org.restful.test.instances.model.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Optional;

/**
 * @project restful-test-instances
 * @created 11.06.2020 10:07
 * <p>
 * @author Alexander A. Kropotin
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(
        of = {
                "uid",
                "name"
        },
        doNotUseGetters = true
)
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class OrganizationDetail implements Serializable {

    @JsonProperty("uid")
    Optional<Long> uid;

    @JsonProperty("name")
    Optional<String> name;

    @JsonProperty("inn")
    Optional<String> inn;

    @JsonProperty("kpp")
    Optional<String> kpp;

    @JsonProperty("address")
    Optional<String> address;

    public Optional<String> getAddress() {
        return this.address;
    }
}