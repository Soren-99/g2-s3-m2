package sorenrahimi.g2s3m2.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sorenrahimi.g2s3m2.exceptions.BadRequestException;
import sorenrahimi.g2s3m2.payloads.dipendenti.DipendenteLoginDTO;
import sorenrahimi.g2s3m2.payloads.dipendenti.DipendenteLoginResponseDTO;
import sorenrahimi.g2s3m2.payloads.dipendenti.NewDipendenteDTO;
import sorenrahimi.g2s3m2.payloads.dipendenti.NewDipendenteResponseDTO;
import sorenrahimi.g2s3m2.services.AuthService;
import sorenrahimi.g2s3m2.services.DipendentiService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private DipendentiService dipendentiService;

    @PostMapping("/login")
    public DipendenteLoginResponseDTO login(@RequestBody DipendenteLoginDTO payload){
        return new DipendenteLoginResponseDTO(this.authService.authenticateDipendenteAndGenerateToken(payload));
    }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewDipendenteResponseDTO save(@RequestBody @Validated NewDipendenteDTO body, BindingResult validation)
    {
        if(validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }

        return new NewDipendenteResponseDTO(this.dipendentiService.save(body).getId());
    }

}

