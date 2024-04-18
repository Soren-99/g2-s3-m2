package sorenrahimi.g2s3m2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sorenrahimi.g2s3m2.entities.Dispositivo;
import sorenrahimi.g2s3m2.exceptions.BadRequestException;
import sorenrahimi.g2s3m2.payloads.dispositivi.NewDispositivoPayload;
import sorenrahimi.g2s3m2.services.DispositiviService;

import java.util.List;

@RestController
@RequestMapping("/dispostivi")
public class DispositiviController {
    @Autowired
    DispositiviService dispositiviService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Dispositivo saveDispositivo(@RequestBody @Validated NewDispositivoPayload body,
                             BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return dispositiviService.save(body);
    }

    @GetMapping("")
    public List<Dispositivo> getDispositivi(@RequestParam(required = false)
                                   Integer dipendenteId) {
        if (dipendenteId != null) return dispositiviService.findByDipendente(dipendenteId);
        else return dispositiviService.getDispositivi();
    }

    @GetMapping("/{dispositivoId}")
    public Dispositivo findById(@PathVariable int dispositivoId){
        return dispositiviService.findById(dispositivoId);
    }

    @PutMapping("/{dispositivoId}")
    public Dispositivo findAndUpdate(@PathVariable int dispositivoId,
                                  @RequestBody NewDispositivoPayload body){
        return dispositiviService.findByIdAndUpdate(dispositivoId, body);
    }

    @DeleteMapping("/{dispositivoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findAndDelete(@PathVariable int dispositivoId){
        dispositiviService.findByIdAndDelete(dispositivoId);
    }
}

