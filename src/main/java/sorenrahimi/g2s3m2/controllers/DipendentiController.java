package sorenrahimi.g2s3m2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sorenrahimi.g2s3m2.entities.Dipendente;
import sorenrahimi.g2s3m2.services.DipendentiService;

@RestController
@RequestMapping("/dipendenti")
public class DipendentiController {
    @Autowired
    private DipendentiService dipendentiService;

    @GetMapping
    @PreAuthorize("hasAuthority('STUDENTE')")
    public Page<Dipendente> getDipendenti(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String sortBy) {
        return this.dipendentiService.getDipendenti(page, size, sortBy);
    }

    @GetMapping("/me")
    public Dipendente getProfile(@AuthenticationPrincipal Dipendente currentAuthenticatedDipendente ){
        return currentAuthenticatedDipendente;
    }
    @PutMapping("/me")
    public Dipendente updateProfile(@AuthenticationPrincipal Dipendente currentAuthenticatedDipendente, @RequestBody Dipendente updateDipendente){
        return this.dipendentiService.findByIdAndUpdate(currentAuthenticatedDipendente.getId(), updateDipendente);
    }
    @DeleteMapping("/me")
    public void deleteProfile(@AuthenticationPrincipal Dipendente currentAuthenticatedDipendente){
        this.dipendentiService.findByIdAndDelete(currentAuthenticatedDipendente.getId());
    }



    @GetMapping("/{dipendenteId}")
    @PreAuthorize("hasAuthority('DIPENDENTE')")
    public Dipendente findById(@PathVariable int dipendenteId){
        return this.dipendentiService.findById(dipendenteId);
    }

    @PutMapping("/{dipendenteId}")
    @PreAuthorize("hasAuthority('DIPENDENTE')")
    private Dipendente findUserByIdAndUpdate(@PathVariable int dipendenteId, @RequestBody Dipendente body){
        return this.dipendentiService.findByIdAndUpdate(dipendenteId, body);
    }

    @DeleteMapping("/{dipendenteId}")
    @PreAuthorize("hasAuthority('STUDENTE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void findByIdAndDelete(@PathVariable int dipendenteId){
        this.dipendentiService.findByIdAndDelete(dipendenteId);
    }
}


