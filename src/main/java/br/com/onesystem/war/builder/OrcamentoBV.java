/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.ItemOrcado;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.builder.OrcamentoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class OrcamentoBV implements Serializable, BuilderView<Orcamento> {

    private Long id;
    private Pessoa pessoa;
    private List<ItemOrcado> itensOrcados;
    private ListaDePreco listaDePreco;
    private FormaDeRecebimento formaDeRecebimento;
    private Cotacao cotacao;
    private Date vencimento;
    private String historico;
    private BigDecimal acrescimo;
    private BigDecimal desconto;
    private BigDecimal despesaCobranca;
    private BigDecimal frete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<ItemOrcado> getItensOrcados() {
        return itensOrcados;
    }

    public void setItensOrcados(List<ItemOrcado> itensOrcados) {
        this.itensOrcados = itensOrcados;
    }

    public ListaDePreco getListaDePreco() {
        return listaDePreco;
    }

    public void setListaDePreco(ListaDePreco listaDePreco) {
        this.listaDePreco = listaDePreco;
    }

    public FormaDeRecebimento getFormaDeRecebimento() {
        return formaDeRecebimento;
    }

    public void setFormaDeRecebimento(FormaDeRecebimento formaDeRecebimento) {
        this.formaDeRecebimento = formaDeRecebimento;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public void setCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public Orcamento construir() throws DadoInvalidoException {
        return new OrcamentoBuilder().comCotacao(cotacao).comFormaDeRecebimento(formaDeRecebimento).
                comHistorico(historico).comItensOrcados(itensOrcados).comListaDePreco(listaDePreco).comPessoa(pessoa).
                comVencimento(vencimento).comFrete(frete).comDespesaCobranca(despesaCobranca).comAcrescimo(acrescimo)
                .comDesconto(desconto).construir();
    }

    public Orcamento construirComID() throws DadoInvalidoException {
        return new OrcamentoBuilder().comId(id).comCotacao(cotacao).comFormaDeRecebimento(formaDeRecebimento).
                comHistorico(historico).comItensOrcados(itensOrcados).comListaDePreco(listaDePreco).comPessoa(pessoa).
                comVencimento(vencimento).comFrete(frete).comDespesaCobranca(despesaCobranca).comAcrescimo(acrescimo)
                .comDesconto(desconto).construir();
    }

}
