package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.MetodoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface MetodoPagamentoRepository extends JpaRepository<MetodoPagamento, UUID> {
    // Ao transformar em interface e estender o JpaRepository,
    // o Spring cria magicamente todos os métodos de CRUD (save, findById, delete, etc.)
}