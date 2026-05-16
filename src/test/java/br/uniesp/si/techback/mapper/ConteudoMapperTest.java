package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.ConteudoDTO;
import br.uniesp.si.techback.model.Conteudo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID; // IMPORTANTE

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testes do ConteudoMapper")
class ConteudoMapperTest {

    private ConteudoMapper conteudoMapper;
    private Conteudo filme;
    private ConteudoDTO conteudoDTO;
    private UUID idTeste;

    @BeforeEach
    void setUp() {
        conteudoMapper = new ConteudoMapper();
        idTeste = UUID.randomUUID(); // Gerando UUID para os testes

        // Criando usando SETTERS (já que sua Model não tem @Builder)
        filme = new Conteudo();
        filme.setId(idTeste);
        filme.setTitulo("Filme de Teste");
        filme.setSinopse("Sinopse do filme de teste");
        filme.setDataLancamento(LocalDate.of(2023, 1, 1));
        filme.setGenero("Ação");
        filme.setDuracaoMinutos(120);
        filme.setClassificacaoIndicativa("12 anos");

        // Criando DTO (Assumindo que o DTO ainda tem @Builder)
        conteudoDTO = ConteudoDTO.builder()
                .id(idTeste)
                .titulo("Filme de Teste")
                .sinopse("Sinopse do filme de teste")
                .dataLancamento(LocalDate.of(2023, 1, 1))
                .genero("Ação")
                .duracaoMinutos(120)
                .classificacaoIndicativa("12 anos")
                .build();
    }

    @Test
    @DisplayName("Deve converter Entity para DTO")
    void deveConverterEntityParaDTO() {
        ConteudoDTO resultado = conteudoMapper.toDTO(filme);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(filme.getId());
        assertThat(resultado.getTitulo()).isEqualTo(filme.getTitulo());
    }

    @Test
    @DisplayName("Deve converter DTO para Entity")
    void deveConverterDTOParaEntity() {
        Conteudo resultado = conteudoMapper.toEntity(conteudoDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(conteudoDTO.getId());
        assertThat(resultado.getTitulo()).isEqualTo(conteudoDTO.getTitulo());
    }

    @Test
    @DisplayName("Deve retornar null quando converter Entity null para DTO")
    void deveRetornarNullQuandoConverterEntityNullParaDTO() {
        assertThat(conteudoMapper.toDTO(null)).isNull();
    }

    @Test
    @DisplayName("Deve retornar null quando converter DTO null para Entity")
    void deveRetornarNullQuandoConverterDTONullParaEntity() {
        assertThat(conteudoMapper.toEntity(null)).isNull();
    }

    @Test
    @DisplayName("Deve converter Entity com campos nulos para DTO")
    void deveConverterEntityComCamposNulosParaDTO() {
        UUID idNovo = UUID.randomUUID();
        Conteudo filmeComNulos = new Conteudo();
        filmeComNulos.setId(idNovo);
        filmeComNulos.setTitulo("Filme com Nulos");

        ConteudoDTO resultado = conteudoMapper.toDTO(filmeComNulos);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(idNovo);
        assertThat(resultado.getSinopse()).isNull();
    }
}