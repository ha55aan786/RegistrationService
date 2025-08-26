package com.example.RegistrationService.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;


    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/addAdmin")
    public ResponseEntity<String> addAdmin(@RequestBody Admin admin) throws Exception {
        if (admin.getFirstName() == null || admin.getFirstName().isBlank()) {
            return ResponseEntity.status(400).body("add first name");
        }
        if (admin.getLastName()  == null || admin.getLastName().isBlank()) {
            return ResponseEntity.status(400).body("add last name");
        }
        if (admin.getEmail()  == null || admin.getEmail().isBlank()) {
            return ResponseEntity.status(400).body("add email");
        }
        if (admin.getPassword() == null || admin.getPassword().isBlank()) {
            return ResponseEntity.status(400).body("add password");
        }

        adminService.addAdmin(admin);
        return ResponseEntity.status(201).body("Added successfully");

    }

    @GetMapping("/deleteAdmin/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable("id") Long id) throws Exception {
        adminService.deleteAdmin(id);

        return ResponseEntity.ok().body("record deleted successfully");
    }

    @GetMapping("/getAdmin/{id}")
    public ResponseEntity<?> getAdmin(@PathVariable Long id) {
        try {
            Admin admin = adminService.findAdmin(id);
            return ResponseEntity.ok(admin);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
    }
}
