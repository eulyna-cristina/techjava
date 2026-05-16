package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.ConteudoDTO;
import br.uniesp.si.techback.model.Conteudo;
import org.springframework.stereotype.Component;

@Component
public class ConteudoMapper {

    public Conteudo toEntity(ConteudoDTO dto) {
        if (dto == null) {
            return null;
        }

        Conteudo entidade = new Conteudo();
        entidade.setId(dto.getId());
        entidade.setTitulo(dto.getTitulo());
        entidade.setTipo(dto.getTipo()); // NOVO
        entidade.setSinopse(dto.getSinopse());
        entidade.setAno(dto.getAno()); // NOVO
        entidade.setDataLancamento(dto.getDataLancamento());
        entidade.setGenero(dto.getGenero());
        entidade.setDuracaoMinutos(dto.getDuracaoMinutos());
        entidade.setClassificacaoIndicativa(dto.getClassificacaoIndicativa());
        entidade.setRelevancia(dto.getRelevancia()); // NOVO
        entidade.setTrailerUrl(dto.getTrailerUrl()); // NOVO

        return entidade;
    }

    public ConteudoDTO toDTO(Conteudo entity) {
        if (entity == null) {
            return null;
        }

        ConteudoDTO dto = new ConteudoDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setTipo(entity.getTipo()); // NOVO
        dto.setSinopse(entity.getSinopse());
        dto.setAno(entity.getAno()); // NOVO
        dto.setDataLancamento(entity.getDataLancamento());
        dto.setGenero(entity.getGenero());
        dto.setDuracaoMinutos(entity.getDuracaoMinutos());
        dto.setClassificacaoIndicativa(entity.getClassificacaoIndicativa());
        dto.setRelevancia(entity.getRelevancia()); // NOVO
        dto.setTrailerUrl(entity.getTrailerUrl()); // NOVO

        return dto;
    }
}