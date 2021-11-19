package org.witsml.parsing.instances.javaxb.implementation.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
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
public class MnemonicDetail {

    String mnemonic;

    List<List<String>> values;
}
