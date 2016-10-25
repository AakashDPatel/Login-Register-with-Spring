package com.model;

import com.common.User;

public interface UserDAO {
    
    public boolean validateUser(User user);
    public int registerUser(User user);
    public boolean usernameExisted(String username);
    public boolean updateProfile(User newUser);
    public boolean updatePassword(int id, String newpass);
}
