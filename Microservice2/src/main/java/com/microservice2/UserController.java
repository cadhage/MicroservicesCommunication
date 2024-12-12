package com.microservice2;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
class UserController {

    private final RestTemplate restTemplate;

    public UserController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/filter-users")
    public List<User> filterUsers() {
        // Call Service A to get the list of users
        String serviceAUrl = "http://localhost:8081/users";

        // Use ParameterizedTypeReference to map the response to List<User>
        ResponseEntity<List<User>> response = restTemplate.exchange(
                serviceAUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {
                }
        );

        List<User> users = response.getBody();

        // Filter users with email domain "@example.com"
        return users.stream()
                .filter(user -> user.getEmail().endsWith("@example.com"))
                .collect(Collectors.toList());
    }
}
