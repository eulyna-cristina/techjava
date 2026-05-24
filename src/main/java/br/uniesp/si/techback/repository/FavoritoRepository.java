package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Favorito;
import br.uniesp.si.techback.model.FavoritoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, FavoritoId> {

    // 1. JPQL Customizada exigida na especificação (Item 5: Favoritos recentes ordenados por data desc)
    @Query("SELECT f FROM Favorito f WHERE f.id.usuarioId = :usuarioId ORDER BY f.criadoEm DESC")
    List<Favorito> listarFavoritosRecentes(@Param("usuarioId") UUID usuarioId);

    // 2. Derived Query equivalente (Spring Data busca de dentro da chave composta id -> usuarioId)
    List<Favorito> findByIdUsuarioIdOrderByCriadoEmDesc(UUID usuarioId);
}