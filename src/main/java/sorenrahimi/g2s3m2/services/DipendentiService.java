package sorenrahimi.g2s3m2.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sorenrahimi.g2s3m2.entities.Dipendente;
import sorenrahimi.g2s3m2.entities.Role;
import sorenrahimi.g2s3m2.exceptions.BadRequestException;
import sorenrahimi.g2s3m2.exceptions.NotFoundException;
import sorenrahimi.g2s3m2.payloads.dipendenti.NewDipendenteDTO;
import sorenrahimi.g2s3m2.repositories.DipendentiRepository;
import sorenrahimi.g2s3m2.tools.MailgunSender;

@Service
public class DipendentiService {

    @Autowired
    private DipendentiRepository dipendentiRepository;
    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private MailgunSender mailgunSender;

    /*public Dipendente save(NewDipendenteDTO body) {
        dipendentiRepository.findByEmail(body.email()).ifPresent(dipendente -> {

            throw new BadRequestException("L'email " +
                    body.email() + "è gia stata utilizzata");
        });
        Dipendente newDipendente = new Dipendente();
        newDipendente.setImmagine("https://ui-immagini.com/api/?name" +
               body.username() + "+" + body.nome() + "+" + body.cognome());
        newDipendente.setUsername(body.username());
        newDipendente.setNome(body.nome());
        newDipendente.setEmail(body.email());
        newDipendente.setCognome(body.cognome());
        newDipendente.setPassword(body.password());
        newDipendente.setRole(Role.DIPENDENTE);
        return dipendentiRepository.save(newDipendente);
    }*/

    public Dipendente save(NewDipendenteDTO body) throws BadRequestException{

        this.dipendentiRepository.findByEmail(body.email()).ifPresent(

                user -> {
                    throw new BadRequestException("L'email " + user.getEmail() + " è già in uso!");
                }
        );

        Dipendente newDipendente = new Dipendente(body.username(), body.nome(), body.cognome(), body.email(), bcrypt.encode(body.password()),
                "https://ui-avatars.com/api/?name="+ body.nome() + "+" + body.cognome());

        mailgunSender.sendRegistrationEmail(newDipendente);

        return dipendentiRepository.save(newDipendente);
    }


    public Page<Dipendente> getDipendenti(int page, int size, String sort){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return dipendentiRepository.findAll(pageable);
    }

    public Dipendente findById ( int id){
        return this.dipendentiRepository.findById(id).orElseThrow(() -> new
                NotFoundException(id));
    }

    public void findByIdAndDelete ( int id){
        Dipendente found = this.findById(id);
        this.dipendentiRepository.delete(found);
    }
    public Dipendente findByIdAndUpdate (int id, Dipendente body){
        Dipendente found = this.findById(id);
        found.setNome(body.getNome());
        found.setCognome(body.getCognome());
        found.setEmail(body.getEmail());
        found.setPassword(body.getPassword());
        found.setImmagine(body.getImmagine());
        return this.dipendentiRepository.save(found);
    }

    public Dipendente findByEmail(String email) {
        return dipendentiRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }


}
