package org.some.api.commons.mapping;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * The type Abstract model mapper.
 *
 * @author Alexander A. Kropotin
 * @project some -api
 * @created 22.01.2021 18:51 <p>
 */
@Slf4j
@FieldDefaults(
        level = AccessLevel.PROTECTED,
        makeFinal = true
)
@Service
public abstract class AbstractModelMapperAdapter
        implements MapperAdapter {

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
            return new ArrayList<T>(){{
                for (R source : sources) {
                    add(map(source, getDestinationInstance(destinationTypeClass)));
                }
            }};
        } catch (NullPointerException e) {
            throw new MappingException(e);
        }
    }

    /**
     * Gets destination instance.
     *
     * @param <T>                  the type parameter
     * @param destinationTypeClass the destination type class
     * @return the destination instance
     * @throws MappingException the mapping exception
     */
    protected <T> T getDestinationInstance(Class<T> destinationTypeClass) throws MappingException {
        try {
            return destinationTypeClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new MappingException(e);
        }
    }
}
