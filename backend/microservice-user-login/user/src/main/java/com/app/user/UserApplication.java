package com.app.user;

import com.app.user.dto.UserDTO;
import com.app.user.model.User;
import com.app.user.repository.IUserRepository;
import com.app.user.service.IUserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
public class UserApplication implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final IUserService userService;

    @Autowired
    public UserApplication(IUserRepository userRepository, IUserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Override
    public void run(String... arg) throws Exception {
        if(userRepository.findAll().isEmpty()){
            UserDTO newUser = new UserDTO("20", "Fulano", "Mengano", "fulano123@correo.com", "123456");
            userService.createUser(newUser);
        }

        if (!userRepository.findAll().isEmpty()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(userRepository.findAll());

            System.out.println(json);
        }
    }
}
