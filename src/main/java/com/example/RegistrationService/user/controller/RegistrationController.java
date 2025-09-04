package com.example.RegistrationService.user.controller;

import com.example.RegistrationService.dto.LoginRequestDTO;
import com.example.RegistrationService.dto.UserDTO;
import com.example.RegistrationService.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class RegistrationController {

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
    public ResponseEntity<String> addUser(@RequestBody UserDTO user) throws Exception {
        if (user.getFirstName() == null || user.getFirstName().isBlank()) {
            return ResponseEntity.badRequest().body("add first name");
        }
        if (user.getLastName() == null || user.getLastName().isBlank()) {
            return ResponseEntity.badRequest().body("add last name");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body("add email");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body("Password is required.");
        }

        userService.addUser(user);
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
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDTO loginRequest) throws Exception {
        try {
            if (loginRequest.getUsername().isEmpty()) {
                return ResponseEntity.badRequest().body("Username is required");
            }
            if (loginRequest.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body("Password is required");
            }
            userService.loginUser(loginRequest);
        } catch (Exception e) {
            throw new Exception (e);
        }
    }
}
