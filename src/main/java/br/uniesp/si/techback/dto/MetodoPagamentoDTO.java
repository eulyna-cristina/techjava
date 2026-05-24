package br.uniesp.si.techback.dto;

import br.uniesp.si.techback.validation.ValidTipoPagamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetodoPagamentoDTO {

    private UUID id;
    private UUID usuarioId;

    @NotBlank(message = "O tipo de pagamento é obrigatório")
    @ValidTipoPagamento // Nosso validador customizado!
    private String tipo;

    private String tokenPagamento;

    @Size(min = 4, max = 4, message = "Os últimos dígitos devem conter exatamente 4 caracteres")
    private String ultimosDigitos;

    private Boolean preferencial;
}