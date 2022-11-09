package de.tekup.ppppxxx.Repositories;

import de.tekup.ppppxxx.Entitties.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Rolerepo extends JpaRepository<Role,Long> {
    Role findByName (String name);
}
