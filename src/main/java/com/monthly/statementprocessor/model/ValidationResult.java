package com.monthly.statementprocessor.model;

public final class ValidationResult {

    private Long reference;
    private String description;

    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
