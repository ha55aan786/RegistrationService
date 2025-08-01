package com.example.RegistrationService.user;

import com.example.RegistrationService.admin.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDBOperations {

    private final AdminRepository adminRepository;

    UserDBOperations(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }

}
