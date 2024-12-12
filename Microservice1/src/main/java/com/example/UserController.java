package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
class UserController {

    @GetMapping("/users")
    public List<User> getUsers() {
        return Arrays.asList(
                new User(1, "John Doe", "john.doe@example.com"),
                new User(2, "Jane Smith", "jane.smith@example.com"),
                new User(3, "Chand Smith", "chand.smith@example.com")

        );
    }
}
