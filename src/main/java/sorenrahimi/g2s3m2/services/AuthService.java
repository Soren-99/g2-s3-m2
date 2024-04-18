package sorenrahimi.g2s3m2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sorenrahimi.g2s3m2.entities.Dipendente;
import sorenrahimi.g2s3m2.exceptions.UnauthorizedException;
import sorenrahimi.g2s3m2.payloads.dipendenti.DipendenteLoginDTO;
import sorenrahimi.g2s3m2.security.JWTTools;

@Service
public class AuthService {
    @Autowired
    private DipendentiService dipendentiService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateDipendenteAndGenerateToken(DipendenteLoginDTO payload){


        Dipendente dipendente = this.dipendentiService.findByEmail(payload.email());

        if (bcrypt.matches(payload.password(), dipendente.getPassword())) {

            return jwtTools.createToken(dipendente);
        } else {

            throw new UnauthorizedException("Credenziali non valide! Effettua di nuovo il login!");
        }


    }
}

