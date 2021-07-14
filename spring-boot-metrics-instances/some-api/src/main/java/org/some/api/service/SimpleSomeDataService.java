package org.some.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.Contracts;
import org.some.api.commons.mapping.MapperAdapter;
import org.some.api.model.detail.SomeDataDetail;
import org.some.api.model.entity.SomeData;
import org.some.api.repository.SomeDataRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @project some-api
 * @created 2021-07-06 17:24
 * <p>
 * @author Alexander A. Kropotin
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Service("SimpleSomeDataService")
public class SimpleSomeDataService implements SomeDataService {

    @Qualifier("ObjectMapperAdapter")
    MapperAdapter mapper;

    @Qualifier("SomeDataRepository")
    SomeDataRepository repository;

    @Override
    public SomeDataDetail create(SomeDataDetail detail) throws MapperAdapter.MappingException {
        SomeData newEntity = this.mapper.map(detail, SomeData.class);
        log.debug("Map detail into newEntity - {}", detail);

        this.repository.save(newEntity);
        log.debug("Create the newEntity\nnewEntity - {}", newEntity);

        detail.setId(Optional.ofNullable(newEntity.getId()));

        return detail;
    }

    @Override
    public SomeDataDetail read(Integer id) throws MapperAdapter.MappingException {
        Optional<SomeData> storedSomeData = this.repository.findById(id);
        Contracts.assertTrue(storedSomeData.isPresent(), "There is no SomeData with this id - {}", id);
        log.debug("Get entity from database - {}", storedSomeData);

        SomeDataDetail detail = this.mapper.map(storedSomeData.get(), SomeDataDetail.class);
        log.debug("Map entity into detail - {}", detail);

        return detail;
    }

    @Override
    public SomeDataDetail update(Integer id, SomeDataDetail detail) throws MapperAdapter.MappingException {
        Optional<SomeData> storedSomeData = this.repository.findById(id);
        Contracts.assertTrue(storedSomeData.isPresent(), "There is no SomeData with this id - {}", id);
        log.debug("Get entity from database - {}", storedSomeData);

        SomeData entity = this.mapper.map(detail, storedSomeData.get());
        log.debug("Map detail into entity - {}", detail);

        this.repository.save(entity);
        log.debug("Update the entity\nentity - {}", entity);

        detail.setId(Optional.ofNullable(entity.getId()));

        return detail;
    }

    @Override
    public SomeDataDetail delete(Integer id) {
        Optional<SomeData> storedSomeData = this.repository.findById(id);
        Contracts.assertTrue(storedSomeData.isPresent(), "There is no SomeData with this id - {}", id);
        log.debug("Get entity from database - {}", storedSomeData);

        this.repository.delete(storedSomeData.get());
        log.debug("Delete the entity\nentity - {}", storedSomeData);

        return SomeDataDetail.builder()
                .id(Optional.ofNullable(id))
                .build();
    }
}
