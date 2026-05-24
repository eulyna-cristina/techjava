package br.uniesp.si.techback.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoritoId implements Serializable {

    private UUID usuarioId;
    private UUID conteudoId;
}