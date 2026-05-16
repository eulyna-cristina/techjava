package br.uniesp.si.techback.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private UUID id;

    @NotBlank(message = "O nome não pode estar em branco")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    private String email;

    @NotBlank(message = "O CPF/CNPJ é obrigatório")
    // Se o professor exigir validação real de CPF, usamos @CPF do Hibernate
    private String cpfCnpj;

    private String perfil; // ADMIN ou USER

    @NotBlank(message = "A senha é necessária para o cadastro")
    private String senha;

    // Aqui mostramos apenas o nome do plano que ele assinou
    private String nomePlano;
}
