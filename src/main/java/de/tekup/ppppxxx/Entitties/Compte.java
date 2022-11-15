package de.tekup.ppppxxx.Entitties;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity

public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long numCompte;
    private  String nomCompte;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Employee employee;

}
