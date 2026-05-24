package br.uniesp.si.techback.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoritoDTO {

    @NotNull(message = "O ID do usuário é obrigatório")
    private UUID usuarioId;

    @NotNull(message = "O ID do conteúdo é obrigatório")
    private UUID conteudoId;

    // Campos extras preenchidos pelo Mapper/Service para facilitar a vida do Front-End
    private String conteudoTitulo;
    private String conteudoTipo; // FILME ou SERIE

    private LocalDateTime criadoEm;
}