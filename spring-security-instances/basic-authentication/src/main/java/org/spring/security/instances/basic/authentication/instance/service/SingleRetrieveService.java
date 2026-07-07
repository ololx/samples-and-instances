package org.spring.security.instances.basic.authentication.instance.service;

import org.spring.security.instances.basic.authentication.instance.core.mapping.MapperAdapter;

/**
 * The interface Single retrieve service.
 *
 * @param <S>  the type parameter
 * @param <ID> the type parameter
 * @author Alexander A. Kropotin
 * @project basic -authentication
 * @created 2021 -07-26 09:49 <p>
 */
public interface SingleRetrieveService<S, ID> {

    /**
     * Retrieve s.
     *
     * @param id the id
     * @return the s
     * @throws MapperAdapter.MappingException the mapping exception
     */
    S retrieve(ID id) throws MapperAdapter.MappingException;
}
