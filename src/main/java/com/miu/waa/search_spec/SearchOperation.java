package com.miu.waa.search_spec;

public enum SearchOperation {
    EQUALITY, NEGATION, GREATER_THAN, LESS_THAN, LIKE, STARTS_WITH, ENDS_WITH, CONTAINS,IS_NULL, IS_NOT_NULL , NEAR;;

    public static final String[] SIMPLE_OPERATION_SET = { ":", "!", ">", "<", "~","null", "notnull" ,"r" };

    public static final String OR_PREDICATE_FLAG = "'";

    public static final String ZERO_OR_MORE_REGEX = "*";

    public static final String OR_OPERATOR = "OR";

    public static final String AND_OPERATOR = "AND";

    public static final String LEFT_PARANTHESIS = "(";

    public static final String RIGHT_PARANTHESIS = ")";

    public static SearchOperation getSimpleOperation(final char input) {
        switch (input) {
            case ':':
                return EQUALITY;
            case '!':
                return NEGATION;
            case '>':
                return GREATER_THAN;
            case '<':
                return LESS_THAN;
            case '~':
                return LIKE;
            case 'n':
                return IS_NULL;
            case 't':
                return IS_NOT_NULL;
            case 'r':  // 'r' for NEAR
                return NEAR;
            default:
                return null;
        }
    }
}