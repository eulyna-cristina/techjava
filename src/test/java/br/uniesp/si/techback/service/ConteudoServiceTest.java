package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.ConteudoDTO;
import br.uniesp.si.techback.mapper.ConteudoMapper;
import br.uniesp.si.techback.model.Conteudo;
import br.uniesp.si.techback.repository.ConteudoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID; // Adicionado UUID

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do ConteudoService")
class ConteudoServiceTest {

    @Mock
    private ConteudoRepository conteudoRepository;

    @Mock
    private ConteudoMapper conteudoMapper;

    @InjectMocks
    private ConteudoService conteudoService;

    private Conteudo filme;
    private ConteudoDTO conteudoDTO;
    private UUID idTeste;

    @BeforeEach
    void setUp() {
        idTeste = UUID.randomUUID();

        // Usando NEW e SETTERS (conforme sua model manual)
        filme = new Conteudo();
        filme.setId(idTeste);
        filme.setTitulo("Filme de Teste");
        filme.setSinopse("Sinopse do filme de teste");
        filme.setDataLancamento(LocalDate.of(2023, 1, 1));
        filme.setGenero("Ação");

        // DTO (Assumindo que o DTO ainda usa @Builder do Lombok)
        conteudoDTO = ConteudoDTO.builder()
                .id(idTeste)
                .titulo("Filme de Teste")
                .sinopse("Sinopse do filme de teste")
                .dataLancamento(LocalDate.of(2023, 1, 1))
                .genero("Ação")
                .build();
    }

    @Test
    @DisplayName("Deve listar todos os filmes")
    void deveListarTodosOsFilmes() {
        when(conteudoRepository.findAll()).thenReturn(Arrays.asList(filme));
        when(conteudoMapper.toDTO(filme)).thenReturn(conteudoDTO);

        List<ConteudoDTO> resultado = conteudoService.listar();

        assertThat(resultado).hasSize(1);
        verify(conteudoRepository).findAll();
    }

    @Test
    @DisplayName("Deve buscar filme por ID quando existir")
    void deveBuscarFilmePorIdQuandoExistir() {
        when(conteudoRepository.findById(idTeste)).thenReturn(Optional.of(filme));
        when(conteudoMapper.toDTO(filme)).thenReturn(conteudoDTO);

        ConteudoDTO resultado = conteudoService.buscarPorId(idTeste);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(idTeste);
    }

    @Test
    @DisplayName("Deve lançar exceção quando buscar filme por ID inexistente")
    void deveLancarExcecaoQuandoBuscarFilmePorIdInexistente() {
        UUID idInexistente = UUID.randomUUID();
        when(conteudoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> conteudoService.buscarPorId(idInexistente))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Deve excluir um filme existente")
    void deveExcluirFilmeExistente() {
        when(conteudoRepository.existsById(idTeste)).thenReturn(true);

        conteudoService.excluir(idTeste);

        verify(conteudoRepository).deleteById(idTeste);
    }
}