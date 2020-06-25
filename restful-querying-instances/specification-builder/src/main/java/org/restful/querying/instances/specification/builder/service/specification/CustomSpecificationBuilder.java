package org.restful.querying.instances.specification.builder.service.specification;

import lombok.*;
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

@Slf4j
@NoArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@Service
public class CustomSpecificationBuilder<ENTITY extends Object> {

    public static CustomSpecificationBuilder getInstance() {
        return new CustomSpecificationBuilder();
    }

    @Data
    @FieldDefaults(
            level = AccessLevel.PRIVATE,
            makeFinal = true
    )
    class CustomCriteria {

        /** The operand (entity field) */
        String key;

        /** The operation */
        String operation;

        /** The value of operand */
        Object value;
    }

    @Data
    @FieldDefaults(
            level = AccessLevel.PRIVATE,
            makeFinal = true
    )
    class CustomSpecification<ENTITY> implements Specification<ENTITY> {

        CustomCriteria criteria;

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

    private final List<CustomSpecification> customSpecifications;

    {
        customSpecifications = new ArrayList<>();
    }


    public CustomSpecificationBuilder with(String key, String operation, Object value) {
        customSpecifications.add(new CustomSpecification(new CustomCriteria(key, operation, value)));

        return this;
    }

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

    public CustomSpecificationBuilder withIn(String key, Object value) {
        return this.withNotNull(key, "in", value);
    }

    public CustomSpecificationBuilder withEquals(String key, Object value) {
        return this.withNotNull(key, ":", value);
    }

    public CustomSpecificationBuilder withMore(String key, Object value) {
        return this.withNotNull(key, ">", value);
    }

    public CustomSpecificationBuilder withLess(String key, Object value) {
        return this.withNotNull(key, "<", value);
    }

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
