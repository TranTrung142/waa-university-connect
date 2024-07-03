package com.miu.waa.search_spec;

import com.miu.waa.entities.Post;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class PostSpecificationsBuilder {

    private final List<SearchCriteria> params;

    public PostSpecificationsBuilder() {
        params = new ArrayList<>();
    }
    public final PostSpecificationsBuilder with(String key, String operation, Object value,
                                                String prefix, String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public final PostSpecificationsBuilder with(String orPredicate, String key, String operation,
                                                Object value, String prefix, String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY) { // the operation may be complex operation
                boolean startWithAsterisk = prefix != null &&
                        prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
                boolean endWithAsterisk = suffix != null &&
                        suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
            else if (op == SearchOperation.IS_NULL || op == SearchOperation.IS_NOT_NULL) {
                params.add(new SearchCriteria(key, op, null, orPredicate != null && orPredicate.equals(SearchOperation.OR_PREDICATE_FLAG)));
                return this;
            }

            params.add(new SearchCriteria(key, op, value, orPredicate != null && orPredicate.equals(SearchOperation.OR_PREDICATE_FLAG)));
        }
        return this;
    }

    public PostSpecificationsBuilder withDeletedAtIsNull() {
        params.add(
                new SearchCriteria(
                        "deletedAt",
                        SearchOperation.IS_NULL,
                        null,
                        false));
        return this;
    }

    public Specification build() {

        if (params.size() == 0)
            return null;

        Specification<Post> result = new PostSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            Specification<Post> a = Specification.where(result).and(new PostSpecification(params.get(i)));

            result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new PostSpecification(params.get(i)))
                    : Specification.where(result).and(new PostSpecification(params.get(i)));
        }

        return result;
    }
}
