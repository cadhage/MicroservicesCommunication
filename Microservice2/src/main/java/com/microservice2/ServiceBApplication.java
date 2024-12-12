package com.microservice2;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class ServiceBApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceBApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
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
                new ParameterizedTypeReference<List<User>>() {}
        );

        List<User> users = response.getBody();

        // Filter users with email domain "@example.com"
        return users.stream()
                .filter(user -> user.getEmail().endsWith("@example.com"))
                .collect(Collectors.toList());
    }
}

class User {
    private int id;
    private String name;
    private String email;

    // Constructor
    public User(@JsonProperty("id") int id,
                @JsonProperty("name") String name,
                @JsonProperty("email") String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
