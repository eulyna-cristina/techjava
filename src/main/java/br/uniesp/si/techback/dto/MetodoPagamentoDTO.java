package br.uniesp.si.techback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetodoPagamentoDTO {

    private UUID id;

    // ID do usuário dono desse método de pagamento
    private UUID usuarioId;

    // Ex: "CARTAO_CREDITO" ou "PIX"
    private String tipo;

    // O token fictício exigido pela especificação (Ex: "tok_iespflix_12345")
    private String tokenPagamento;

    // Apenas os 4 últimos dígitos para o Front exibir com segurança (Ex: "4321")
    private String ultimosDigitos;

    private Boolean preferencial;
}