/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author Rafael
 */
@Entity
@DiscriminatorValue("NOTA_RECEBIDA")
public class NotaRecebida extends Nota implements Serializable {

    @ManyToOne
    private FaturaRecebida faturaRecebida;

    @ManyToOne
    private ConhecimentoDeFrete conhecimentoDeFrete;

    @ManyToOne(cascade = CascadeType.MERGE)
    private PedidoAFornecedores pedidoAFornecedores;

    public NotaRecebida() {
    }

    public NotaRecebida(Long id, Pessoa pessoa, Operacao operacao, List<ItemDeNota> itens,
            FormaDeRecebimento formaDeRecebimento, ListaDePreco listaDePreco,
            List<CobrancaVariavel> cobrancas,
            Moeda moedaPadrao, List<ValorPorCotacao> valorPorCotacao,
            BigDecimal desconto, BigDecimal acrescimo, BigDecimal despesaCobranca,
            BigDecimal frete, BigDecimal aFaturar, BigDecimal totalEmDinheiro, Nota notaDeOrigem, Date emissao,
            Caixa caixa, Usuario usuario, FaturaRecebida faturaRecebida, ConhecimentoDeFrete conhecimentoDeFrete, Filial filial, PedidoAFornecedores pedidoAFornecedores, Integer numeroNF) throws DadoInvalidoException {
        super(id, pessoa, operacao, itens, formaDeRecebimento, listaDePreco, cobrancas, moedaPadrao, valorPorCotacao, desconto, acrescimo, despesaCobranca, frete, aFaturar, totalEmDinheiro, notaDeOrigem, emissao, caixa, usuario, filial, numeroNF);
        this.faturaRecebida = faturaRecebida;
        this.conhecimentoDeFrete = conhecimentoDeFrete;
        this.pedidoAFornecedores = pedidoAFornecedores;
        if (id == null) {
            adicionaNotaNoItem();
        }
    }

    protected void adicionaNotaNoItem() throws DadoInvalidoException {
        for (ItemDeNota i : getItens()) {
            i.setNota(this);
        }
    }

    public FaturaRecebida getFaturaRecebida() {
        return faturaRecebida;
    }

    public void setFaturaRecebida(FaturaRecebida faturaRecebida) {
        this.faturaRecebida = faturaRecebida;
    }

    public ConhecimentoDeFrete getConhecimentoDeFrete() {
        return conhecimentoDeFrete;
    }

    public void setConhecimentoDeFrete(ConhecimentoDeFrete conhecimentoDeFrete) {
        this.conhecimentoDeFrete = conhecimentoDeFrete;
    }

    public PedidoAFornecedores getPedidoAFornecedores() {
        return pedidoAFornecedores;
    }

    @Override
    public String toString() {
        return "NotaEmitida{" + "id=" + getId() + ", pessoa=" + (getPessoa() != null ? getPessoa().getId() : null)
                + ", operacao=" + (getOperacao() != null ? getOperacao().getId() : null)
                + ", emissao=" + getEmissao()
                + ", itensRecebidos=" + getItens()
                + ", formaDeRecebimento=" + (getFormaDeRecebimento() != null ? getFormaDeRecebimento().getId() : null)
                + ", listaDePreco=" + getListaDePreco()
                + ", parcelas=" + getParcelas() + '}';
    }

}
