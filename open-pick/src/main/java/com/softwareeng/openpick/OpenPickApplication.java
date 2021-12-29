package com.softwareeng.openpick;

import com.softwareeng.openpick.user.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OpenPickApplication {

    public User loggedUser;

    public static void main(String[] args) {
        SpringApplication.run(OpenPickApplication.class, args);
    }
}
