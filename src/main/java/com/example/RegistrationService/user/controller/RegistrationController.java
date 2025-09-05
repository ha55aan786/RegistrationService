package com.example.RegistrationService.user.controller;

import com.example.RegistrationService.dto.LoginRequestDTO;
import com.example.RegistrationService.dto.RegisterRequestDTO;
import com.example.RegistrationService.dto.UserDTO;
import com.example.RegistrationService.user.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/getUser")
    public ResponseEntity<?> getAllUser() {
        try {
            List<UserDTO> user = userService.findAllUser();
            return ResponseEntity.ok(user);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
    }

    @PostMapping("/registerUser")
    public ResponseEntity<String> addUser(@Valid @RequestBody RegisterRequestDTO user) throws Exception {
        if (user.getFirstName() == null || user.getFirstName().isBlank()) {
            logger.warn("first name is not added for registration");
            return ResponseEntity.badRequest().body("add first name");
        }
        if (user.getLastName() == null || user.getLastName().isBlank()) {
            logger.warn("last name is not added for registration");
            return ResponseEntity.badRequest().body("add last name");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            logger.warn("email is not added for registration");
            return ResponseEntity.badRequest().body("add email");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            logger.warn("password is not added for registration");
            return ResponseEntity.badRequest().body("Password is required.");
        }

        userService.addUser(user);
        logger.info("user added successfully for registration");
        return ResponseEntity.status(201).body("Added successfully");

    }

    @GetMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) throws Exception {
        userService.deleteUser(id);

        return ResponseEntity.ok().body("record deleted successfully");
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        try {
            UserDTO user = userService.findUser(id);
            return ResponseEntity.ok(user);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        try {
            if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty()) {
                logger.warn("username is not added for login");
                return ResponseEntity.badRequest().body("Username is required");
            }
            if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
                logger.warn("password is not added for login");
                return ResponseEntity.badRequest().body("Password is required");
            }
            String loginSuccess = userService.loginUser(loginRequest);
            if (!Objects.isNull(loginSuccess)) {
                logger.info("login successfully");
                return ResponseEntity.ok("Login successful"+ "jwt token = " + loginSuccess);
            } else {
                logger.error("Invalid credentials for login");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (Exception e) {
            if (e.getMessage().contains("Invalid Username")) {
                logger.error("invalid username for login");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("An error occurred while logging in: " + e.getMessage());
            }
            if (e.getMessage().contains("Invalid Password")) {
                logger.error("invalid Password for login");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("An error occurred while logging in: " + e.getMessage());
            }

            logger.error("Internal server error for login");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }
}
