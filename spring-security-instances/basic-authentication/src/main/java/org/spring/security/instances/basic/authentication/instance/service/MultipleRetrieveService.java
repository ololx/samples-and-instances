package org.spring.security.instances.basic.authentication.instance.service;

import org.spring.security.instances.basic.authentication.instance.core.mapping.MapperAdapter;

import java.util.Collection;

/**
 * The interface Multiple retrieve service.
 *
 * @param <S> the type parameter
 * @author Alexander A. Kropotin
 * @project basic -authentication
 * @created 2021 -07-26 09:49 <p>
 */
public interface MultipleRetrieveService<S> {

    /**
     * Retrieve s.
     *
     * @return the s
     * @throws MapperAdapter.MappingException the mapping exception
     */
    Collection<S> retrieve() throws MapperAdapter.MappingException;
}
