package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;
import java.time.LocalDate;

@Entity
@Table(name = "conteudo")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Conteudo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String tipo;

    @Column(columnDefinition = "TEXT")
    private String sinopse;

    @Column(nullable = false)
    private Integer ano;

    @Column
    private LocalDate dataLancamento;

    @Column
    private String genero;

    @Column(nullable = false)
    private Integer duracaoMinutos;

    @Column
    private String classificacaoIndicativa;

    @Column(precision = 5, scale = 2, nullable = false) // Ajustado para suportar até 100.00
    private BigDecimal relevancia;

    @Column
    private String trailerUrl;
}