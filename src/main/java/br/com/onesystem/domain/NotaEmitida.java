/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFisica;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Rafael
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_NOTAEMITIDA",
        sequenceName = "SEQ_NOTAEMITIDA")
public class NotaEmitida extends Nota implements Serializable {

    @OneToOne(cascade = {CascadeType.ALL})
    private Orcamento orcamento;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Comanda comanda;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Condicional condicional;

    public NotaEmitida() {
        emissao = new Date();
    }

    public NotaEmitida(Long id, Pessoa pessoa, Operacao operacao, List<ItemDeNota> itens,
            FormaDeRecebimento formaDeRecebimento, ListaDePreco listaDePreco,
            List<Cobranca> cobrancas,
            Moeda moedaPadrao, Orcamento orcamento, List<ValorPorCotacao> valorPorCotacao,
            BigDecimal desconto, BigDecimal acrescimo, BigDecimal despesaCobranca,
            BigDecimal frete, BigDecimal aFaturar, BigDecimal totalEmDinheiro, Nota notaDeOrigem,
            Comanda comanda, Condicional condicional) throws DadoInvalidoException {
        super(id, pessoa, operacao, itens, formaDeRecebimento, listaDePreco, cobrancas, moedaPadrao, valorPorCotacao, desconto, acrescimo, despesaCobranca, frete, aFaturar, totalEmDinheiro, notaDeOrigem);
        this.orcamento = orcamento;
        this.comanda = adicionaNotaNa(comanda);
        this.condicional = adicionaNotaNa(condicional);
        adicionaNoEstoque();
    }

    public Comanda adicionaNotaNa(Comanda comanda) {
        if (comanda != null) {
            comanda.adiciona(this);
        }
        return comanda;
    }

    public Condicional adicionaNotaNa(Condicional condicional) {
        if (condicional != null) {
            condicional.adiciona(this);
        }
        return condicional;
    }

    protected void adicionaNoEstoque() throws DadoInvalidoException {
        for (ItemDeNota i : getItens()) {
            i.setNota(this);
            if (condicional == null || (condicional != null && !(condicional.getOperacao().getOperacaoDeEstoque().equals(getOperacao().getOperacaoDeEstoque())))) {
                i.geraEstoque();
            }
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

    @Override
    public String toString() {
        return "NotaEmitida{" + "id=" + getId() + ", pessoa=" + (getPessoa() != null ? getPessoa().getId() : null)
                + ", operacao=" + (getOperacao() != null ? getOperacao().getId() : null)
                + ", emissao=" + getEmissao()
                + ", itensEmitidos=" + getItens()
                + ", formaDeRecebimento=" + (getFormaDeRecebimento() != null ? getFormaDeRecebimento().getId() : null)
                + ", listaDePreco=" + getListaDePreco()
                + ", parcelas=" + getParcelas()
                + ",orcamento=" + getOrcamento() + '}';
    }

}
