package de.tekup.ppppxxx.Entitties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long num;
    private  String nom;
    @JsonIgnore
    @ManyToOne
    private Projet projet;
}
