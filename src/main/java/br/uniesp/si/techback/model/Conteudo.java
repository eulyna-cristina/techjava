package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;
import java.time.LocalDate;

@Entity
@Table(name = "conteudo")
public class Conteudo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Alterado para UUID explicitamente
    private UUID id;

    @Column(nullable = false)
    private String titulo;

    @Column
    private String tipo;

    @Column(columnDefinition = "TEXT") // Bom para sinopses longas
    private String sinopse;

    @Column
    private Integer ano;

    @Column
    private LocalDate dataLancamento;

    @Column
    private String genero;

    @Column
    private Integer duracaoMinutos;

    @Column
    private String classificacaoIndicativa;

    @Column(precision = 4, scale = 2) // Define precisão para o BigDecimal
    private BigDecimal relevancia;

    @Column
    private String trailerUrl;

    // Construtor padrão obrigatório pelo JPA
    public Conteudo() {}

    // Getters e Setters Corrigidos
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public LocalDate getDataLancamento() { return dataLancamento; }
    public void setDataLancamento(LocalDate dataLancamento) { this.dataLancamento = dataLancamento; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public Integer getDuracaoMinutos() { return duracaoMinutos; }
    public void setDuracaoMinutos(Integer duracaoMinutos) { this.duracaoMinutos = duracaoMinutos; }

    public String getClassificacaoIndicativa() { return classificacaoIndicativa; }
    public void setClassificacaoIndicativa(String classificacaoIndicativa) { this.classificacaoIndicativa = classificacaoIndicativa; }

    // CORREÇÃO AQUI: Agora batendo com o tipo BigDecimal
    public BigDecimal getRelevancia() { return relevancia; }
    public void setRelevancia(BigDecimal relevancia) { this.relevancia = relevancia; }

    public String getTrailerUrl() { return trailerUrl; }
    public void setTrailerUrl(String trailerUrl) { this.trailerUrl = trailerUrl; }
}