package org.witsml.parsing.instances.javaxb.implementation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @project witsml-parsing-instances
 * @created 2021-11-19 07:49
 * <p>
 * @author Alexander A. Kropotin
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
public class LogDetail {

    @JsonRawValue
    @JsonProperty("meta")
    String meta;

    @JsonProperty("dataset")
    List<DataSet> dataSet;
}
