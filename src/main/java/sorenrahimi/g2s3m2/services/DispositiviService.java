package sorenrahimi.g2s3m2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sorenrahimi.g2s3m2.entities.Dipendente;
import sorenrahimi.g2s3m2.entities.Dispositivo;
import sorenrahimi.g2s3m2.entities.StatoDispositivo;
import sorenrahimi.g2s3m2.exceptions.NotFoundException;
import sorenrahimi.g2s3m2.payloads.dispositivi.NewDispositivoPayload;
import sorenrahimi.g2s3m2.repositories.DispositiviRepository;

import java.util.List;
@Service
public class DispositiviService {
    @Autowired
    private DispositiviRepository dispositiviRepository;
    @Autowired
    private DipendentiService dipendentiService;


    public Dispositivo save(NewDispositivoPayload body) {
        Dipendente dipendente = dipendentiService.findById(body.dipendenteId());
        Dispositivo newDispositivo = new Dispositivo();
        newDispositivo.setTipo(body.tipo());
        newDispositivo.setStatoDispositivo(StatoDispositivo.DISPONIBILE);
        return dispositiviRepository.save(newDispositivo);
    }


    public List<Dispositivo> getDispositivi() {
        return dispositiviRepository.findAll();
    }

    public Dispositivo findById(int id) {
        return dispositiviRepository.findById(id).orElseThrow(()-> new
                NotFoundException(id));
    }

    public void findByIdAndDelete(int id){
        Dispositivo found = this.findById(id);
        dispositiviRepository.delete(found);
    }

    public Dispositivo findByIdAndUpdate(int id, NewDispositivoPayload body){
        Dispositivo found = this.findById(id);

        found.setTipo(body.tipo());
        found.setStatoDispositivo(body.statoDispositivo());
        if (found.getDipendente().getId() != body.dipendenteId()) {
            Dipendente newDipendente =
                    dipendentiService.findById(body.dipendenteId());
            found.setDipendente(newDipendente);
        }
        return dispositiviRepository.save(found);
    }
    public List<Dispositivo> findByDipendente(int dipendenteId){
        Dipendente dipendente = dipendentiService.findById(dipendenteId);
        return dispositiviRepository.findByDipendente(dipendente);
    }
}

