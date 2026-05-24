package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Conteudo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConteudoRepository extends JpaRepository<Conteudo, UUID> {

    // 1. JPQL Customizada com Filtros Dinâmicos (REQUISITO CENTRAL DO INTEGRANTE 2)
    @Query("SELECT c FROM Conteudo c WHERE " +
            "(:termo IS NULL OR LOWER(c.titulo) LIKE LOWER(CONCAT('%', :termo, '%')) OR LOWER(c.sinopse) LIKE LOWER(CONCAT('%', :termo, '%'))) AND " +
            "(:tipo IS NULL OR c.tipo = :tipo) AND " +
            "(:genero IS NULL OR LOWER(c.genero) = LOWER(:genero))")
    List<Conteudo> buscarComFiltros(
            @Param("termo") String termo,
            @Param("tipo") String tipo,
            @Param("genero") String genero);

    // 2. Método JPQL para listagem ordenada (Ajustado o nome para Conteudos)
    @Query("select c from Conteudo c order by c.titulo asc")
    List<Conteudo> listarConteudosOrdenados();

    // 3. Derived Query equivalente à de cima (Spring Data gera o SQL sozinho)
    List<Conteudo> findAllByOrderByTituloAsc();

    // 4. Derived Query para busca exata por gênero e título
    Optional<Conteudo> findByGeneroAndTitulo(String genero, String titulo);

    // 5. JPQL para busca exata (Ajustado retorno para Optional por segurança)
    @Query("select c from Conteudo c where c.genero = :genero and c.titulo = :titulo")
    Optional<Conteudo> buscarPorGeneroETitulo(@Param("genero") String gen, @Param("titulo") String tit);
}