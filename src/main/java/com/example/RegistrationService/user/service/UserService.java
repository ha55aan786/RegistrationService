package com.example.RegistrationService.user.service;

import com.example.RegistrationService.dto.LoginRequestDTO;
import com.example.RegistrationService.dto.RegisterRequestDTO;
import com.example.RegistrationService.dto.UserDTO;
import com.example.RegistrationService.genericExceptions.EmailAlreadyExistsException;
import com.example.RegistrationService.user.entity.User;
import com.example.RegistrationService.user.repository.UserRepository;
import com.example.RegistrationService.utility.JwtUtility;
import com.example.RegistrationService.utility.MailSenderUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void addUser(RegisterRequestDTO userDto) {
        try {
            String encodedPassword = passwordEncoder.encode(userDto.getPassword());
            User user = new User();

            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setPassword(encodedPassword);

            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            // Checks if it's due to the unique constraint on email
            if (e.getCause().toString().contains("(email)")) {
                throw new EmailAlreadyExistsException("This email is already registered.");
            }
            if (e.getCause().toString().contains("(username)")) {
                throw new EmailAlreadyExistsException("This username is already registered.");
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

    public List<UserDTO> findAllUser() {

        List<User> users = userRepository.findAll();
        List<UserDTO> UserDTOs = new ArrayList<>();
        for (User user : users) {
            UserDTO userDto = new UserDTO();
            userDto.setUsername(user.getUsername());
            userDto.setId(user.getId());
            userDto.setEmail(user.getEmail());
            userDto.setLastName(user.getLastName());
            userDto.setFirstName(user.getFirstName());

            UserDTOs.add(userDto);
        }
        return UserDTOs;
    }

    public UserDTO findUser(Long id) throws Exception {
        try {
            Optional<User> userOpt = userRepository.findById(id);
            UserDTO userDto = new UserDTO();
            if (userOpt.isPresent()) {
                User user = userOpt.get();


                userDto.setId(user.getId());
                userDto.setUsername(user.getUsername());
                userDto.setEmail(user.getEmail());
                userDto.setLastName(user.getLastName());
                userDto.setFirstName(user.getFirstName());
                return userDto;
            }
            return userDto;

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public String loginUser(LoginRequestDTO loginRequest) throws Exception {
        User user ;
        try {
            user = userRepository.findByUsername(loginRequest.getUsername());
            if (Objects.isNull(user)) {
                throw new Exception("Invalid Username");
            }
            if  (!user.isActive()) {
                logger.warn("Account blocked for " + user.getUsername());
                throw new Exception("Account blocked due to multiple invalid tries");
            }
            boolean isCorrectPassword = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
            if (!isCorrectPassword) {
                user.setInvalidTries(user.getInvalidTries() + 1);
                if (user.getInvalidTries() > 5) {
                    user.setActive(false);
                    MailSenderUtility.callMailSenderService(JwtUtility.generateToken(user.getUsername()), "Account blocked", user.getEmail());
                }
                userRepository.save(user);
                throw new Exception ("Invalid Password");
            }

        } catch (Exception e) {
            throw new Exception(e);
        }

        user.setInvalidTries(0);
        userRepository.save(user);
        return JwtUtility.generateToken(user.getUsername());
    }
}
