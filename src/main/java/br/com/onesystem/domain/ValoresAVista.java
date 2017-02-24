/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(sequenceName = "SEQ_VALORESAVISTA", name = "SEQ_VALORESAVISTA",
        initialValue = 1, allocationSize = 1)
public class ValoresAVista implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VALORESAVISTA")
    private Long id;
    @Min(value = 0, message = "{min_dinheiro}")
    private BigDecimal dinheiro;
    @Min(value = 0, message = "{min_credito}")
    private BigDecimal credito;
    @OneToMany(mappedBy = "valoresAVista", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Cheque> cheques;
    @Min(value = 0, message = "{min_cartao}")
    @OneToOne(cascade = CascadeType.ALL)
    private BoletoDeCartao cartao;
    @Min(value = 0, message = "{min_aFaturar}")
    private BigDecimal aFaturar;
    @OneToOne
    private NotaEmitida notaEmitida;
    @ManyToOne
    private Moeda moeda;
    @Min(value = 0, message = "{valorDesconto_min}")
    @Column(nullable = true)
    private BigDecimal desconto;
    @Min(value = 0, message = "{valor_acrescimo_min}")
    @Column(nullable = true)
    private BigDecimal acrescimo;
    @Min(value = 0, message = "{valor_despesa_cobranca_min}")
    private BigDecimal despesaCobranca;
    @Min(value = 0, message = "{valor_frete_min}")
    private BigDecimal frete;

    public ValoresAVista() {
    }

    public ValoresAVista(Long id,
            BigDecimal dinheiro, BigDecimal credito, BoletoDeCartao cartao,
            BigDecimal aFaturar, NotaEmitida notaEmitida, Moeda moeda, BigDecimal desconto,
            BigDecimal acrescimo, BigDecimal despesaCobranca, BigDecimal frete,
            List<Cheque> cheques) throws DadoInvalidoException {
        this.id = id;
        this.dinheiro = dinheiro;
        this.credito = credito;
        this.aFaturar = aFaturar;
        this.notaEmitida = notaEmitida;
        this.moeda = moeda;
        this.desconto = desconto;
        this.acrescimo = acrescimo;
        this.despesaCobranca = despesaCobranca;
        this.frete = frete;
        this.cartao = cartao;
        this.cheques = cheques;
        ehValido();
    }
    
     public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("dinheiro",
                "credito", "cartao", "aFaturar", "acrescimo", "desconto",
                "despesaCobranca", "frete");
        new ValidadorDeCampos<ValoresAVista>().valida(this, campos);
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getDinheiro() {
        return dinheiro;
    }

    public BigDecimal getCredito() {
        return credito;
    }

    public BoletoDeCartao getCartao() {
        return cartao;
    }

    public BigDecimal getaFaturar() {
        return aFaturar;
    }

    public NotaEmitida getNotaEmitida() {
        return notaEmitida;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public BigDecimal getAcrescimo() {
        return acrescimo;
    }

    public BigDecimal getDespesaCobranca() {
        return despesaCobranca;
    }

    public List<Cheque> getCheques() {
        return cheques;
    }

    public BigDecimal getFrete() {
        return frete;
    }

    public void setCheques(List<Cheque> cheques) {
        this.cheques = cheques;
    }      

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof ValoresAVista)) {
            return false;
        }
        ValoresAVista outro = (ValoresAVista) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
