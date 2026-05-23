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
public class FuncionarioDTO {

    private UUID id;

    @NotBlank(message = "O nome do funcionário é obrigatório")
    private String nome;

    @NotBlank(message = "O e-mail corporativo é obrigatório")
    @Email(message = "O e-mail deve ser válido")
    private String email;

    @NotBlank(message = "A matrícula ou registro funcional é obrigatória")
    private String matricula;

    // Ex: "ADMIN", "GERENTE_CONTEUDO", "SUPORTE"
    @NotBlank(message = "O cargo/permissão deve ser informado")
    private String cargo;

    private Boolean ativo;
}