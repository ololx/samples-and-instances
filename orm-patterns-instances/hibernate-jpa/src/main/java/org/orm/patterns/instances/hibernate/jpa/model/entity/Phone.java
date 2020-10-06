package org.orm.patterns.instances.hibernate.jpa.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * The type Phone.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(
        of = {
                "id"
        },
        doNotUseGetters = true
)
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@Entity(name = "Phone")
@Table(name = "phone")
public class Phone {

    @JsonProperty("id")
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            insertable = false,
            nullable = false
    )
    Long id;

    @Column(
            name = "person_id",
            nullable = false
    )
    Long personId;

    @JsonProperty("number")
    @Column(
            name = "number",
            nullable = false
    )
    String number;
}