package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Data
public class MetodoPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String tipo; // Ex: "CARTAO_CREDITO", "PIX"

    private String tokenPagamento; // Aqui entra o "dado tokenizado" exigido (Ex: "tok_1NfX2e2e...")

    private String ultimosDigitos; // Ex: "4321" (para exibir visualmente no Front)

    private Boolean preferencial; // Se é o cartão principal do usuário
}