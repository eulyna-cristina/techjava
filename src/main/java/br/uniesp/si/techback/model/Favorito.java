package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Muitos favoritos pertencem a um único usuário
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Muitos favoritos podem apontar para um único conteúdo (filme/série)
    @ManyToOne
    @JoinColumn(name = "conteudo_id")
    private Conteudo conteudo;
}