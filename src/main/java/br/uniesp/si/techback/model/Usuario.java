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

    @Column(name = "cpf_cnpj", unique = true, length = 18)
    private String cpfCnpj;

    @Column(length = 150)
    private String razaoSocial;

    @Column(length = 100)
    private String nomeFantasia;


    @Column(nullable = false, length = 20)
    private String perfil; // ADMIN | USER

    // CAMPOS ADICIONADOS PARA A INTEGRAÇÃO DO VIACEP
    @Column(length = 10)
    private String cep;

    @Column(length = 150)
    private String logradouro;

    @Column(length = 100)
    private String bairro;

    @Column(length = 100)
    private String localidade;

    @Column(length = 2)
    private String uf;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime updatedEm;

    @ManyToOne
    @JoinColumn(name = "plano_id")
    private Plano plano;

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