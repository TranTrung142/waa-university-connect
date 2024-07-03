package com.miu.waa.search_spec;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SearchCriteria {
    private String key;
    private SearchOperation operation;
    private Object value;
    private boolean orPredicate;

    public boolean isOrPredicate() {
        return orPredicate;
    }

    public SearchCriteria(String key, SearchOperation operation, Object value, boolean orPredicate) {
        this.key = key;
        this.operation = operation;
        this.value = value;
        this.orPredicate = orPredicate;
    }
}