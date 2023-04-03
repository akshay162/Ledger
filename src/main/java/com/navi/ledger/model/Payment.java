package com.navi.ledger.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Payment {

    private Integer amount;
    private Integer emiNumber;
    private Status status;
    private Type type;
    private Integer remainingEMICounts;

    public enum Status {
        DUE,
        PAID
    }

    public enum Type {
        EMI,
        LUMPSUM
    }
}
