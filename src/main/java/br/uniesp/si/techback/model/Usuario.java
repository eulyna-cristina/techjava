package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    @NotBlank(message = "CPF/CNPJ é obrigatório")
    @Column(unique = true, nullable = false)
    private String cpfCnpj;

    // Aqui definimos se é ADMIN ou USER comum
    @Column(nullable = false)
    private String perfil;

    // Relacionamento com Plano (Um usuário tem um plano)
    @ManyToOne
    @JoinColumn(name = "plano_id")
    private Plano plano;
}