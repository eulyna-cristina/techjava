package br.uniesp.si.techback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssinaturaDTO {

    private UUID id;

    // Passamos apenas o ID do usuário e do plano no DTO para simplificar o JSON
    private UUID usuarioId;
    private UUID planoId;

    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Boolean ativo;
}