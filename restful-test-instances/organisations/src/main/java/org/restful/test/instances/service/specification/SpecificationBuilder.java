package org.restful.test.instances.service.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

/**
 * The interface Specification builder.
 * <p>
 * @param <ENTITY> the type parameter
 * <p>
 * @author Alexander A. Kropotin
 * @project restful -test-instances
 * @created 02.09.2020 11:56
 */
public interface SpecificationBuilder<ENTITY extends Object> {

    /**
     * With specification builder.
     *
     * @param key       the key
     * @param operation the operation
     * @param value     the value
     * @return the specification builder
     */
    SpecificationBuilder with(String key, String operation, Object value);

    /**
     * With not null custom specification builder.
     *
     * @param key       the key
     * @param operation the operation
     * @param value     the value
     * @return the custom specification builder
     */
    CustomSpecificationBuilder withNotNull(String key, String operation, Object value);

    /**
     * With in custom specification builder.
     *
     * @param key   the key
     * @param value the value
     * @return the custom specification builder
     */
    CustomSpecificationBuilder withIn(String key, Object value) ;

    /**
     * With equals custom specification builder.
     *
     * @param key   the key
     * @param value the value
     * @return the custom specification builder
     */
    CustomSpecificationBuilder withEquals(String key, Object value);

    /**
     * With more custom specification builder.
     *
     * @param key   the key
     * @param value the value
     * @return the custom specification builder
     */
    CustomSpecificationBuilder withMore(String key, Object value);

    /**
     * With less custom specification builder.
     *
     * @param key   the key
     * @param value the value
     * @return the custom specification builder
     */
    CustomSpecificationBuilder withLess(String key, Object value);

    /**
     * Build specification.
     *
     * @return the specification
     */
    Specification<ENTITY> build();
}
