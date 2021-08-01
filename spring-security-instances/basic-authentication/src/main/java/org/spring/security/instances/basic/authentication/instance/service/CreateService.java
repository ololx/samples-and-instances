package org.spring.security.instances.basic.authentication.instance.service;

import org.spring.security.instances.basic.authentication.instance.core.mapping.MapperAdapter;

/**
 * The interface Create service.
 *
 * @param <S> the type parameter
 * @param <Q> the type parameter
 * @author Alexander A. Kropotin
 * @project basic -authentication
 * @created 2021 -07-26 09:48 <p>
 */
public interface CreateService<S, Q> {

    /**
     * Create s.
     *
     * @param detail the detail
     * @return the s
     * @throws MapperAdapter.MappingException the mapping exception
     */
    S create(Q detail) throws MapperAdapter.MappingException;
}
