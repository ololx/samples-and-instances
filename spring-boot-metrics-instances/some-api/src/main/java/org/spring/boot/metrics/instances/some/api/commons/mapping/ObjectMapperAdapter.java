package org.spring.boot.metrics.instances.some.api.commons.mapping;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The type Jackson adapter.
 *
 * @author Alexander A. Kropotin
 * @project some -api
 * @created 22.01.2021 18:51 <p>
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Service("ObjectMapperAdapter")
public class ObjectMapperAdapter
        extends AbstractModelMapperAdapter {

    /**
     * The Mapper.
     */
    ObjectMapper mapper;

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
    @Override
    public <T, R> T map(R source, Class<T> destinationTypeClass) throws MappingException {
        log.trace("Start to map entity with params:\nsource - {}\nand destination - {}", source, destinationTypeClass);
        T destination = this.map(source, getDestinationInstance(destinationTypeClass));
        log.trace("Return the object to object mapping result - {}", destination);

        return destination;
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
    @Override
    public <T, R> T map(R source, T destination) throws MappingException {
        log.trace("Start to map entity with params:\nsource - {}\nand destination - {}", source, destination);

        try {
            this.mapper.updateValue(destination, source);
            log.trace("Return the object to class mapping result - {}", destination);

            return destination;
        } catch (JsonMappingException e) {
            log.error("Catch NPE - {}", e.getMessage());

            throw new MappingException(e);
        }
    }
}
