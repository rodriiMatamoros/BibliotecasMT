package io.bootify.bibliotecas_m_t.repos;

import io.bootify.bibliotecas_m_t.domain.Bibliotecarios;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BibliotecariosRepository extends JpaRepository<Bibliotecarios, Long> {

    boolean existsByGmailIgnoreCase(String gmail);

    boolean existsByPasswordIgnoreCase(String password);

}
