package org.spring.security.instances.basic.authentication.instance.service;

import org.spring.security.instances.basic.authentication.instance.core.mapping.MapperAdapter;

/**
 * The interface Update service.
 *
 * @param <S>  the type parameter
 * @param <Q>  the type parameter
 * @param <ID> the type parameter
 * @author Alexander A. Kropotin
 * @project basic -authentication
 * @created 2021 -07-26 09:48 <p>
 */
public interface UpdateService<S, Q, ID> {

    /**
     * Update s.
     *
     * @param id     the id
     * @param detail the detail
     * @return the s
     * @throws MapperAdapter.MappingException the mapping exception
     */
    S update(ID id, Q detail) throws MapperAdapter.MappingException;
}
