/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.BaixaDAO;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.valueobjects.EstadoDeLancamento;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import org.hibernate.Hibernate;
import org.hibernate.validator.cdi.HibernateValidator;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_PAGAMENTO",
        sequenceName = "SEQ_PAGAMENTO")
public class Pagamento {

    @Id
    @GeneratedValue(generator = "SEQ_PAGAMENTO", strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(mappedBy = "pagamento", cascade = CascadeType.ALL)
    private List<TipoDeCobranca> tipoDeCobranca;

    @OneToMany(mappedBy = "pagamento", cascade = CascadeType.ALL)
    private List<FormaDeCobranca> formasDeCobranca;

    @Min(value = 0, message = "{min_dinheiro}")
    private BigDecimal totalEmDinheiro = BigDecimal.ZERO;

    @ManyToOne
    private Cotacao cotacaoPadrao;

    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;

    @Enumerated(EnumType.STRING)
    private EstadoDeLancamento estado;

    @ManyToOne
    private Caixa caixa;

    public Pagamento() {
    }

    public Pagamento(Long id, List<TipoDeCobranca> tipoDeCobranca, List<FormaDeCobranca> formasDeCobranca,
            Cotacao cotacaoPadrao, Date emissao, BigDecimal totalEmDinheiro, EstadoDeLancamento estado, Caixa caixa) {
        this.id = id;
        this.tipoDeCobranca = tipoDeCobranca;
        this.formasDeCobranca = formasDeCobranca;
        this.cotacaoPadrao = cotacaoPadrao;
        this.emissao = emissao;
        this.totalEmDinheiro = totalEmDinheiro;
        this.estado = estado;
        this.caixa = caixa;
    }

    public void ehValido() throws DadoInvalidoException {
        if ((tipoDeCobranca == null || tipoDeCobranca.isEmpty()) && (formasDeCobranca == null || formasDeCobranca.isEmpty())) {
            throw new EDadoInvalidoException("Deve_possuir_recebimentos_informados");
        }
    }

    public void adiciona(TipoDeCobranca tipo) {
        if (tipoDeCobranca == null) {
            tipoDeCobranca = new ArrayList<>();
        }
        tipo.setPagamento(this);
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
        forma.setPagamento(this);
        formasDeCobranca.add(forma);
    }

    public void atualiza(FormaDeCobranca forma) {
        formasDeCobranca.set(formasDeCobranca.indexOf(forma), forma);
    }

    public void atualizaBaixas(FormaDeCobranca forma) {
        List<Baixa> bx = new BaixaDAO().buscarBaixasW().ePorFormaDeCobranca(forma).listaDeResultados();
        bx.forEach((b) -> {
            b.atualizaValor(forma.getValor());
        });
    }

    public void atualizaBaixas(TipoDeCobranca tipo) {
        List<Baixa> bx = new BaixaDAO().buscarBaixasW().ePorTipoDeCobranca(tipo).listaDeResultados();
        bx.forEach((b) -> {
            b.atualizaValor(tipo.getValor());
        });
    }

    public void remove(FormaDeCobranca forma) {
        formasDeCobranca.remove(forma);
    }

    public void geraBaixas() {
        if (tipoDeCobranca != null) {
            tipoDeCobranca.forEach(t -> t.geraBaixas());
        }
        if (formasDeCobranca != null) {
            formasDeCobranca.forEach(f -> f.geraBaixas());
        }
    }

    public void efetiva() {
        estado = EstadoDeLancamento.EFETIVADO;
    }

    public void efetivaBaixas() throws DadoInvalidoException {
        for (TipoDeCobranca t : tipoDeCobranca) {
            t.descancelar();
        }
        for (FormaDeCobranca f : formasDeCobranca) {
            f.descancelar();
        }
    }

    public void cancela() throws DadoInvalidoException {
        estado = EstadoDeLancamento.CANCELADO;
        for (TipoDeCobranca t : tipoDeCobranca) {
            t.cancela();
        }
        for (FormaDeCobranca f : formasDeCobranca) {
            f.cancela();
        }
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

    public Caixa getCaixa() {
        return caixa;
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

    public EstadoDeLancamento getEstado() {
        return estado;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Pagamento)) {
            return false;
        }
        Pagamento outro = (Pagamento) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
