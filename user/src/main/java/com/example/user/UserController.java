package com.example.user;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag; // මේකත් එකතු කළා
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "APIs for User Service") // ලස්සනට පේන්න Tag එකක් දැම්මා
@OpenAPIDefinition(
        info = @Info(title = "User API", version = "1.0"),
        servers = {
                // Gateway එක හරහා යන්න අනිවාර්යයෙන්ම /api prefix එක ඕනේ
                @Server(url = "http://localhost:8080/api", description = "Gateway Access (8080)"),
                // කෙලින්ම User Service එකට (8084) කතා කරන්න
                @Server(url = "http://localhost:8084", description = "Direct Access (8084)")
        }
)
public class UserController {

    @Autowired
    private UserService userService;

    // 1. GET ALL USERS
    @GetMapping
    @Operation(summary = "Get all users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // 2. GET USER BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    // 3. POST (CREATE USER)
    @PostMapping
    @Operation(summary = "Create a new user")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    // 4. PUT (UPDATE USER)
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing user")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // 5. DELETE USER
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User with ID " + id + " deleted successfully";
    }
}