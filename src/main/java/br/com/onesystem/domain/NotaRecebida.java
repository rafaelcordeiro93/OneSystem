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
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Rafael
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_NOTARECEBIDA",
        sequenceName = "SEQ_NOTARECEBIDA")
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
            List<Cobranca> cobrancas,
            Moeda moedaPadrao, List<ValorPorCotacao> valorPorCotacao,
            BigDecimal desconto, BigDecimal acrescimo, BigDecimal despesaCobranca,
            BigDecimal frete, BigDecimal aFaturar, BigDecimal totalEmDinheiro, Nota notaDeOrigem, Date emissao,
            Caixa caixa, Usuario usuario, FaturaRecebida faturaRecebida, ConhecimentoDeFrete conhecimentoDeFrete, PedidoAFornecedores pedidoAFornecedores) throws DadoInvalidoException {
        super(id, pessoa, operacao, itens, formaDeRecebimento, listaDePreco, cobrancas, moedaPadrao, valorPorCotacao, desconto, acrescimo, despesaCobranca, frete, aFaturar, totalEmDinheiro, notaDeOrigem, emissao, caixa, usuario);
        this.faturaRecebida = faturaRecebida;
        this.conhecimentoDeFrete = conhecimentoDeFrete;
        this.pedidoAFornecedores = pedidoAFornecedores;
        if (id == null) {
            adicionaNoEstoque();
        }
    }

    protected void adicionaNoEstoque() throws DadoInvalidoException {
        for (ItemDeNota i : getItens()) {
            i.setNota(this);
            i.geraEstoque();
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
