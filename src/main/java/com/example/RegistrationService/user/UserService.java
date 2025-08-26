package com.example.RegistrationService.user;

import com.example.RegistrationService.genericExceptions.EmailAlreadyExistsException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void addUser(User user) {
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
                // Checks if it's due to the unique constraint on email
                if (e.getMessage().contains("unique_email")) {
                    throw new EmailAlreadyExistsException("This email is already registered.");
                }
                throw e; // rethrow other exceptions
            }
        }

        public void deleteUser(Long id) throws Exception {
           try {
               userRepository.deleteById(id);
           } catch (Exception e) {
               throw new Exception(e);
           }
        }

}
