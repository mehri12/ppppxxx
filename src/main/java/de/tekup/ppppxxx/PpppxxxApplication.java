package de.tekup.ppppxxx;

import de.tekup.ppppxxx.Entitties.Employee;
import de.tekup.ppppxxx.Entitties.Role;
import de.tekup.ppppxxx.Services.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class PpppxxxApplication {

    public static void main(String[] args) {

        SpringApplication.run(PpppxxxApplication.class, args);
    }


    @Bean
    CommandLineRunner run(EmployeeService employeeService) {
        return args -> {
            employeeService.saverole(new Role(1, "admin"));
            employeeService.saverole(new Role(2, "chef_projet"));
            employeeService.saverole(new Role(3, "membre_projet"));


            employeeService.saveemployee(new Employee(1, "mehri",
                    "mojtabah", "123456", "mehri.152@gmail.com", new ArrayList<>()));
            employeeService.saveemployee(new Employee(2, "amine",
                    "benghouiziya", "1234", "amine.152@gmail.com", new ArrayList<>()));
            employeeService.saveemployee(new Employee(3, "yassine",
                    "fathallah", "123456789", "fathallah.152@gmail.com", new ArrayList<>()));


            employeeService.addroletoemployee("mojtabah", "admin");
            employeeService.addroletoemployee("amine", "chef_projet");
            employeeService.addroletoemployee("yassine", "membre_projet");
        };

    }
}
