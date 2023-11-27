package io.bootify.bibliotecas_m_t.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PrestamoDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String libro;

    @Size(max = 255)
    private String lector;

    @Size(max = 255)
    private String fecchaDePrestamo;

    @Size(max = 255)
    private String fechaDeDevolucion;

    private Long prestamos;

    private Long prestamo;

    @NotNull
    private Long prestamosId;

    @NotNull
    private Long prestamoId2;

    private Long prestamosId3;

}
