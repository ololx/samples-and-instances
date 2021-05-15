package org.restful.test.instances.service.mapping;

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
 * @project restful-test-instances
 * @created 02.09.2020 18:50
 * <p>
 * @author Alexander A. Kropotin
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Service
public class SimpleModelMapper implements CustomModelMapper {

    ObjectMapper objectMapper;

    /**
     * Map t.
     *
     * @param source      the source
     * @param destination the destination
     * @return the t
     */
    @Override
    public <T, R> T map(R source, T destination) throws MappingException {
        if (source == null || destination == null) return null;

        try {
            return this.objectMapper.updateValue(destination, source);
        } catch (JsonMappingException e) {
            throw new MappingException(e);
        }
    }

    /**
     * Map t.
     *
     * @param source               the source
     * @param destinationTypeClass the destination type class
     * @return the t
     * @throws MappingException the mapping exception
     */
    @Override
    public <T, R> T map(R source, Class<T> destinationTypeClass) throws MappingException {
        if (destinationTypeClass == null) return null;

        try {
            return this.map(source, destinationTypeClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new MappingException(e);
        }
    }

    /**
     * Map list.
     *
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
