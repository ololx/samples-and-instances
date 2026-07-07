package org.orm.patterns.instances.hibernate.jdbc.template.model.dao;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.orm.patterns.instances.hibernate.jdbc.template.model.entity.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The type Person dao.
 *
 * @author Alexander A. Kropotin
 * @project orm -patterns-instances
 * @created 12.05.2021 08:03 <p>
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Repository
public class PersonReporistory implements JdbcTemplateRepository<Person, Long> , DeleteRepository {

    /**
     * The Jdbc template.
     */
    JdbcTemplate jdbcTemplate;

    /**
     * The Batch size.
     */
    @Value("${spring.jpa.properties.jdbc.batch_size}")
    @NonFinal
    Integer batchSize;

    /**
     * Save s.
     *
     * @param <S>    the type parameter
     * @param entity the entity
     * @return the s
     */
    @Override
    public <S extends Person> S save(S entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement stmt = connection.prepareStatement(
                            "INSERT INTO person (first_name, last_name, age)\n"
                                    + "VALUES (?, ?, ?)",
                            new String[]{"id"});
                    stmt.setString(1, entity.getFirstName());
                    stmt.setString(2, entity.getLastName());
                    stmt.setLong(3, entity.getAge());

                    return stmt;
                    },
                keyHolder
        );

        entity.setId(keyHolder.getKey().longValue());

        return entity;
    }

    /**
     * Save all list.
     *
     * @param <S>      the type parameter
     * @param entities the entities
     * @return the list
     */
    @Override
    public <S extends Person> Collection<S> saveAll(Collection<S> entities) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO person (first_name, last_name, age)\n"
                        + "VALUES (?, ?, ?)",
                entities,
                batchSize,
                new ParameterizedPreparedStatementSetter<S>() {
                    public void setValues(PreparedStatement ps, S argument) throws SQLException {
                        ps.setString(1, argument.getFirstName());
                        ps.setString(2, argument.getLastName());
                        ps.setInt(3, argument.getAge());
                    }
                });

        return entities;
    }

    /**
     * Find by id optional.
     *
     * @param aLong the a long
     * @return the optional
     */
    @Override
    public Optional<Person> findById(Long aLong) {
        Person result = (Person) jdbcTemplate.queryForObject(
                "SELECT * FROM person WHERE id = ?",
                new BeanPropertyRowMapper(Person.class),
                aLong
        );

        return Optional.ofNullable(result);
    }

    /**
     * Find all iterable.
     *
     * @return the iterable
     */
    @Override
    public List<Person> findAll() {
        List<Person> result = jdbcTemplate.query(
                "SELECT * FROM person",
                new BeanPropertyRowMapper(Person.class)
        );

        if (result == null) return Collections.emptyList();

        return result;
    }

    /**
     * Delete.
     *
     * @param entity the entity
     */
    @Override
    public void delete(Person entity) {
        int result = jdbcTemplate.update(
                "DELETE FROM person WHERE id = ?",
                entity.getId()
        );
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM person");
    }
}
