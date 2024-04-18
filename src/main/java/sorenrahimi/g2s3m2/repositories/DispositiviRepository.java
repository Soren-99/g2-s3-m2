package sorenrahimi.g2s3m2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sorenrahimi.g2s3m2.entities.Dipendente;
import sorenrahimi.g2s3m2.entities.Dispositivo;

import java.util.List;


@Repository
public interface DispositiviRepository extends JpaRepository<Dispositivo, Integer> {
    List<Dispositivo> findByDipendente(Dipendente dipendente);
}
