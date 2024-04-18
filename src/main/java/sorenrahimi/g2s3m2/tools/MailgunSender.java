package sorenrahimi.g2s3m2.tools;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sorenrahimi.g2s3m2.entities.Dipendente;


@Component
public class MailgunSender {
    private String apiKey;
    private String domainName;

    public MailgunSender(@Value("${mailgun.apikey}") String apiKey, @Value("${mailgun.domainname}") String domainName){
        this.apiKey = apiKey;
        this.domainName = domainName;
    }

    public void sendRegistrationEmail(Dipendente recipient){
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/"+ this.domainName + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", "sorenrahimi99@gmail.com")
                .queryString("to", recipient.getEmail())
                .queryString("subject", "Registrazione Completata!")
                .queryString("text", "Complimenti " + recipient.getNome() + " per esserti registrato!")
                .asJson();

        System.out.println(response.getBody());
    }
}
