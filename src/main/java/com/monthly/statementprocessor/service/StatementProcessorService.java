package com.monthly.statementprocessor.service;

import com.monthly.statementprocessor.enums.FileType;
import com.monthly.statementprocessor.exception.StatementProcessException;
import com.monthly.statementprocessor.model.StatementInput;
import com.monthly.statementprocessor.model.StatementOutput;
import com.monthly.statementprocessor.processor.FileProcessorFactory;
import com.monthly.statementprocessor.util.ThrowingFunction;
import com.monthly.statementprocessor.validator.StatementValidator;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Component
public class StatementProcessorService {

    public StatementOutput execute(MultipartFile file) {

        StatementInput input = Optional.of(file)
                .map(MultipartFile::getContentType)
                .map(FileType::get)
                .map(FileProcessorFactory::getFileProcessor)
                .map(ThrowingFunction.unchecked(fileProcessor -> fileProcessor.process(file.getInputStream())))
                .orElseThrow(() -> new StatementProcessException("Can't read the data"));

        StatementOutput output = new StatementOutput();
        output.setResult(StatementValidator.validate(input));
        return output;
    }
}
