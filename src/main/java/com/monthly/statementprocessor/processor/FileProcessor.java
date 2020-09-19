package com.monthly.statementprocessor.processor;

import java.io.InputStream;

import com.monthly.statementprocessor.model.StatementInput;

public interface FileProcessor {
    StatementInput process(InputStream inputStream);
}
