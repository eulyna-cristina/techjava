package br.uniesp.si.techback.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class CnpjResponseDTO {

    private String cnpj;

    @JsonAlias({"razao_social", "razaosocial", "razaoSocial"})
    private String razaoSocial;

    @JsonAlias({"nome_fantasia", "nomefantasia", "nomeFantasia"})
    private String nomeFantasia;

    private String telefone;
}