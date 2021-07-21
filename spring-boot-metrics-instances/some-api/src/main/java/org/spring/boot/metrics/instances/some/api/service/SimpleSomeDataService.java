package org.spring.boot.metrics.instances.some.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.spring.boot.metrics.instances.some.api.commons.mapping.MapperAdapter;
import org.spring.boot.metrics.instances.some.api.model.detail.SomeDataDetail;
import org.spring.boot.metrics.instances.some.api.model.entity.SomeData;
import org.spring.boot.metrics.instances.some.api.model.exception.NonExistentEntityException;
import org.spring.boot.metrics.instances.some.api.repository.SomeDataRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The type Simple some data service.
 *
 * @author Alexander A. Kropotin
 * @project some -api
 * @created 2021 -07-06 17:24 <p>
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
        log.debug("Create the newEntity - {}", newEntity);

        detail.setId(Optional.ofNullable(newEntity.getId()));

        return detail;
    }

    @Override
    public SomeDataDetail read(Integer id) throws MapperAdapter.MappingException {
        Optional<SomeData> storedSomeData = this.getStoredSomeData(id);

        SomeDataDetail detail = this.mapper.map(storedSomeData.get(), SomeDataDetail.class);
        log.debug("Map entity into detail - {}", detail);

        return detail;
    }

    @Override
    public SomeDataDetail update(Integer id, SomeDataDetail detail) throws MapperAdapter.MappingException {
        Optional<SomeData> storedSomeData = this.getStoredSomeData(id);

        SomeData entity = this.mapper.map(detail, storedSomeData.get());
        log.debug("Map detail into entity - {}", detail);

        this.repository.save(entity);
        log.debug("Update the entity - {}", entity);

        detail.setId(Optional.ofNullable(entity.getId()));

        return detail;
    }

    @Override
    public SomeDataDetail delete(Integer id) {
        Optional<SomeData> storedSomeData = this.getStoredSomeData(id);

        this.repository.delete(storedSomeData.get());
        log.debug("Delete the entity - {}", storedSomeData);

        return SomeDataDetail.builder()
                .id(Optional.ofNullable(id))
                .build();
    }

    private Optional<SomeData> getStoredSomeData(Integer id) {
        Optional<SomeData> storedSomeData = this.repository.findById(id);

        if (!storedSomeData.isPresent()) {
            NonExistentEntityException e = new NonExistentEntityException("There is no SomeData with this id - " + id);
            log.error(e.getLocalizedMessage());
            throw e;
        }

        log.debug("Get entity from database - {}", storedSomeData);

        return storedSomeData;
    }
}
