package io.bootify.bibliotecas_m_t.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "Prestamoes")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Prestamo {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String libro;

    @Column
    private String lector;

    @Column
    private String fecchaDePrestamo;

    @Column
    private String fechaDeDevolucion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestamos_id")
    private Libro prestamos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestamo_id")
    private Lector prestamo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestamos_id_id", nullable = false)
    private Libro prestamosId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestamo_id2id", nullable = false)
    private Lector prestamoId2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestamos_id3id")
    private Bibliotecarios prestamosId3;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
