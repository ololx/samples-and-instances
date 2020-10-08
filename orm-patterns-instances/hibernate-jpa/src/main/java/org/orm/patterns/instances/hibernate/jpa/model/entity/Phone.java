package org.orm.patterns.instances.hibernate.jpa.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * The type Phone.
 */
@Builder
@ToString(
        exclude = "person"
)
@EqualsAndHashCode(
        of = {
                "id"
        },
        doNotUseGetters = true
)
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    Person person;

    @JsonProperty("number")
    @Column(
            name = "number",
            nullable = false
    )
    String number;
}