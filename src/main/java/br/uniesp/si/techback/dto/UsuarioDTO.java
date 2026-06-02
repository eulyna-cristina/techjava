package br.uniesp.si.techback.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class UsuarioDTO {

    private UUID id;

    @NotBlank(message = "O nome completo é obrigatório")
    @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
    private String nome;

    @NotNull(message = "A data de nascimento é obrigatória")
    private LocalDate dataNascimento;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    @Size(max = 254, message = "O e-mail deve ter no máximo 254 caracteres")
    private String email;

    @NotBlank(message = "O CPF/CNPJ é obrigatório")
    @Size(max = 14, message = "O CPF/CNPJ deve ter no máximo 14 dígitos")
    private String cpfCnpj;

    @NotBlank(message = "O perfil é obrigatório")
    private String perfil; // ADMIN ou USER

    @NotBlank(message = "A senha é necessária para o cadastro")
    @Size(min = 8, message = "A senha deve conter no mínimo 8 caracteres") // Exigência do edital item 3
    private String senha;

    @NotBlank(message = "O CEP é obrigatório para validação externa")
    private String cep;

    private String nomePlano;


}