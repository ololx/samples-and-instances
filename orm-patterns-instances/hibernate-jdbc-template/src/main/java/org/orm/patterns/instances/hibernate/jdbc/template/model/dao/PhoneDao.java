package org.orm.patterns.instances.hibernate.jdbc.template.model.dao;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.apachecommons.CommonsLog;
import org.orm.patterns.instances.hibernate.jdbc.template.model.entity.Person;
import org.orm.patterns.instances.hibernate.jdbc.template.model.entity.Phone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The type Phone dao.
 *
 * @author Alexander A. Kropotin
 * @project orm -patterns-instances
 * @created 12.05.2021 08:03 <p>
 */
@CommonsLog
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Service
public class PhoneDao implements DataAccess<Phone, Long> {

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
    public <S extends Phone> S save(S entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement stmt = connection.prepareStatement(
                            "INSERT INTO phone (person_id, number)\n"
                                    + "VALUES (?, ?)",
                            new String[]{"id"});
                    stmt.setLong(1, entity.getPersonId());
                    stmt.setString(2, entity.getNumber());

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
    public <S extends Phone> Collection<S> saveAll(Collection<S> entities) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO phone (person_id, number)\\n\"\n" +
                        "                        + \"VALUES (?, ?)",
                entities,
                batchSize,
                new ParameterizedPreparedStatementSetter<S>() {
                    public void setValues(PreparedStatement ps, S argument) throws SQLException {
                        ps.setLong(1, argument.getPersonId());
                        ps.setString(2, argument.getNumber());
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
    public Optional<Phone> findById(Long aLong) {
        Phone result = (Phone) jdbcTemplate.queryForObject(
                "SELECT * FROM phone WHERE id = ?",
                new BeanPropertyRowMapper(Phone.class),
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
    public List<Phone> findAll() {
        List<Phone> result = jdbcTemplate.query(
                "SELECT * FROM phone",
                new BeanPropertyRowMapper(Phone.class)
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
    public void delete(Phone entity) {
        int result = jdbcTemplate.update(
                "SELECT * FROM phone WHERE id = ?",
                new ParameterizedPreparedStatementSetter<Phone>() {
                    public void setValues(PreparedStatement ps, Phone argument) throws SQLException {
                        ps.setLong(1, argument.getId());
                    }
                }
        );
    }
}
