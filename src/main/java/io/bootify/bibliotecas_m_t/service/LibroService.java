package io.bootify.bibliotecas_m_t.service;

import io.bootify.bibliotecas_m_t.domain.Libro;
import io.bootify.bibliotecas_m_t.domain.Prestamo;
import io.bootify.bibliotecas_m_t.model.LibroDTO;
import io.bootify.bibliotecas_m_t.repos.LibroRepository;
import io.bootify.bibliotecas_m_t.repos.PrestamoRepository;
import io.bootify.bibliotecas_m_t.util.NotFoundException;
import io.bootify.bibliotecas_m_t.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final PrestamoRepository prestamoRepository;

    public LibroService(final LibroRepository libroRepository,
            final PrestamoRepository prestamoRepository) {
        this.libroRepository = libroRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public List<LibroDTO> findAll() {
        final List<Libro> libroes = libroRepository.findAll(Sort.by("id"));
        return libroes.stream()
                .map(libro -> mapToDTO(libro, new LibroDTO()))
                .toList();
    }

    public LibroDTO get(final Long id) {
        return libroRepository.findById(id)
                .map(libro -> mapToDTO(libro, new LibroDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LibroDTO libroDTO) {
        final Libro libro = new Libro();
        mapToEntity(libroDTO, libro);
        return libroRepository.save(libro).getId();
    }

    public void update(final Long id, final LibroDTO libroDTO) {
        final Libro libro = libroRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(libroDTO, libro);
        libroRepository.save(libro);
    }

    public void delete(final Long id) {
        libroRepository.deleteById(id);
    }

    private LibroDTO mapToDTO(final Libro libro, final LibroDTO libroDTO) {
        libroDTO.setId(libro.getId());
        libroDTO.setTitulo(libro.getTitulo());
        libroDTO.setAutor(libro.getAutor());
        libroDTO.setPaginas(libro.getPaginas());
        return libroDTO;
    }

    private Libro mapToEntity(final LibroDTO libroDTO, final Libro libro) {
        libro.setTitulo(libroDTO.getTitulo());
        libro.setAutor(libroDTO.getAutor());
        libro.setPaginas(libroDTO.getPaginas());
        return libro;
    }

    public String getReferencedWarning(final Long id) {
        final Libro libro = libroRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Prestamo prestamosPrestamo = prestamoRepository.findFirstByPrestamos(libro);
        if (prestamosPrestamo != null) {
            return WebUtils.getMessage("libro.prestamo.prestamos.referenced", prestamosPrestamo.getId());
        }
        final Prestamo prestamosIdPrestamo = prestamoRepository.findFirstByPrestamosId(libro);
        if (prestamosIdPrestamo != null) {
            return WebUtils.getMessage("libro.prestamo.prestamosId.referenced", prestamosIdPrestamo.getId());
        }
        return null;
    }

}
