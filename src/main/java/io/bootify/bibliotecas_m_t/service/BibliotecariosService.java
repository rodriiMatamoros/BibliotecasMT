package io.bootify.bibliotecas_m_t.service;

import io.bootify.bibliotecas_m_t.domain.Bibliotecarios;
import io.bootify.bibliotecas_m_t.domain.Prestamo;
import io.bootify.bibliotecas_m_t.model.BibliotecariosDTO;
import io.bootify.bibliotecas_m_t.repos.BibliotecariosRepository;
import io.bootify.bibliotecas_m_t.repos.PrestamoRepository;
import io.bootify.bibliotecas_m_t.util.NotFoundException;
import io.bootify.bibliotecas_m_t.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BibliotecariosService {

    private final BibliotecariosRepository bibliotecariosRepository;
    private final PrestamoRepository prestamoRepository;

    public BibliotecariosService(final BibliotecariosRepository bibliotecariosRepository,
            final PrestamoRepository prestamoRepository) {
        this.bibliotecariosRepository = bibliotecariosRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public List<BibliotecariosDTO> findAll() {
        final List<Bibliotecarios> bibliotecarioses = bibliotecariosRepository.findAll(Sort.by("id"));
        return bibliotecarioses.stream()
                .map(bibliotecarios -> mapToDTO(bibliotecarios, new BibliotecariosDTO()))
                .toList();
    }

    public BibliotecariosDTO get(final Long id) {
        return bibliotecariosRepository.findById(id)
                .map(bibliotecarios -> mapToDTO(bibliotecarios, new BibliotecariosDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final BibliotecariosDTO bibliotecariosDTO) {
        final Bibliotecarios bibliotecarios = new Bibliotecarios();
        mapToEntity(bibliotecariosDTO, bibliotecarios);
        return bibliotecariosRepository.save(bibliotecarios).getId();
    }

    public void update(final Long id, final BibliotecariosDTO bibliotecariosDTO) {
        final Bibliotecarios bibliotecarios = bibliotecariosRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(bibliotecariosDTO, bibliotecarios);
        bibliotecariosRepository.save(bibliotecarios);
    }

    public void delete(final Long id) {
        bibliotecariosRepository.deleteById(id);
    }

    private BibliotecariosDTO mapToDTO(final Bibliotecarios bibliotecarios,
            final BibliotecariosDTO bibliotecariosDTO) {
        bibliotecariosDTO.setId(bibliotecarios.getId());
        bibliotecariosDTO.setNombre(bibliotecarios.getNombre());
        bibliotecariosDTO.setApellido(bibliotecarios.getApellido());
        bibliotecariosDTO.setGmail(bibliotecarios.getGmail());
        bibliotecariosDTO.setPassword(bibliotecarios.getPassword());
        return bibliotecariosDTO;
    }

    private Bibliotecarios mapToEntity(final BibliotecariosDTO bibliotecariosDTO,
            final Bibliotecarios bibliotecarios) {
        bibliotecarios.setNombre(bibliotecariosDTO.getNombre());
        bibliotecarios.setApellido(bibliotecariosDTO.getApellido());
        bibliotecarios.setGmail(bibliotecariosDTO.getGmail());
        bibliotecarios.setPassword(bibliotecariosDTO.getPassword());
        return bibliotecarios;
    }

    public boolean gmailExists(final String gmail) {
        return bibliotecariosRepository.existsByGmailIgnoreCase(gmail);
    }

    public boolean passwordExists(final String password) {
        return bibliotecariosRepository.existsByPasswordIgnoreCase(password);
    }

    public String getReferencedWarning(final Long id) {
        final Bibliotecarios bibliotecarios = bibliotecariosRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Prestamo prestamosId3Prestamo = prestamoRepository.findFirstByPrestamosId3(bibliotecarios);
        if (prestamosId3Prestamo != null) {
            return WebUtils.getMessage("bibliotecarios.prestamo.prestamosId3.referenced", prestamosId3Prestamo.getId());
        }
        return null;
    }

}
