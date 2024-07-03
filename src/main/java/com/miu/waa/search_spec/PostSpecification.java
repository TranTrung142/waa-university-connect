package com.miu.waa.search_spec;

import com.miu.waa.entities.Post;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
@Getter
public class PostSpecification implements Specification<Post> {
    private final SearchCriteria criteria;
    @Override
    public Predicate toPredicate(final Root<Post> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
            switch (criteria.getOperation()) {
                case EQUALITY:
                    return builder.equal(root.get(criteria.getKey()), criteria.getValue());
                case NEGATION:
                    return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
                case GREATER_THAN:
                    return builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
                case LESS_THAN:
                    return builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
                case LIKE:
                    return builder.like(root.get(criteria.getKey()), criteria.getValue().toString());
                case STARTS_WITH:
                    return builder.like(root.get(criteria.getKey()), criteria.getValue() + "%");
                case ENDS_WITH:
                    return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue());
                case CONTAINS:
                    return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
                case IS_NULL:
                    return builder.isNull(root.get(criteria.getKey()));
                case IS_NOT_NULL:
                    return builder.isNotNull(root.get(criteria.getKey()));
                default:
                    return null;
            }
    }
}
