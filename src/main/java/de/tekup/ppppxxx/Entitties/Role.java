package de.tekup.ppppxxx.Entitties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @JsonIgnore
    @ManyToOne
    private Employee employee;


    public Role(long id, String name) {
        this.id=id;
        this.name=name;

    }
}
