package br.uniesp.si.techback.client;

import br.uniesp.si.techback.dto.ViaCepResponseDTO; // Importe o DTO do seu projeto
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viaCepClient", url = "https://viacep.com.br/ws")
public interface ViaCepClient {

    // Modificado para retornar ViaCepResponseDTO e com o nome buscarPorCep
    @GetMapping("/{cep}/json/")
    ViaCepResponseDTO buscarPorCep(@PathVariable("cep") String cep);
}