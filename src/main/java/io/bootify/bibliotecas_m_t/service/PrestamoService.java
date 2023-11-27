package io.bootify.bibliotecas_m_t.service;

import io.bootify.bibliotecas_m_t.domain.Bibliotecarios;
import io.bootify.bibliotecas_m_t.domain.Lector;
import io.bootify.bibliotecas_m_t.domain.Libro;
import io.bootify.bibliotecas_m_t.domain.Prestamo;
import io.bootify.bibliotecas_m_t.model.PrestamoDTO;
import io.bootify.bibliotecas_m_t.repos.BibliotecariosRepository;
import io.bootify.bibliotecas_m_t.repos.LectorRepository;
import io.bootify.bibliotecas_m_t.repos.LibroRepository;
import io.bootify.bibliotecas_m_t.repos.PrestamoRepository;
import io.bootify.bibliotecas_m_t.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final LibroRepository libroRepository;
    private final LectorRepository lectorRepository;
    private final BibliotecariosRepository bibliotecariosRepository;

    public PrestamoService(final PrestamoRepository prestamoRepository,
            final LibroRepository libroRepository, final LectorRepository lectorRepository,
            final BibliotecariosRepository bibliotecariosRepository) {
        this.prestamoRepository = prestamoRepository;
        this.libroRepository = libroRepository;
        this.lectorRepository = lectorRepository;
        this.bibliotecariosRepository = bibliotecariosRepository;
    }

    public List<PrestamoDTO> findAll() {
        final List<Prestamo> prestamoes = prestamoRepository.findAll(Sort.by("id"));
        return prestamoes.stream()
                .map(prestamo -> mapToDTO(prestamo, new PrestamoDTO()))
                .toList();
    }

    public PrestamoDTO get(final Long id) {
        return prestamoRepository.findById(id)
                .map(prestamo -> mapToDTO(prestamo, new PrestamoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PrestamoDTO prestamoDTO) {
        final Prestamo prestamo = new Prestamo();
        mapToEntity(prestamoDTO, prestamo);
        return prestamoRepository.save(prestamo).getId();
    }

    public void update(final Long id, final PrestamoDTO prestamoDTO) {
        final Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(prestamoDTO, prestamo);
        prestamoRepository.save(prestamo);
    }

    public void delete(final Long id) {
        prestamoRepository.deleteById(id);
    }

    private PrestamoDTO mapToDTO(final Prestamo prestamo, final PrestamoDTO prestamoDTO) {
        prestamoDTO.setId(prestamo.getId());
        prestamoDTO.setLibro(prestamo.getLibro());
        prestamoDTO.setLector(prestamo.getLector());
        prestamoDTO.setFecchaDePrestamo(prestamo.getFecchaDePrestamo());
        prestamoDTO.setFechaDeDevolucion(prestamo.getFechaDeDevolucion());
        prestamoDTO.setPrestamos(prestamo.getPrestamos() == null ? null : prestamo.getPrestamos().getId());
        prestamoDTO.setPrestamo(prestamo.getPrestamo() == null ? null : prestamo.getPrestamo().getId());
        prestamoDTO.setPrestamosId(prestamo.getPrestamosId() == null ? null : prestamo.getPrestamosId().getId());
        prestamoDTO.setPrestamoId2(prestamo.getPrestamoId2() == null ? null : prestamo.getPrestamoId2().getId());
        prestamoDTO.setPrestamosId3(prestamo.getPrestamosId3() == null ? null : prestamo.getPrestamosId3().getId());
        return prestamoDTO;
    }

    private Prestamo mapToEntity(final PrestamoDTO prestamoDTO, final Prestamo prestamo) {
        prestamo.setLibro(prestamoDTO.getLibro());
        prestamo.setLector(prestamoDTO.getLector());
        prestamo.setFecchaDePrestamo(prestamoDTO.getFecchaDePrestamo());
        prestamo.setFechaDeDevolucion(prestamoDTO.getFechaDeDevolucion());
        final Libro prestamos = prestamoDTO.getPrestamos() == null ? null : libroRepository.findById(prestamoDTO.getPrestamos())
                .orElseThrow(() -> new NotFoundException("prestamos not found"));
        prestamo.setPrestamos(prestamos);

        return prestamo;
    }

}
