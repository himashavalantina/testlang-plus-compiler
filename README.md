A compiler and interpreter built for the TestLang++ DSL.  
This project includes the Lexer (JFlex), Parser (CUP/LALR), AST Generator, and a JUnit Test Generator that converts DSL scripts into executable API validation tests.

It compiles a DSL input (`example.test`) into JUnit 5 tests (`GeneratedTests.java`) using Java HttpClient and supports POST, GET, PUT, DELETE requests.

## Project Files
- src/main/resources/example.test
- src/main/resources/invalid.test
- src/test/java/com/testlang/generated/GeneratedTests.java
- mock-api/ (Spring Boot mock API)

## Prerequisites
- Java 17+
- Maven 3.8+

## Start the Mock API
cd mock-api
mvn spring-boot:run

OR

mvn -f mock-api/pom.xml -q package
java -jar mock-api/target/mock-api-0.0.1-SNAPSHOT.jar --server.port=8080

## Running Tests
$env:TEST_BASE_URL = "http://localhost:8080"
mvn test

## Generate Tests from DSL
mvn -q -Dexec.mainClass=org.example.Main -Dexec.args="src/main/resources/example.test" exec:java
mvn test

## Error Handling (invalid.test)
Syntax errors are reported with line/column and token context.

## DSL Features
- config { }
- let variables
- $variable substitution
- expect status/header/body assertions

## Submission Includes
- example.test
- GeneratedTests.java
- README.md
