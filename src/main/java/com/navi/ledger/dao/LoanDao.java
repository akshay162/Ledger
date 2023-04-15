package com.navi.ledger.dao;

import com.navi.ledger.model.Loan;
import com.navi.ledger.utils.Utils;
import com.navi.ledger.utils.exceptions.ElementNotFoundException;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class LoanDao {

    private final Map<String, Loan> loanLedger;

    public LoanDao() {
        this.loanLedger = new ConcurrentHashMap<>();
    }

    /**
     * Returns loan using the unique hash key
     *
     * @param hashKey Loan key is composite key using BankName and BorrowerName to uniquely identifies a customer loan
     * @return
     */
    public Loan getLoan(String hashKey) {
        return Optional.ofNullable(loanLedger.get(hashKey))
                .orElseThrow(() -> new ElementNotFoundException(String.format("Loan with key: %s not found", hashKey)));
    }

    public void save(Loan loan) {
        loanLedger.put(Utils.getLoanHashKey(loan.getBankName(), loan.getBorrowerName()), loan);
    }

    public boolean doesLoanExists(String key) {
        return loanLedger.containsKey(key);
    }
}
