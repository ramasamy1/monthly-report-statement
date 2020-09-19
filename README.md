# monthly-report-statement
Monthly Report Statement 

## Steps to run this application 

1.Clone this repository

2.Run mvn clean package

3.Run mvn spring-boot:run

4.Alternative
Import the project in your favourite IDE -> Run as Java Application

Run the Main Java file file src/main/java/com/rabobank/statementprocessor/Application.java

## Postman API testing tool

The API can be reached at http://localhost:8080/customer/api/monthly/process-statement
Upload the csv or xml file as 'file' attribute in POST body

## CLASSES IN THE APPLICATION

ControllerStatementProcessorController handles th request & response.

Service StatementProcessorService prepares the output using other modules.

FileProcessorFactory is to create input file processor based on the file type

StatementValidator is for the validations.
