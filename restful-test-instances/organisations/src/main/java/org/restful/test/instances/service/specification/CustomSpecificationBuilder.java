package org.restful.test.instances.service.specification;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The type Custom specification builder.
 * <p>
 * @param <ENTITY> the type parameter
 * @author Alexander A. Kropotin
 * @project restful -test-instances
 * @created 2020 -04-25 13:46
 */
@Slf4j
@NoArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@Service
public class CustomSpecificationBuilder<ENTITY extends Object> {

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static CustomSpecificationBuilder getInstance() {
        return new CustomSpecificationBuilder();
    }

    /**
     * The type Custom criteria.
     */
    @Data
    @FieldDefaults(
            level = AccessLevel.PRIVATE,
            makeFinal = true
    )
    class CustomCriteria {

        /**
         * The operand (entity field)
         */
        String key;

        /**
         * The operation
         */
        String operation;

        /**
         * The value of operand
         */
        Object value;
    }

    /**
     * The type Custom specification.
     *
     * @param <ENTITY> the type parameter
     */
    @Data
    @FieldDefaults(
            level = AccessLevel.PRIVATE,
            makeFinal = true
    )
    class CustomSpecification<ENTITY> implements Specification<ENTITY> {

        /**
         * The Criteria.
         */
        CustomCriteria criteria;

        /**
         * To predicate predicate.
         *
         * @param detail  the detail
         * @param query   the query
         * @param builder the builder
         * @return the predicate
         */
        @Override
        public Predicate toPredicate(Root<ENTITY> detail, CriteriaQuery<?> query, CriteriaBuilder builder) {

            if (criteria.getOperation().equalsIgnoreCase(">")) {

                return builder.greaterThanOrEqualTo(
                        detail.<String> get(criteria.getKey()), criteria.getValue().toString()
                );
            } else if (criteria.getOperation().equalsIgnoreCase("<")) {

                return builder.lessThanOrEqualTo(
                        detail.<String> get(criteria.getKey()), criteria.getValue().toString()
                );
            } else if (criteria.getOperation().equalsIgnoreCase(":")) {

                if (detail.get(criteria.getKey()).getJavaType() == String.class) {

                    return builder.like(
                            detail.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%"
                    );
                } else {

                    return builder.equal(
                            detail.get(criteria.getKey()), criteria.getValue()
                    );
                }
            } else if (criteria.getOperation().equalsIgnoreCase("in")) {

                return builder.in(
                        detail.get(criteria.getKey())).value(criteria.getValue()
                );
            }

            return null;
        }
    }

    /**
     * The Custom specifications.
     */
    private final List<CustomSpecification> customSpecifications;

    {
        customSpecifications = new ArrayList<>();
    }


    /**
     * With custom specification builder.
     *
     * @param key       the key
     * @param operation the operation
     * @param value     the value
     * @return the custom specification builder
     */
    public CustomSpecificationBuilder with(String key, String operation, Object value) {
        customSpecifications.add(new CustomSpecification(new CustomCriteria(key, operation, value)));

        return this;
    }

    /**
     * With not null custom specification builder.
     *
     * @param key       the key
     * @param operation the operation
     * @param value     the value
     * @return the custom specification builder
     */
    public CustomSpecificationBuilder withNotNull(String key, String operation, Object value) {

        if (value != null) {

            if (value instanceof Collection && !((Collection) value).isEmpty()) {

                return this.with(key, operation, value);
            } else {

                return this.with(key, operation, value);
            }
        }

        return this;
    }

    /**
     * With in custom specification builder.
     *
     * @param key   the key
     * @param value the value
     * @return the custom specification builder
     */
    public CustomSpecificationBuilder withIn(String key, Object value) {
        return this.withNotNull(key, "in", value);
    }

    /**
     * With equals custom specification builder.
     *
     * @param key   the key
     * @param value the value
     * @return the custom specification builder
     */
    public CustomSpecificationBuilder withEquals(String key, Object value) {
        return this.withNotNull(key, ":", value);
    }

    /**
     * With more custom specification builder.
     *
     * @param key   the key
     * @param value the value
     * @return the custom specification builder
     */
    public CustomSpecificationBuilder withMore(String key, Object value) {
        return this.withNotNull(key, ">", value);
    }

    /**
     * With less custom specification builder.
     *
     * @param key   the key
     * @param value the value
     * @return the custom specification builder
     */
    public CustomSpecificationBuilder withLess(String key, Object value) {
        return this.withNotNull(key, "<", value);
    }

    /**
     * Build specification.
     *
     * @return the specification
     */
    public Specification<ENTITY> build() {

        if (customSpecifications.size() == 0) return null;

        Specification specification = customSpecifications.get(0);
        for (int i = 1; i < customSpecifications.size(); i++) {
            specification = Specification.where(specification)
                    .and(customSpecifications.get(i));
        }

        customSpecifications.clear();

        return specification;
    }
}
