package com.app.user.service;

import com.app.user.dto.UserDTO;
import com.app.user.dto.UserLogin;
import com.app.user.model.User;
import com.app.user.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUser() {
        if (!userRepository.findAll().isEmpty()) {
            return new ArrayList<>(userRepository.findAll());
        }
        return null;
    }

    @Override
    public boolean getLogin(UserLogin login) {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(userExists(login.getEmail())){
            User user = userRepository.findByEmail(login.getEmail());
            return bCryptPasswordEncoder.matches(login.getPassword(), user.getPassword());
        }
        return false;
    }

    @Override
    public User createUser(UserDTO request) {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if(userExists(request.getEmail())){
            throw new IllegalStateException("El email ya existe");
        }

        User user = new User();
        user.setAge(request.getAge());
        user.setFirstName(request.getFirstName());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));

        if(validateUser(user)){
            return userRepository.save(user);
        }

        throw new IllegalStateException("Error al crear el usuario");
    }

    public boolean validateUser(User user) {
        if (user.getAge() == null) {
            System.out.println("La edad del usuario no puede ser nula "+ '\'');
            return false;
        } else if (user.getFirstName() == null) {
            System.out.println("El nombre del usuario no puede ser nulo "+ '\'');
            return false;
        } else if (user.getLastname() == null) {
            System.out.println("El apellido del usuario no puede ser nulo "+ '\'');
            return false;
        } else if (user.getEmail() == null) {
            System.out.println("El mail del usuario no puede ser nulo "+ '\'');
            return false;
        } else if (user.getPassword() == null) {
            System.out.println("La contraseña del usuario no puede ser nula "+ '\'');
            return false;
        }
        return true;
    }

    public boolean userExists(String email){
        return userRepository.findByEmail(email) != null;
    }
}
