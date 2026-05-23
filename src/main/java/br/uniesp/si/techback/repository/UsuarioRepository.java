package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    List<Usuario> findAllByOrderByNomeCompletoAsc();
}