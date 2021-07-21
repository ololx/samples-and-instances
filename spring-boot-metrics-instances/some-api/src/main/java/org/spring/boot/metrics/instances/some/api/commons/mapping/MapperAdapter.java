package org.spring.boot.metrics.instances.some.api.commons.mapping;

import java.util.Collection;
import java.util.List;

/**
 * The interface Model mapper.
 *
 * @author Alexander A. Kropotin
 * @project some -api
 * @created 22.01.2021 18:44 <p>
 */
public interface MapperAdapter {

    /**
     * The type Mapping exception.
     */
    class MappingException extends Exception {

        /**
         * Instantiates a new Mapping exception.
         *
         * @param e the e
         */
        public MappingException(Exception e) {
            super(e.getMessage(), e.getCause());
        }
    }

    /**
     * Map t.
     *
     * @param <T>                  the type parameter
     * @param <R>                  the type parameter
     * @param source               the source
     * @param destinationTypeClass the destination type class
     * @return the t
     * @throws MappingException the mapping exception
     */
    <T, R> T map(R source, Class<T> destinationTypeClass) throws MappingException;

    /**
     * Map t.
     *
     * @param <T>         the type parameter
     * @param <R>         the type parameter
     * @param source      the source
     * @param destination the destination
     * @return the t
     * @throws MappingException the mapping exception
     */
    <T, R> T map(R source, T destination) throws MappingException;

    /**
     * Map list.
     *
     * @param <T>                  the type parameter
     * @param <R>                  the type parameter
     * @param sources              the sources
     * @param destinationTypeClass the destination type class
     * @return the list
     * @throws MappingException the mapping exception
     */
    <T, R> List<T> map(Collection<R> sources, Class<T> destinationTypeClass) throws MappingException;
}
