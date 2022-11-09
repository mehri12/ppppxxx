package de.tekup.ppppxxx.Repositories;

import de.tekup.ppppxxx.Entitties.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface Employeerepo extends JpaRepository<Employee,Long>{
    Optional<Employee> findByFirstname(String firstName);

    Optional<Employee> findByEmail(String email);

}
