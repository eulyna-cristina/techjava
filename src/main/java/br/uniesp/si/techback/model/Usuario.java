package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome_completo", nullable = false, length = 150)
    private String nomeCompleto;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false, unique = true, length = 254)
    private String email;

    @Column(name = "senha_hash", nullable = false, length = 60)
    private String senhaHash;

    @Column(name = "cpf_cnpj", unique = true, length = 14)
    private String cpfCnpj;

    @Column(nullable = false, length = 20)
    private String perfil; // ADMIN | USER

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime updatedEm;

    // Relacionamento opcional mantido para o seu endpoint de vincular plano
    @ManyToOne
    @JoinColumn(name = "plano_id")
    private Plano plano;

    // Atualiza as datas de criação e modificação automaticamente
    @PrePersist
    protected void onCreate() {
        criadoEm = LocalDateTime.now();
        updatedEm = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedEm = LocalDateTime.now();
    }
}