package org.orm.patterns.instances.hibernate.jdbc.template.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.orm.patterns.instances.hibernate.jdbc.template.service.validation.constraint.PhoneOwnerAge;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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


    @Column(
            name = "person_id",
            nullable = false
    )
    Long personId;

    @JsonProperty("number")
    @Pattern(
            regexp = "^(\\+\\d{1}( )?){1}((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "The Phone number is missing"
    )
    @Column(
            name = "number",
            nullable = false
    )
    String number;
}