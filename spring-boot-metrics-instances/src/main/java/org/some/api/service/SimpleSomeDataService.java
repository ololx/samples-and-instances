package org.some.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.some.api.commons.mapping.MapperAdapter;
import org.some.api.model.detail.SomeDataDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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

    @Override
    public SomeDataDetail create(SomeDataDetail detail) throws MapperAdapter.MappingException {
        log.info(detail.toString());

        return this.mapper.map(detail, SomeDataDetail.class);
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
