package de.tekup.ppppxxx.Entitties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Projet")
    private int idProjet;
    @Column(name = "nom_Projet")
    private String nomProjet;
    @OneToMany(mappedBy = "projet",fetch = FetchType.LAZY)
    private List<Tache> taches;
    @ManyToOne
    private Employee employee;
}
