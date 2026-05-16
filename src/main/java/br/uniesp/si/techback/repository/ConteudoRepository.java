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

    // Mudado Filme para Conteudo aqui
    @Query("select c from Conteudo c order by c.titulo asc")
    List<Conteudo> listarFilmesOrdenados();

    List<Conteudo> findAllByOrderByTituloAsc();

    Optional<Conteudo> findByGeneroAndTitulo(String genero, String titulo);

    // Mudado Filme para Conteudo e ajustado o alias para 'c'
    @Query("select c from Conteudo c where c.genero = :genero and c.titulo = :titulo")
    Conteudo buscarPorGeneroETitulo(@Param("genero") String gen, @Param("titulo") String tit);
}