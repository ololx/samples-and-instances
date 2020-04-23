package org.restful.test.instances.db.local.test.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@Entity(name = "Organization")
@Table(name = "organization")
public class Organization implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "id",
            insertable = false,
            nullable = false
    )
    Long id;

    @Column(name = "inn")
    String inn;

    @Column(name = "kpp")
    String kpp;

    @Column(name = "address")
    String address;
}