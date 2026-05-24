package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Plano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface PlanoRepository extends JpaRepository<Plano, UUID> {

    // Consulta JPQL customizada exigida pelo roteiro do professor
    @Query("SELECT p FROM Plano p WHERE p.preco <= :precoMaximo ORDER BY p.preco ASC")
    List<Plano> buscarPlanosMaisBaratosQue(@Param("precoMaximo") BigDecimal precoMaximo);
}