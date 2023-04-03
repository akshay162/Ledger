package com.navi.ledger.command;

public enum CommandType {

    BALANCE("BALANCE", 4),
    LOAN("LOAN", 6),
    PAYMENT("PAYMENT", 5);

    private final String value;
    private final Integer paramCounts;

    CommandType(final String value, final Integer paramCounts) {
        this.value = value;
        this.paramCounts = paramCounts;
    }

    public Integer getParamCounts() {
        return this.paramCounts;
    }

    public String getValue() {
        return this.value;
    }
}
