/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_RECEBIMENTO",
        sequenceName = "SEQ_RECEBIMENTO")
public class Recebimento {

    @Id
    @GeneratedValue(generator = "SEQ_RECEBIMENTO", strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(mappedBy = "recebimento")
    private List<TipoDeCobranca> tipoDeCobranca;

    @OneToMany(mappedBy = "recebimento")
    private List<FormaDeCobranca> formasDeCobranca;

    @OneToMany(mappedBy = "recebimento")
    private List<ValorPorCotacao> valoresPorCotacao;

    @Min(value = 0, message = "{min_dinheiro}")
    private BigDecimal totalEmDinheiro = BigDecimal.ZERO;

    @ManyToOne
    private Cotacao cotacaoPadrao;

    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;

    public Recebimento() {
    }

    public Recebimento(Long id, List<TipoDeCobranca> tipoDeCobranca, List<FormaDeCobranca> formasDeCobranca,
            List<ValorPorCotacao> valoresPorCotacao, Cotacao cotacaoPadrao, Date emissao) {
        this.id = id;
        this.tipoDeCobranca = tipoDeCobranca;
        this.formasDeCobranca = formasDeCobranca;
        this.valoresPorCotacao = valoresPorCotacao;
        this.cotacaoPadrao = cotacaoPadrao;
        this.emissao = emissao;
    }

    public void adiciona(TipoDeCobranca tipo) {
        if (tipoDeCobranca == null) {
            tipoDeCobranca = new ArrayList<>();
        }
        tipo.setRecebimento(this);
        tipoDeCobranca.add(tipo);
    }

    public void atualiza(TipoDeCobranca tipo) {
        tipoDeCobranca.set(tipoDeCobranca.indexOf(tipo), tipo);
    }

    public void remove(TipoDeCobranca tipo) {
        tipoDeCobranca.remove(tipo);
    }

    public void adiciona(FormaDeCobranca forma) {
        if (formasDeCobranca == null) {
            formasDeCobranca = new ArrayList<>();
        }
        forma.setRecebimento(this);
        formasDeCobranca.add(forma);
    }

    public void atualiza(FormaDeCobranca forma) {
        formasDeCobranca.set(formasDeCobranca.indexOf(forma), forma);
    }

    public void remove(FormaDeCobranca forma) {
        formasDeCobranca.remove(forma);
    }

    public Long getId() {
        return id;
    }

    public List<TipoDeCobranca> getTipoDeCobranca() {
        return tipoDeCobranca;
    }

    public List<FormaDeCobranca> getFormasDeCobranca() {
        return formasDeCobranca;
    }

    public List<ValorPorCotacao> getValoresPorCotacao() {
        return valoresPorCotacao;
    }

    public BigDecimal getTotalEmDinheiro() {
        return totalEmDinheiro;
    }

    public Cotacao getCotacaoPadrao() {
        return cotacaoPadrao;
    }

    public Date getEmissao() {
        return emissao;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Recebimento)) {
            return false;
        }
        Recebimento outro = (Recebimento) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
