package com.example.testlang;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TestlangController {

    private static final String VALID_USERNAME = "test@example.com";
    private static final String VALID_PASSWORD = "P@ssword123";
    private static final String MOCK_TOKEN = "mocked_session_token_12345";

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> successfulLogin(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
            Map<String, String> response = new HashMap<>();
            response.put("token", MOCK_TOKEN);
            response.put("message", "Login successful");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
    }

    @GetMapping("/user/profile")
    public ResponseEntity<Map<String, Object>> getUserProfile(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {

        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("error", "Missing token"));
        }
        String cleanedToken = authorizationHeader.replaceFirst("(?i)Bearer\\s+", "").trim();
        if (!MOCK_TOKEN.equals(cleanedToken)) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized token"));
        }

        Map<String, Object> profile = new HashMap<>();
        profile.put("id", 101);
        profile.put("email", VALID_USERNAME);
        profile.put("name", "Test User");
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(
            @PathVariable int id,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {

        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("error", "Missing token"));
        }
        String cleanedToken = authorizationHeader.replaceFirst("(?i)Bearer\\s+", "").trim();
        if (!MOCK_TOKEN.equals(cleanedToken)) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized token"));
        }

        Map<String, Object> body = new HashMap<>();
        body.put("id", id);
        body.put("email", VALID_USERNAME);
        body.put("name", "Test User");
        return ResponseEntity.ok(body);
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(
            @PathVariable int id,
            @RequestBody Map<String, Object> payload,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {

        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("error", "Missing token"));
        }
        String cleanedToken = authorizationHeader.replaceFirst("(?i)Bearer\\s+", "").trim();
        if (!MOCK_TOKEN.equals(cleanedToken)) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized token"));
        }

        Map<String, Object> body = new HashMap<>();
        body.put("updated", true);
        body.put("id", id);
        return ResponseEntity.ok()
                .header("X-App", "TestLangDemo")
                .body(body);
    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(
            @PathVariable int id,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {

        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("error", "Missing token"));
        }
        String cleanedToken = authorizationHeader.replaceFirst("(?i)Bearer\\s+", "").trim();
        if (!MOCK_TOKEN.equals(cleanedToken)) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized token"));
        }

        return ResponseEntity.ok().body(Map.of("deleted", true, "id", id));
    }
}
