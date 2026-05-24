package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "favorito")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Favorito {

    @EmbeddedId
    private FavoritoId id;

    // Muitos favoritos pertencem a um único usuário
    // insertable=false e updatable=false porque quem manda no valor é o FavoritoId
    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "FK_FAVORITO_USUARIO"))
    private Usuario usuario;

    // Muitos favoritos apontam para um único conteúdo
    @ManyToOne
    @MapsId("conteudoId")
    @JoinColumn(name = "conteudo_id", foreignKey = @ForeignKey(name = "FK_FAVORITO_CONTEUDO"))
    private Conteudo conteudo;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    // Método auxiliar do JPA executado automaticamente antes de salvar no banco
    @PrePersist
    protected void onCreate() {
        this.criadoEm = LocalDateTime.now();
    }
}