package br.uniesp.si.techback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal; // Importante para a relevância
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConteudoDTO {

    private UUID id;

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @NotBlank(message = "O tipo (FILME/SERIE) é obrigatório")
    private String tipo; // NOVO: Para diferenciar filmes de séries

    private String sinopse;

    private LocalDate dataLancamento;

    @NotNull(message = "O ano é obrigatório")
    private Integer ano; // NOVO: Requisito 2.2

    private String genero;

    private Integer duracaoMinutos;

    private String classificacaoIndicativa;

    private BigDecimal relevancia; // NOVO: Requisito 2.2 (ex: 9.5)

    private String trailerUrl; // NOVO: Requisito 2.2
}