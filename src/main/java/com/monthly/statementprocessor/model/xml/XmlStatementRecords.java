package com.monthly.statementprocessor.model.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "records")
public class XmlStatementRecords {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "record")
    private List<XmlStatementRecord> xmlRecords;

    public List<XmlStatementRecord> getXmlRecords() {
        return xmlRecords;
    }

    public void setXmlRecords(List<XmlStatementRecord> xmlRecords) {
        this.xmlRecords = xmlRecords;
    }
}