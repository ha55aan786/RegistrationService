package com.example.RegistrationService.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    private final AdminService adminService;


    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/addAdmin")
    public ResponseEntity<String> addAdmin(
            @RequestParam(name = "first_name") String firstName,
            @RequestParam(name = "last_name") String lastName,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "pass") String password) throws Exception {
        Admin admin = new Admin();

        if (firstName == null || firstName.isBlank()) {
            return ResponseEntity.status(400).body("add first name");
        }
        if (lastName  == null || lastName.isBlank()) {
            return ResponseEntity.status(400).body("add last name");
        }
        if (email  == null || email.isBlank()) {
            return ResponseEntity.status(400).body("add email");
        }
        if (password  == null || password.isBlank()) {
            return ResponseEntity.status(400).body("add password");
        }
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setEmail(email);
        admin.setPassword(password);

//        try {
            adminService.addAdmin(admin);
            return ResponseEntity.status(200).body("Added successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(400).body(e.getMessage());
//
//        }
    }
}
