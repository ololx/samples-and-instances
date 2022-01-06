/**
 * Copyright 2021 the project witsml-parsing-instances authors
 * and the original author or authors annotated by {@author}
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.witsml.parsing.instances.javaxb.implementation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @project witsml-parsing-instances
 * @created 2021-12-03 11:35
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
public class MnemonicDetail {

    @JsonProperty("reportedMnemonic")
    String mnemonic;

    @JsonProperty("reportedMnemonicAbbreviation")
    String abbreviation;

    @JsonProperty("measuredValueUOM")
    String unit;

    @JsonProperty("measuredValues")
    List<String> data;
}
