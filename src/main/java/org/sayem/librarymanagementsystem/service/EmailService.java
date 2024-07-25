package org.sayem.librarymanagementsystem.service;

import org.sayem.librarymanagementsystem.entity.User;

public interface EmailService {

    void sendVerificationEmail(User user, String token);

    void sendTemporaryUserPassword(User user, String generatedPassword);
}
