package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Assinatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssinaturaRepository extends JpaRepository<Assinatura, UUID> {

    // JPQL Customizada para buscar a assinatura ativa de um determinado usuário
    @Query("SELECT a FROM Assinatura a WHERE a.usuario.id = :usuarioId AND a.ativo = true")
    Optional<Assinatura> buscarAssinaturaAtivaDoUsuario(@Param("usuarioId") UUID usuarioId);
}