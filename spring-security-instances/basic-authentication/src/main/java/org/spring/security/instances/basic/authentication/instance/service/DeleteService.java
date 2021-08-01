package org.spring.security.instances.basic.authentication.instance.service;

/**
 * The interface Delete service.
 *
 * @param <S>  the type parameter
 * @param <ID> the type parameter
 * @author Alexander A. Kropotin
 * @project basic -authentication
 * @created 2021 -07-26 09:48 <p>
 */
public interface DeleteService<S, ID> {

    /**
     * Delete s.
     *
     * @param id the id
     * @return the s
     */
    S delete(ID id);
}
