package org.restful.test.instances.service.mapping;

import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.Collection;
import java.util.List;

/**
 * The interface Model mapper adapter.
 * <p>
 *
 * @author Alexander A. Kropotin
 * @project restful -test-instances
 * @created 02.09.2020 15:03
 */
public interface CustomModelMapper {

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
