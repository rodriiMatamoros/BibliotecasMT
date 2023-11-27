package io.bootify.bibliotecas_m_t.repos;

import io.bootify.bibliotecas_m_t.domain.Lector;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LectorRepository extends JpaRepository<Lector, Long> {

    boolean existsByCorreoIgnoreCase(String correo);

    boolean existsByLibroIgnoreCase(String libro);

}
