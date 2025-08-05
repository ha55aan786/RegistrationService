package com.example.RegistrationService.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    void addUser(User user);
    void deleteUser(Long id);
    User getUser(Long id);
}
