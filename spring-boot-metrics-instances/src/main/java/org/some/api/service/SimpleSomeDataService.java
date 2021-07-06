package org.some.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
        log.info("Receive create entity request\ndetail - {}", detail);

        SomeData entity = this.mapper.map(detail, SomeData.class);
        this.repository.save(entity);
        log.info("Create the entity\nentity - {}", entity);

        detail.setId(Optional.ofNullable(entity.getId()));
        log.info("Send created entity detail like response\ndetail - {}", detail);

        return detail;
    }

    @Override
    public SomeDataDetail read(Integer integer) {
        return null;
    }

    @Override
    public SomeDataDetail update(Integer integer, SomeDataDetail detail) {
        return null;
    }

    @Override
    public SomeDataDetail delete(Integer integer) {
        return null;
    }
}
