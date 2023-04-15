package com.navi.ledger.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Loan {
    private String bankName;
    private String borrowerName;
    private Integer principle;
    private Integer rateOfInterest;
    private Integer timeInYears;
    private Integer interest;
    private Integer emiRemaining;
    private Integer amountRemaining;
    private List<Payment> payments;
}
