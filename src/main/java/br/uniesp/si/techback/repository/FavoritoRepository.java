package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.List;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, UUID> {
    // Esse é o método customizado que o FavoritoService está tentando usar na linha 49:
    List<Favorito> findByUsuarioId(UUID usuarioId);
}