package org.orm.patterns.instances.commons.mapping;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * The type Simple model mapper.
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Service
public class SimpleModelMapper implements CustomModelMapper {

    /**
     * The Object mapper.
     */
    ObjectMapper objectMapper;

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
    @Override
    public <T, R> T map(R source, T destination) throws MappingException {
        try {
            return this.objectMapper.updateValue(destination, source);
        } catch (JsonMappingException e) {
            throw new MappingException(e);
        }
    }

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
    @Override
    public <T, R> List<T> map(Collection<R> sources, Class<T> destinationTypeClass) throws MappingException {
        if (sources == null) return Collections.emptyList();

        try {
            return new ArrayList<>(){{
                for (R source : sources) {
                    T destination = destinationTypeClass.newInstance();
                    add(map(source, destination));
                }
            }};
        } catch (InstantiationException | IllegalAccessException | NullPointerException e) {
            throw new MappingException(e);
        }
    }
}
