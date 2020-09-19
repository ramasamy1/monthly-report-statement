package com.monthly.statementprocessor.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.monthly.statementprocessor.exception.StatementProcessException;
import com.monthly.statementprocessor.model.StatementInput;
import com.monthly.statementprocessor.model.StatementRecord;
import com.monthly.statementprocessor.model.xml.XmlStatementRecord;
import com.monthly.statementprocessor.model.xml.XmlStatementRecords;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.stream.Collectors;

public class XmlProcessor implements FileProcessor {
    @Override
    public StatementInput process(InputStream inputStream) {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new ParameterNamesModule());
        xmlMapper.registerModule(new Jdk8Module());
        xmlMapper.registerModule(new JavaTimeModule());

        XmlStatementRecords statementRecords;
        try {
            statementRecords = xmlMapper.readValue(inputStream, XmlStatementRecords.class);
        } catch (IOException | IllegalArgumentException e) {
            throw new StatementProcessException("Invalid xml file", e);
        }
        return convert(statementRecords);
    }

    private StatementInput convert(XmlStatementRecords statementRecords) {

        StatementInput statementInput = new StatementInput();
        statementInput.setInput(statementRecords.getXmlRecords()
                .stream()
                .map(this::mapXmlStatementRecord)
                .collect(Collectors.toList()));
        return statementInput;
    }

    private StatementRecord mapXmlStatementRecord(XmlStatementRecord record) {

        StatementRecord statementRecord = new StatementRecord();
        try {
            statementRecord.setReference(Long.parseLong(record.getReference()));
            statementRecord.setAccountNumber(record.getAccountNumber());
            statementRecord.setDescription(record.getDescription());
            statementRecord.setStartBalance(new BigDecimal(record.getStartBalance()));
            statementRecord.setMutation(new BigDecimal(record.getMutation()));
            statementRecord.setEndBalance(new BigDecimal(record.getEndBalance()));
        } catch (NumberFormatException nfe) {
            throw new StatementProcessException("Can't map xml data. Please check the input", nfe);
        }
        return statementRecord;
    }
}
