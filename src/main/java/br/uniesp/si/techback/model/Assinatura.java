package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
public class Assinatura {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne // Um usuário tem uma assinatura ativa
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne // Muitos usuários podem ter o mesmo tipo de plano (Ex: Premium)
    @JoinColumn(name = "plano_id")
    private Plano plano;

    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Boolean ativo;
}