package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.MetodoPagamentoDTO;
import br.uniesp.si.techback.model.MetodoPagamento;

public class MetodoPagamentoMapper {

    /**
     * Converte uma Entidade (Banco de Dados) para um DTO (API/Visualização)
     */
    public static MetodoPagamentoDTO paraDTO(MetodoPagamento entidade) {
        if (entidade == null) {
            return null;
        }

        return MetodoPagamentoDTO.builder()
                .id(entidade.getId())
                .usuarioId(entidade.getUsuario() != null ? entidade.getUsuario().getId() : null)
                .tipo(entidade.getTipo())
                .tokenPagamento(entidade.getTokenPagamento())
                .ultimosDigitos(entidade.getUltimosDigitos())
                .preferencial(entidade.getPreferencial())
                .build();
    }

    /**
     * Converte um DTO (API) para uma Entidade (Banco de Dados)
     * Nota: O relacionamento com a entidade Usuario deve ser estabelecido no Service.
     */
    public static MetodoPagamento paraEntidade(MetodoPagamentoDTO dto) {
        if (dto == null) {
            return null;
        }

        MetodoPagamento entidade = new MetodoPagamento();
        entidade.setId(dto.getId());
        entidade.setTipo(dto.getTipo() != null ? dto.getTipo().toUpperCase() : null);
        entidade.setTokenPagamento(dto.getTokenPagamento());
        entidade.setUltimosDigitos(dto.getUltimosDigitos());
        entidade.setPreferencial(dto.getPreferencial() != null ? dto.getPreferencial() : false);

        return entidade;
    }
}