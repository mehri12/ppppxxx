package de.tekup.ppppxxx.Entitties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long employee_id;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    @JsonIgnore
    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY)
    private List<Role> Roles;
    @JsonIgnore
    @OneToMany(mappedBy ="employee",fetch = FetchType.LAZY)
    private List<Projet> projets;
    @OneToOne(cascade = CascadeType.ALL)
    private Compte compte;

    public Employee(Long employee_id, String firstname, String lastname, String password, String email) {
        this.employee_id=employee_id;
        this.firstname=firstname;
        this.lastname=lastname;
        this.password=password;
        this.email=email;
    }

    public <E> Employee(int employee_id, String firstname, String lastname, String password, String email, ArrayList<E> es) {
        this.employee_id=employee_id;
        this.firstname=firstname;
        this.lastname=lastname;
        this.password=password;
        this.email=email;
    }

    public long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(long employee_id) {
        this.employee_id = employee_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return Roles;
    }

    public void setRoles(List<Role> roles) {
        Roles = roles;
    }
}
