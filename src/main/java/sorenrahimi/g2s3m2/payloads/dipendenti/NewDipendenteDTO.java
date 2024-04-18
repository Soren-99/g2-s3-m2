package sorenrahimi.g2s3m2.payloads.dipendenti;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewDipendenteDTO(

        @NotEmpty(message = "Lo username è obbligatorio")
        @Size(min = 3, max = 30, message = "Nome deve avere" +
                "minimo 3 caratteri, massimo 30")
        String username,
        @NotEmpty(message = "Il nome è obbligatorio")
        @Size(min = 3, max = 30, message = "Nome deve avere" +
                "minimo 3 caratteri, massimo 30")
        String nome,
        @NotEmpty(message = "il cognome è obbligatorio")
        String cognome,
        @NotEmpty(message = "L'email è obbligatoria")
        @Email(message = "L'email inserita non è valida")
        String email,
        @NotEmpty(message = "la password è obbligatorio")
                String password

        ) {
}
