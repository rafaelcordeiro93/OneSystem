/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.MoedaFomatter;
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
    private BigDecimal dinheiro = BigDecimal.ZERO;
    @OneToMany(mappedBy = "valoresAVista", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Cheque> cheques;
    @OneToOne(cascade = CascadeType.ALL)
    private BoletoDeCartao boletoDeCartao;
    @Min(value = 0, message = "{min_aFaturar}")
    private BigDecimal aFaturar = BigDecimal.ZERO;
    @OneToOne
    private Nota nota;
    @ManyToOne
    private Cotacao cotacao;
    @Min(value = 0, message = "{valorDesconto_min}")
    @Column(nullable = true)
    private BigDecimal desconto = BigDecimal.ZERO;
    @Min(value = 0, message = "{valor_acrescimo_min}")
    @Column(nullable = true)
    private BigDecimal acrescimo = BigDecimal.ZERO;
    @Min(value = 0, message = "{valor_despesa_cobranca_min}")
    private BigDecimal despesaCobranca = BigDecimal.ZERO;
    @Min(value = 0, message = "{valor_frete_min}")
    private BigDecimal frete = BigDecimal.ZERO;

    public ValoresAVista() {
    }

    public ValoresAVista(Long id,
            BigDecimal dinheiro, BoletoDeCartao boletoDeCartao,
            BigDecimal aFaturar, Nota nota, Cotacao cotacao, BigDecimal desconto,
            BigDecimal acrescimo, BigDecimal despesaCobranca, BigDecimal frete,
            List<Cheque> cheques) throws DadoInvalidoException {
        this.id = id;
        this.dinheiro = dinheiro;
        this.aFaturar = aFaturar;
        this.nota = nota;
        this.cotacao = cotacao;
        this.desconto = desconto;
        this.acrescimo = acrescimo;
        this.despesaCobranca = despesaCobranca;
        this.frete = frete;
        this.boletoDeCartao = boletoDeCartao;
        this.cheques = cheques;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("dinheiro",
                "aFaturar", "acrescimo", "desconto",
                "despesaCobranca", "frete");
        new ValidadorDeCampos<ValoresAVista>().valida(this, campos);
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getDinheiro() {
        return dinheiro;
    }

    public BoletoDeCartao getBoletoCartao() {
        return boletoDeCartao;
    }

    public BigDecimal getaFaturar() {
        return aFaturar;
    }

    public Nota getNota() {
        return nota;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public BigDecimal getAcrescimo() {
        return acrescimo;
    }

    public BigDecimal getTotalCheque() {
        BigDecimal total = BigDecimal.ZERO;
        for (Cheque i : cheques) {
            total = total.add(i.getValor());
        }
        return total;
    }

    public String getTotalChequeFormatado() {
        if (nota != null) {
            if (cheques != null && cheques.isEmpty()) {
                return MoedaFomatter.format(nota.getMoedaPadrao(), getTotalCheque());
            } else {
                return MoedaFomatter.format(nota.getMoedaPadrao(), BigDecimal.ZERO);
            }
        } else if (cheques != null && cheques.isEmpty()) {
            return MoedaFomatter.format(getTotalCheque());
        } else {
            return MoedaFomatter.format(BigDecimal.ZERO);
        }
    }

    public String getAcrescimoFormatado() {
        if (nota != null) {
            return MoedaFomatter.format(nota.getMoedaPadrao(), getAcrescimo());
        } else {
            return MoedaFomatter.format(getAcrescimo());
        }
    }

    public String getDinheiroFormatado() {
        if (nota != null) {
            return MoedaFomatter.format(nota.getMoedaPadrao(), getDinheiro());
        } else {
            return MoedaFomatter.format(getDinheiro());
        }
    }

    public String getDescontoFormatado() {
        if (nota != null) {
            return MoedaFomatter.format(nota.getMoedaPadrao(), getDesconto());
        } else {
            return MoedaFomatter.format(getDesconto());
        }
    }

    public String getDespesaCobrancaFormatado() {
        if (nota != null) {
            return MoedaFomatter.format(nota.getMoedaPadrao(), getDespesaCobranca());
        } else {
            return MoedaFomatter.format(getDespesaCobranca());
        }
    }

    public String getFreteFormatado() {
        if (nota != null) {
            return MoedaFomatter.format(nota.getMoedaPadrao(), getFrete());
        } else {
            return MoedaFomatter.format(getFrete());
        }
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

    public void setNota(Nota nota) {
        this.nota = nota;
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

    @Override
    public String toString() {
        return "ValoresAVista{" + "id=" + id + ", dinheiro=" + dinheiro + ", cheques=" + cheques + ", boletoDeCartao=" + boletoDeCartao + ", aFaturar=" + aFaturar + ", nota=" + (nota != null ? nota.getId() : null) + ", cotacao=" + (cotacao != null ? cotacao.getId() : null) + ", desconto=" + desconto + ", acrescimo=" + acrescimo + ", despesaCobranca=" + despesaCobranca + ", frete=" + frete + '}';
    }

}
