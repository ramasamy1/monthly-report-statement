package com.monthly.statementprocessor.processor;

import com.monthly.statementprocessor.enums.FileType;

public class FileProcessorFactory {

    private FileProcessorFactory() {
        throw new IllegalStateException("Factory class, not meant to be instantiated");
    }

    public static FileProcessor getFileProcessor(FileType fileType) {
        switch (fileType) {
            case CSV:
                return new CsvProcessor();
            case XML:
                return new XmlProcessor();
            default:
                return null;
        }
    }
}
