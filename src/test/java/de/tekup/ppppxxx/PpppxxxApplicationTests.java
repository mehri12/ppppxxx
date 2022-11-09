package de.tekup.ppppxxx;

import de.tekup.ppppxxx.Entitties.Employee;
import de.tekup.ppppxxx.Entitties.Role;
import de.tekup.ppppxxx.Services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootTest
class PpppxxxApplicationTests {

    public static void main(String[] args) {

        SpringApplication.run(PpppxxxApplicationTests.class, args) ;
    }




    @Test
    void contextLoads() {
    }

}
