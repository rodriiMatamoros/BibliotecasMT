package io.bootify.bibliotecas_m_t.service;

import io.bootify.bibliotecas_m_t.domain.Lector;
import io.bootify.bibliotecas_m_t.domain.Prestamo;
import io.bootify.bibliotecas_m_t.model.LectorDTO;
import io.bootify.bibliotecas_m_t.repos.LectorRepository;
import io.bootify.bibliotecas_m_t.repos.PrestamoRepository;
import io.bootify.bibliotecas_m_t.util.NotFoundException;
import io.bootify.bibliotecas_m_t.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LectorService {

    private final LectorRepository lectorRepository;
    private final PrestamoRepository prestamoRepository;

    public LectorService(final LectorRepository lectorRepository,
            final PrestamoRepository prestamoRepository) {
        this.lectorRepository = lectorRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public List<LectorDTO> findAll() {
        final List<Lector> lectors = lectorRepository.findAll(Sort.by("id"));
        return lectors.stream()
                .map(lector -> mapToDTO(lector, new LectorDTO()))
                .toList();
    }

    public LectorDTO get(final Long id) {
        return lectorRepository.findById(id)
                .map(lector -> mapToDTO(lector, new LectorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LectorDTO lectorDTO) {
        final Lector lector = new Lector();
        mapToEntity(lectorDTO, lector);
        return lectorRepository.save(lector).getId();
    }

    public void update(final Long id, final LectorDTO lectorDTO) {
        final Lector lector = lectorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(lectorDTO, lector);
        lectorRepository.save(lector);
    }

    public void delete(final Long id) {
        lectorRepository.deleteById(id);
    }

    private LectorDTO mapToDTO(final Lector lector, final LectorDTO lectorDTO) {
        lectorDTO.setId(lector.getId());
        lectorDTO.setNombre(lector.getNombre());
        lectorDTO.setApellido(lector.getApellido());
        lectorDTO.setCorreo(lector.getCorreo());
        lectorDTO.setLibro(lector.getLibro());
        return lectorDTO;
    }

    private Lector mapToEntity(final LectorDTO lectorDTO, final Lector lector) {
        lector.setNombre(lectorDTO.getNombre());
        lector.setApellido(lectorDTO.getApellido());
        lector.setCorreo(lectorDTO.getCorreo());
        lector.setLibro(lectorDTO.getLibro());
        return lector;
    }

    public boolean correoExists(final String correo) {
        return lectorRepository.existsByCorreoIgnoreCase(correo);
    }

    public boolean libroExists(final String libro) {
        return lectorRepository.existsByLibroIgnoreCase(libro);
    }

    public String getReferencedWarning(final Long id) {
        final Lector lector = lectorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Prestamo prestamoPrestamo = prestamoRepository.findFirstByPrestamo(lector);
        if (prestamoPrestamo != null) {
            return WebUtils.getMessage("lector.prestamo.prestamo.referenced", prestamoPrestamo.getId());
        }
        final Prestamo prestamoId2Prestamo = prestamoRepository.findFirstByPrestamoId2(lector);
        if (prestamoId2Prestamo != null) {
            return WebUtils.getMessage("lector.prestamo.prestamoId2.referenced", prestamoId2Prestamo.getId());
        }
        return null;
    }

}
