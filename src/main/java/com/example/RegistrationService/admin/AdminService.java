package com.example.RegistrationService.admin;

import com.example.RegistrationService.genericExceptions.EmailAlreadyExistsException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void addAdmin(Admin admin) throws Exception {
        try{
            adminRepository.save(admin);
        } catch (DataIntegrityViolationException e) {
            // Checks if it's due to the unique constraint on email
            if (e.getMessage().contains("unique_email")) {
                throw new EmailAlreadyExistsException("This email is already registered.");
            }
            throw e; // rethrow other exceptions
        }
        }

    public void deleteAdmin(Long id) throws Exception {
        try {
            adminRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void findAdmin(Long id){
        adminRepository.findById(id);
    }
}
