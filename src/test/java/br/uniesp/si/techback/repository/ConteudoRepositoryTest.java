package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Conteudo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID; // IMPORTANTE

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Testes do ConteudoRepository")
class ConteudoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ConteudoRepository conteudoRepository;

    private Conteudo filmeTeste;

    @BeforeEach
    void setUp() {
        // Usando NEW e SETTERS porque sua model não tem @Builder
        filmeTeste = new Conteudo();
        filmeTeste.setTitulo("Filme de Teste");
        filmeTeste.setSinopse("Sinopse do filme de teste");
        filmeTeste.setDataLancamento(LocalDate.of(2023, 1, 1));
        filmeTeste.setGenero("Ação");
        filmeTeste.setDuracaoMinutos(120);
        filmeTeste.setClassificacaoIndicativa("12 anos");
        filmeTeste.setRelevancia(new BigDecimal("9.5"));
    }

    @Test
    @DisplayName("Deve salvar um filme com sucesso")
    void deveSalvarFilme() {
        Conteudo filmeSalvo = conteudoRepository.save(filmeTeste);

        assertThat(filmeSalvo).isNotNull();
        assertThat(filmeSalvo.getId()).isNotNull(); // Verifica se o UUID foi gerado
        assertThat(filmeSalvo.getTitulo()).isEqualTo(filmeTeste.getTitulo());
    }

    @Test
    @DisplayName("Deve encontrar filme por ID quando existir")
    void deveEncontrarFilmePorId() {
        Conteudo filmeSalvo = entityManager.persistAndFlush(filmeTeste);

        Optional<Conteudo> filmeEncontrado = conteudoRepository.findById(filmeSalvo.getId());

        assertThat(filmeEncontrado).isPresent();
        assertThat(filmeEncontrado.get().getId()).isEqualTo(filmeSalvo.getId());
    }

    @Test
    @DisplayName("Deve retornar vazio quando buscar por ID inexistente")
    void deveRetornarVazioQuandoBuscarPorIdInexistente() {
        // Criando um UUID aleatório que com certeza não existe no banco
        Optional<Conteudo> filmeEncontrado = conteudoRepository.findById(UUID.randomUUID());

        assertThat(filmeEncontrado).isEmpty();
    }

    @Test
    @DisplayName("Deve listar todos os filmes")
    void deveListarTodosOsFilmes() {
        entityManager.persistAndFlush(filmeTeste);

        Conteudo filme2 = new Conteudo();
        filme2.setTitulo("Filme de Teste 2");
        filme2.setGenero("Comédia");
        entityManager.persistAndFlush(filme2);

        List<Conteudo> filmes = conteudoRepository.findAll();

        assertThat(filmes).hasSize(2);
    }

    @Test
    @DisplayName("Deve deletar filme por ID")
    void deveDeletarFilmePorId() {
        Conteudo filmeSalvo = entityManager.persistAndFlush(filmeTeste);
        UUID id = filmeSalvo.getId();

        conteudoRepository.deleteById(id);

        Optional<Conteudo> filmeDeletado = conteudoRepository.findById(id);
        assertThat(filmeDeletado).isEmpty();
    }
}