package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.repository.PlanoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlanoService {

    private final PlanoRepository planoRepository;

    @Transactional
    public Plano salvar(Plano plano) {
        log.info("Salvando novo plano: {}", plano.getNome());
        return planoRepository.save(plano);
    }

    public List<Plano> listarTodos() {
        return planoRepository.findAll();
    }

    public Plano buscarPorId(UUID id) {
        return planoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado com o ID: " + id));
    }

    @Transactional
    public void excluir(UUID id) {
        if (!planoRepository.existsById(id)) {
            throw new RuntimeException("Não é possível excluir: Plano inexistente.");
        }
        planoRepository.deleteById(id);
    }
}
