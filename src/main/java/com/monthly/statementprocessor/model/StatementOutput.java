package com.monthly.statementprocessor.model;

import java.util.List;

public final class StatementOutput {

    private List<ValidationResult> result;

    public List<ValidationResult> getResult() {
        return result;
    }

    public void setResult(List<ValidationResult> result) {
        this.result = result;
    }
}
