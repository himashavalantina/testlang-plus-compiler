# TestLang++ (SE2062) – Final Submission Guide

This project implements the TestLang++ DSL compiler and a compliant mock API. It compiles a DSL input (`example.test`) into JUnit 5 tests (`GeneratedTests.java`) using Java 11+ HttpClient and covers all four HTTP methods: POST, GET, PUT, and DELETE.

## Files
- `src/main/resources/example.test`
- `src/main/resources/invalid.test`
- `src/test/java/com/testlang/generated/GeneratedTests.java`
- `mock-api/` (Spring Boot mock server used by tests)

## Prerequisites
- Java 17+
- Maven 3.8+

## Start the Mock API
Run the mock API in a separate terminal. Either:

```
cd mock-api
mvn spring-boot:run
```

Or package and run:

```
mvn -f mock-api/pom.xml -q package
java -jar mock-api/target/mock-api-0.0.1-SNAPSHOT.jar --server.port=8080
```

The mock server exposes (used by the generated tests):
- POST `/login` → returns `{ token: "mocked_session_token_12345", ... }`
- GET `/api/users/{id}` → requires `Authorization: Bearer <token>`, returns `{ id: <id>, ... }`
- PUT `/api/users/{id}` → requires `Authorization`, expects JSON body, responds with header `X-App: TestLangDemo` and body `{ updated: true, ... }`
- DELETE `/api/users/{id}` → requires `Authorization`, responds with `{ deleted: true, ... }`

## Run the Tests
From the module directory containing the POM:

```
cd testlang-compiler
```

Set the base URL via environment variable (recommended):

```
$env:TEST_BASE_URL = "http://localhost:8080"
mvn test
```

## What the Generated Tests Do
- `test_Login` (POST /login): expects 200 and a JSON body containing `"token"`.
- `test_GetUser` (GET /api/users/42): uses `Authorization: Bearer <token>`, expects 200, and body containing `"id": 42`.
- `test_UpdateUser` (PUT /api/users/42): sends `{ "role": "ADMIN" }`, expects 200, header `X-App = TestLangDemo`, and body containing `"updated": true`.
- `test_DeleteUser` (DELETE /api/users/42): expects 200 and body containing `"deleted": true`.

Run a single test or a subset:

```
mvn -Dtest=GeneratedTests#test_Login test
mvn -Dtest=GeneratedTests#test_Login+test_GetUser test
mvn -Dtest=GeneratedTests test
```

## Compile from DSL (example.test)
Use the Maven Exec plugin to run the compiler entrypoint:

```
mvn -q -Dexec.mainClass=org.example.Main -Dexec.args="src/main/resources/example.test" exec:java
```

Then run the generated JUnit tests:

```
mvn test
```

Alternatively, run via classpath directly (after `mvn -DskipTests package`):

```
java -cp "target/classes;$env:USERPROFILE\.m2\repository\com\github\vbmacher\java-cup-runtime\11b\java-cup-runtime-11b.jar;$env:USERPROFILE\.m2\repository\com\github\vbmacher\java-cup\11b\java-cup-11b.jar" org.example.Main src\main\resources\example.test
```

## Error Handling Demonstration (invalid.test)
The compiler reports custom errors with line/column and token context.

Run (after `mvn -DskipTests package`):

```
java -cp "target/classes;$env:USERPROFILE\.m2\repository\com\github\vbmacher\java-cup-runtime\11b\java-cup-runtime-11b.jar;$env:USERPROFILE\.m2\repository\com\github\vbmacher\java-cup\11b\java-cup-11b.jar" org.example.Main src\main\resources\invalid.test
```

Example output:

```
Syntax Error at line 3, column 3 near token type 4: Syntax error
... Couldn't repair and continue parse
```

Exit code is non-zero (1).

## DSL Design Overview
- `config { ... }` block supports base URL and default headers.
- `let name = "value";` declarations capture global variables.
- `$name` substitution works in paths, headers, and JSON bodies.
- Assertions supported:
  - `expect status = NUMBER`
  - `expect header "K" = "V"`
  - `expect header "K" contains "sub"`
  - `expect body contains "sub"`

## Submission Bundle
Include these three artifacts for grading:
- `example.test`
- `GeneratedTests.java`
- `README.md` (this file)

## Troubleshooting
- Run commands from this module folder (contains `pom.xml`).
- Set `TEST_BASE_URL` before running tests.
- Ensure mock API is running on the same port as `TEST_BASE_URL`.
