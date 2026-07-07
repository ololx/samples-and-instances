package org.restful.test.instances.model.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

/**
 * @project restful-test-instances
 * @created 15.05.2021 10:31
 * <p>
 * @author Alexander A. Kropotin
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(
        of = {
                "employUid"
        },
        doNotUseGetters = true
)
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class EmployDetail {

    @JsonProperty("employ_uid")
    Optional<Long> employUid;

    @JsonProperty("organization_uid")
    Optional<Long> organizationUid;

    @JsonProperty("position_uid")
    Optional<Short> positionUid;

    @JsonProperty("full_name")
    Optional<String> fullName;
}
