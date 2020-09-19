package com.monthly.statementprocessor.model;


import java.util.List;

public final class StatementInput {

    private List<StatementRecord> input;

    public List<StatementRecord> getInput() {
        return input;
    }

    public void setInput(List<StatementRecord> input) {
        this.input = input;
    }
}
