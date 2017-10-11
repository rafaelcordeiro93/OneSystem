/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.impl.MetodoInacessivelRelatorio;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Rafael
 */
@Entity
@DiscriminatorValue("NOTA_EMITIDA")
public class NotaEmitida extends Nota implements Serializable {

    @OneToOne(cascade = {CascadeType.ALL})
    private Orcamento orcamento;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Comanda comanda;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Condicional condicional;

    @ManyToOne
    private FaturaEmitida faturaEmitida;

    @OneToOne(cascade = {CascadeType.ALL})
    private LoteNotaFiscal loteNotaFiscal;

    public NotaEmitida() {
        emissao = new Date();
    }

    public NotaEmitida(Long id, Pessoa pessoa, Operacao operacao, List<ItemDeNota> itens,
            FormaDeRecebimento formaDeRecebimento, ListaDePreco listaDePreco,
            List<CobrancaVariavel> cobrancas,
            Cotacao cotacao, Orcamento orcamento, List<ValorPorCotacao> valorPorCotacao,
            BigDecimal desconto, BigDecimal acrescimo, BigDecimal despesaCobranca,
            BigDecimal frete, BigDecimal aFaturar, BigDecimal totalEmDinheiro, Nota notaDeOrigem,
            Comanda comanda, Condicional condicional, Date emissao, Caixa caixa,
            Usuario usuario, FaturaEmitida faturaEmitida, Filial filial, Integer numeroNF, LoteNotaFiscal loteNotaFiscal) throws DadoInvalidoException {
        super(id, pessoa, operacao, itens, formaDeRecebimento, listaDePreco, cobrancas, cotacao, valorPorCotacao, desconto, acrescimo, despesaCobranca,
                frete, aFaturar, totalEmDinheiro, notaDeOrigem, emissao, caixa, usuario, filial, numeroNF);
        this.orcamento = orcamento;
        this.comanda = comanda;
        this.condicional = condicional;
        this.faturaEmitida = faturaEmitida;
        this.loteNotaFiscal = loteNotaFiscal;
        if (id == null) {
            adicionaNotaNaComanda();
            adicionaNotaNaCondicional();
            adicionaNotaNoItem();
        }
    }

    public void setFaturaEmitida(FaturaEmitida faturaEmitida) {
        this.faturaEmitida = faturaEmitida;
    }

    public void adicionaNotaNaComanda() {
        if (comanda != null) {
            comanda.adiciona(this);
        }
    }

    public void adicionaNotaNaCondicional() {
        if (condicional != null) {
            condicional.adiciona(this);
        }
    }

    protected void adicionaNotaNoItem() throws DadoInvalidoException {
        for (ItemDeNota i : getItens()) {
            i.setNota(this);
        }
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public Condicional getCondicional() {
        return condicional;
    }

    public FaturaEmitida getFaturaEmitida() {
        return faturaEmitida;
    }

    public LoteNotaFiscal getLoteNotaFiscal() {
        return loteNotaFiscal;
    }

    @Override
    public String toString() {
        return "NotaEmitida{" + "id=" + getId() + ", pessoa=" + (getPessoa() != null ? getPessoa().getId() : null)
                + ", operacao=" + (getOperacao() != null ? getOperacao().getId() : null)
                + ", emissao=" + getEmissao()
                + ", itensEmitidos=" + getItens()
                + ", formaDeRecebimento=" + (getFormaDeRecebimento() != null ? getFormaDeRecebimento().getId() : null)
                + ", listaDePreco=" + getListaDePreco()
                + ", parcelas=" + getCobrancas()
                + ", valorPorCotacao = " + getValorPorCotacao()
                + ",orcamento=" + getOrcamento() + '}';
    }

}
