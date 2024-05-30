package com.heshanthenura.libro.services;

import com.heshanthenura.libro.User;

public class Authentication {
    public static boolean validateUser(User user, String enteredPassword) {
        if (user == null || enteredPassword == null) {
            return false;
        }
        return user.getPassword().equals(enteredPassword);
    }
}
