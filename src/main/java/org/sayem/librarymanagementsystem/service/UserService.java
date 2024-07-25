package org.sayem.librarymanagementsystem.service;

import org.sayem.librarymanagementsystem.dto.UserAddRequest;
import org.sayem.librarymanagementsystem.dto.UserRegistrationRequest;
import org.sayem.librarymanagementsystem.entity.User;

import java.util.List;

public interface UserService {

    User registerNewUser(UserRegistrationRequest registrationDto);

    boolean verifyUser(String token);

    List<User> getUsers();

    void addNewUser(UserAddRequest request);

    void deactivateUser(long id);

    void activateUser(long id);
}
