package org.sayem.librarymanagementsystem.service.impl;

import org.sayem.librarymanagementsystem.dto.UserAddRequest;
import org.sayem.librarymanagementsystem.dto.UserRegistrationRequest;
import org.sayem.librarymanagementsystem.entity.User;
import org.sayem.librarymanagementsystem.entity.VerificationToken;
import org.sayem.librarymanagementsystem.repository.UserRepository;
import org.sayem.librarymanagementsystem.repository.VerificationTokenRepository;
import org.sayem.librarymanagementsystem.service.EmailService;
import org.sayem.librarymanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.sayem.librarymanagementsystem.utils.LibraryUtils.generateRandomPassword;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    @Transactional
    public User registerNewUser(UserRegistrationRequest registrationDto) {
        User user = new User();
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setUsername(registrationDto.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setEmail(registrationDto.getEmail().toLowerCase());
        user.setEnabled(false);
        user.setRole("ROLE_USER");
        User savedUser = userRepository.save(user);

        // Create verification token
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(savedUser);
        verificationToken.setExpiryDate(LocalDateTime.now().plusDays(1));
        tokenRepository.save(verificationToken);
        emailService.sendVerificationEmail(savedUser, token);
        return savedUser;
    }

    @Override
    public boolean verifyUser(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null || verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }

        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        tokenRepository.delete(verificationToken);
        return true;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void addNewUser(UserAddRequest request) {
        String userSystemGeneratedPassword = generateRandomPassword();
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(userSystemGeneratedPassword));
        user.setEmail(request.getEmail().toLowerCase());
        user.setEnabled(true);
        user.setRole(request.getRole());
        User savedUser = userRepository.save(user);
        emailService.sendTemporaryUserPassword(savedUser, userSystemGeneratedPassword);
    }

    @Override
    public void deactivateUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found."));

        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public void activateUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found."));

        user.setActive(true);
        userRepository.save(user);
    }
}
