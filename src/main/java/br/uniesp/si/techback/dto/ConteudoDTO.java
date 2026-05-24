package br.uniesp.si.techback.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private String tipo; // Para diferenciar filmes de séries

    private String sinopse;

    private LocalDate dataLancamento;

    @NotNull(message = "O ano é obrigatório")
    @Min(value = 1888, message = "O ano de lançamento não pode ser anterior a 1888") // Ano do primeiro filme da história
    @Max(value = 2030, message = "O ano de lançamento não pode ser em um futuro tão distante")
    private Integer ano;

    private String genero;

    @NotNull(message = "A duração é obrigatória")
    @Positive(message = "A duração em minutos deve ser um valor maior que zero")
    private Integer duracaoMinutos;

    private String classificacaoIndicativa;

    @NotNull(message = "A relevância é obrigatória")
    @Min(value = 0, message = "A relevância mínima é 0")
    @Max(value = 100, message = "A relevância máxima é 100") // Padrão de mercado (ex: 95% relevante)
    private BigDecimal relevancia;

    private String trailerUrl;
}