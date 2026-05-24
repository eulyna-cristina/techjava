package br.uniesp.si.techback.dto;

import br.uniesp.si.techback.validation.PlanoValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanoDTO {

    private UUID id;

    @NotBlank(message = "O nome do plano é obrigatório")
    @PlanoValidation // Nosso validador customizado em ação
    private String nome;

    private String descricao;

    @NotNull(message = "O preço do plano é obrigatório")
    @Positive(message = "O preço do plano deve ser um valor maior que zero")
    private BigDecimal preco;
}