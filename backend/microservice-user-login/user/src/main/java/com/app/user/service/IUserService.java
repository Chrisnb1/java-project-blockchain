package com.app.user.service;

import com.app.user.dto.UserDTO;
import com.app.user.dto.UserLogin;
import com.app.user.model.User;

import java.util.List;

public interface IUserService {

    List<User> getAllUser();

    boolean getLogin(UserLogin login);

    User createUser(UserDTO newUser);
}
