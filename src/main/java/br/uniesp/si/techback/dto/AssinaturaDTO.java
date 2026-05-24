package br.uniesp.si.techback.dto;

import br.uniesp.si.techback.validation.ValidPeriodo;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidPeriodo // Nosso validador customizado de período cronológico!
public class AssinaturaDTO {

    private UUID id;

    @NotNull(message = "O ID do usuário é obrigatório")
    private UUID usuarioId;

    @NotNull(message = "O ID do plano é obrigatório")
    private UUID planoId;

    private UUID metodoPagamentoId;

    @NotNull(message = "A data de início é obrigatória")
    private LocalDate dataInicio;

    @NotNull(message = "A data de fim é obrigatória")
    private LocalDate dataFim;

    private Boolean ativo;
}