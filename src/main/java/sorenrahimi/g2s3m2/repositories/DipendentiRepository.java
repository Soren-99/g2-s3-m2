package sorenrahimi.g2s3m2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sorenrahimi.g2s3m2.entities.Dipendente;

import java.util.Optional;

public interface DipendentiRepository extends JpaRepository<Dipendente, Integer> {

    Optional<Dipendente> findByEmail(String email);
}
