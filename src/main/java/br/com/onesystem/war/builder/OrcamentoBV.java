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
import br.com.onesystem.util.MoedaFormatter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    private BigDecimal porcentagemAcrescimo;
    private BigDecimal porcentagemDesconto;

    public OrcamentoBV() {
    }

    public OrcamentoBV(Orcamento o) {
        this.id = o.getId();
        this.pessoa = o.getPessoa();
        this.listaDePreco = o.getListaDePreco();
        this.formaDeRecebimento = o.getFormaDeRecebimento();
        this.cotacao = o.getCotacao();
        this.vencimento = o.getValidade();
        this.historico = o.getObservacao();
        this.acrescimo = o.getAcrescimo();
        this.desconto = o.getDesconto();
        this.despesaCobranca = o.getDespesaCobranca();
        this.frete = o.getFrete();
        this.itensOrcados = o.getItensOrcados();
    }

    public void adiciona(ItemOrcadoBV item) throws DadoInvalidoException {
        if (itensOrcados == null) {
            itensOrcados = new ArrayList<>();
        }
        item.setId(getCodigoItem());
        ItemOrcado ie = item.construirComId();
        itensOrcados.add(ie);
    }

    public void atualiza(ItemOrcado itemSelecionado, ItemOrcado itemNovo) throws DadoInvalidoException {
        itensOrcados.set(itensOrcados.indexOf(itemSelecionado), itemNovo);
    }

    public void remove(ItemOrcado item) {
        itensOrcados.remove(item);
    }

    public BigDecimal getTotalItens() {
        if (itensOrcados == null) {
            return BigDecimal.ZERO;
        } else {
            return itensOrcados.stream().map(ItemOrcado::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }

    public String getTotalItensFormatado() {
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), getTotalItens());
    }

    private Long getCodigoItem() {
        Long id = (long) 1;
        if (!itensOrcados.isEmpty()) {
            for (ItemOrcado dp : itensOrcados) {
                if (dp.getId() >= id) {
                    id = dp.getId() + 1;
                }
            }
        }
        return id;
    }

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

    public BigDecimal getAcrescimo() {
        return acrescimo;
    }

    public void setAcrescimo(BigDecimal acrescimo) {
        this.acrescimo = acrescimo;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getDespesaCobranca() {
        return despesaCobranca;
    }

    public void setDespesaCobranca(BigDecimal despesaCobranca) {
        this.despesaCobranca = despesaCobranca;
    }

    public BigDecimal getFrete() {
        return frete;
    }

    public void setFrete(BigDecimal frete) {
        this.frete = frete;
    }

    public BigDecimal getPorcentagemAcrescimo() {
        return porcentagemAcrescimo;
    }

    public void setPorcentagemAcrescimo(BigDecimal porcentagemAcrescimo) {
        this.porcentagemAcrescimo = porcentagemAcrescimo;
    }

    public BigDecimal getPorcentagemDesconto() {
        return porcentagemDesconto;
    }

    public void setPorcentagemDesconto(BigDecimal porcentagemDesconto) {
        this.porcentagemDesconto = porcentagemDesconto;
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
