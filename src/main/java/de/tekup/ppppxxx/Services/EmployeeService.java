package de.tekup.ppppxxx.Services;

import de.tekup.ppppxxx.Entitties.Employee;
import de.tekup.ppppxxx.Entitties.Role;
import de.tekup.ppppxxx.Repositories.Employeerepo;
import de.tekup.ppppxxx.Repositories.Rolerepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor @Slf4j @Transactional @Component
public class EmployeeService implements UserDetailsService {

    @Autowired
    private Employeerepo employeeRepo;
    @Autowired
    private Rolerepo rolerepo;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String firstname) throws UsernameNotFoundException {
        Optional<Employee> employee = employeeRepo.findByFirstname(firstname);
        if (!employee.isPresent()) {
            log.error("employee_name is not exist in the database");
            throw new UsernameNotFoundException("employee not found");
        } else {
            log.info("employee is found in the databse ");
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            employee.get().getRoles().forEach(role ->
            {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            return new org.springframework.security.core.userdetails.
                    User(employee.get().getFirstname(), employee.get().getPassword(), authorities);
        }
    }

    public Employee saveemployee(Employee employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return employeeRepo.save(employee);
    }

    public Role saverole(Role role) {

        return rolerepo.save(role);
    }

    public void addroletoemployee(String firstname, String rolename) {
        Optional<Employee> employee = employeeRepo.findByFirstname(firstname);
        Role role = rolerepo.findByName(rolename);

    }


    public Employee getemployee(String firstname) {
        if (employeeRepo.findByFirstname(firstname).isPresent()) {
            return employeeRepo.findByFirstname(firstname).get();
        }
        return null;
    }


    public List<Employee> getallemployee() {
        return employeeRepo.findAll();
    }


    public Employee findById(long employee_id) {
        if (employeeRepo.findById(employee_id).isPresent()) {
            return employeeRepo.findById(employee_id).get();
        } else
            return null;
    }


    public void deleteemp(long employee_id) {
        if (employeeRepo.findById(employee_id).isPresent()){
            employeeRepo.deleteById(employee_id);
        }

    }
}
