package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.ConteudoDTO;
import br.uniesp.si.techback.service.ConteudoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean; // Importado o MockBean correto para o Spring 3.3
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConteudoController.class)
@DisplayName("Testes do ConteudoController")
class ConteudoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // Alterado de @MockitoBean para @MockBean
    private ConteudoService conteudoService;

    @Autowired
    private ObjectMapper objectMapper;

    private ConteudoDTO conteudoDTO;
    private ConteudoDTO conteudoSalvoDTO;
    private UUID idTeste;

    @BeforeEach
    void setUp() {
        idTeste = UUID.randomUUID(); // Gerando um UUID para os testes

        conteudoDTO = ConteudoDTO.builder()
                .titulo("Inception")
                .tipo("FILME")
                .sinopse("Um ladrão que rouba segredos...")
                .ano(2010) // Corrigido: sem o (integer) quebrado
                .genero("Ficção Científica")
                .relevancia(new BigDecimal("9.8"))
                .build();

        conteudoSalvoDTO = ConteudoDTO.builder()
                .id(idTeste) // Usando UUID aqui
                .titulo("Inception")
                .tipo("FILME")
                .sinopse("Um ladrão que rouba segredos...")
                .ano(2010) // Corrigido: sem o (interger) quebrado
                .genero("Ficção Científica")
                .relevancia(new BigDecimal("9.8"))
                .build();
    }

    @Test
    @DisplayName("Deve listar todos os conteúdos")
    void deveListarTodosOsConteudos() throws Exception {
        List<ConteudoDTO> lista = Arrays.asList(conteudoSalvoDTO);
        when(conteudoService.listar()).thenReturn(lista);

        mockMvc.perform(get("/conteudos")) // Rota atualizada
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(idTeste.toString()))
                .andExpect(jsonPath("$[0].titulo").value("Inception"));
    }

    @Test
    @DisplayName("Deve buscar por ID quando existir")
    void deveBuscarPorIdQuandoExistir() throws Exception {
        when(conteudoService.buscarPorId(idTeste)).thenReturn(conteudoSalvoDTO);

        mockMvc.perform(get("/conteudos/" + idTeste))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idTeste.toString()));
    }

    @Test
    @DisplayName("Deve criar um novo conteúdo")
    void deveCriarNovoConteudo() throws Exception {
        when(conteudoService.salvar(any(ConteudoDTO.class))).thenReturn(conteudoSalvoDTO);

        mockMvc.perform(post("/conteudos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(conteudoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(idTeste.toString()));
    }

    @Test
    @DisplayName("Deve excluir um conteúdo existente")
    void deveExcluirConteudoExistente() throws Exception {
        // Como o método é void, não precisamos de 'when'
        mockMvc.perform(delete("/conteudos/" + idTeste))
                .andExpect(status().isNoContent());
    }
}