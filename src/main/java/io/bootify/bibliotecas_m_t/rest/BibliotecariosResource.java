package io.bootify.bibliotecas_m_t.rest;

import io.bootify.bibliotecas_m_t.model.BibliotecariosDTO;
import io.bootify.bibliotecas_m_t.service.BibliotecariosService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/bibliotecarioss", produces = MediaType.APPLICATION_JSON_VALUE)
public class BibliotecariosResource {

    private final BibliotecariosService bibliotecariosService;

    public BibliotecariosResource(final BibliotecariosService bibliotecariosService) {
        this.bibliotecariosService = bibliotecariosService;
    }

    @GetMapping
    public ResponseEntity<List<BibliotecariosDTO>> getAllBibliotecarioss() {
        return ResponseEntity.ok(bibliotecariosService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BibliotecariosDTO> getBibliotecarios(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(bibliotecariosService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createBibliotecarios(
            @RequestBody @Valid final BibliotecariosDTO bibliotecariosDTO) {
        final Long createdId = bibliotecariosService.create(bibliotecariosDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateBibliotecarios(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final BibliotecariosDTO bibliotecariosDTO) {
        bibliotecariosService.update(id, bibliotecariosDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBibliotecarios(@PathVariable(name = "id") final Long id) {
        bibliotecariosService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
