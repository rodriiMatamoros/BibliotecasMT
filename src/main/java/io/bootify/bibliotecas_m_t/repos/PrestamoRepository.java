package io.bootify.bibliotecas_m_t.repos;

import io.bootify.bibliotecas_m_t.domain.Bibliotecarios;
import io.bootify.bibliotecas_m_t.domain.Lector;
import io.bootify.bibliotecas_m_t.domain.Libro;
import io.bootify.bibliotecas_m_t.domain.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    Prestamo findFirstByPrestamos(Libro libro);

    Prestamo findFirstByPrestamo(Lector lector);

    Prestamo findFirstByPrestamosId(Libro libro);

    Prestamo findFirstByPrestamoId2(Lector lector);

    Prestamo findFirstByPrestamosId3(Bibliotecarios bibliotecarios);

}
