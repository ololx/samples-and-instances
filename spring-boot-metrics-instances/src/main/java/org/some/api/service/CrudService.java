package org.some.api.service;

import org.some.api.commons.mapping.MapperAdapter;
import org.some.api.model.detail.SomeDataDetail;

/**
 * @project some-api
 * @created 2021-07-06 17:59
 * <p>
 * @author Alexander A. Kropotin
 */
public interface CrudService<DETAIL extends SomeDataDetail, ID> {

    DETAIL create(DETAIL detail) throws MapperAdapter.MappingException;

    DETAIL read(ID id);

    DETAIL update(ID id, DETAIL detail);

    DETAIL delete(ID id);
}
