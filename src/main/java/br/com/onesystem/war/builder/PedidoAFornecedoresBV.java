package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.ItemDePedido;
import br.com.onesystem.domain.ParcelaDePedido;
import br.com.onesystem.domain.PedidoAFornecedores;
import br.com.onesystem.domain.builder.PedidoAFornecedoresBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.valueobjects.EstadoDePedido;
import br.com.onesystem.war.service.ConfiguracaoService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PedidoAFornecedoresBV implements Serializable, BuilderView<PedidoAFornecedores> {

    private Long id;
    private Pessoa pessoa;
    private Operacao operacao;
    private FormaDeRecebimento formaDeRecebimento;
    private Date previsaoDeEntrega = new Date();
    private Date emissao = new Date();
    private String contato;
    private String observacao;
    private EstadoDePedido estado;
    private Moeda moeda;
    private List<ItemDePedido> itens = new ArrayList<>();
    private List<ParcelaDePedido> parcelaDePedido = new ArrayList<>();
    private BigDecimal desconto = BigDecimal.ZERO;
    private BigDecimal acrescimo = BigDecimal.ZERO;
    private BigDecimal despesaCobranca = BigDecimal.ZERO;
    private BigDecimal frete = BigDecimal.ZERO;
    private BigDecimal totalEmDinheiro = BigDecimal.ZERO;

    public PedidoAFornecedoresBV(PedidoAFornecedores p) {
        this.id = p.getId();
        this.pessoa = p.getPessoa();
        this.operacao = p.getOperacao();
        this.formaDeRecebimento = p.getFormaDeRecebimento();
        this.previsaoDeEntrega = p.getPrevisaoDeEntrega();
        this.emissao = p.getEmissao();
        this.estado = p.getEstado();
        this.moeda = p.getMoeda();
        this.itens = p.getItens();
        this.contato = p .getContato();
        this.parcelaDePedido = p.getParcelaDePedido();
        this.desconto = p.getDesconto();
        this.acrescimo = p.getAcrescimo();
        this.despesaCobranca = p.getDespesaCobranca();
        this.frete = p.getFrete();
        this.totalEmDinheiro = p.getTotalEmDinheiro();
        this.observacao = p.getObservacao();
    }

    public PedidoAFornecedoresBV() {
    }

    public BigDecimal getTotalItens() {
        if (itens.size() > 0 && itens != null) {
            return itens.stream().map(ItemDePedido::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal getTotalPedido() {
        BigDecimal a = acrescimo == null ? BigDecimal.ZERO : acrescimo;
        BigDecimal f = frete == null ? BigDecimal.ZERO : frete;
        BigDecimal c = despesaCobranca == null ? BigDecimal.ZERO : despesaCobranca;
        BigDecimal d = desconto == null ? BigDecimal.ZERO : desconto;
        return getTotalItens().add(a.add(f.add(c))).subtract(d);
    }

    public Moeda getMoedaPadrao() throws EDadoInvalidoException {
        Configuracao cfg = new ConfiguracaoService().buscar();
        return cfg.getMoedaPadrao();
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

    public Operacao getOperacao() {
        return operacao;
    }

    public void setOperacao(Operacao operacao) {
        this.operacao = operacao;
    }

    public FormaDeRecebimento getFormaDeRecebimento() {
        return formaDeRecebimento;
    }

    public void setFormaDeRecebimento(FormaDeRecebimento formaDeRecebimento) {
        this.formaDeRecebimento = formaDeRecebimento;
    }

    public Date getPrevisaoDeEntrega() {
        return previsaoDeEntrega;
    }

    public void setPrevisaoDeEntrega(Date previsaoDeEntrega) {
        this.previsaoDeEntrega = previsaoDeEntrega;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public EstadoDePedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoDePedido estado) {
        this.estado = estado;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    public List<ItemDePedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemDePedido> itens) {
        this.itens = itens;
    }

    public List<ParcelaDePedido> getParcelaDePedido() {
        return parcelaDePedido;
    }

    public void setParcelaDePedido(List<ParcelaDePedido> parcelaDePedido) {
        this.parcelaDePedido = parcelaDePedido;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getAcrescimo() {
        return acrescimo;
    }

    public void setAcrescimo(BigDecimal acrescimo) {
        this.acrescimo = acrescimo;
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

    public BigDecimal getTotalEmDinheiro() {
        return totalEmDinheiro;
    }

    public void setTotalEmDinheiro(BigDecimal totalEmDinheiro) {
        this.totalEmDinheiro = totalEmDinheiro;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public PedidoAFornecedores construir() throws DadoInvalidoException {
        return new PedidoAFornecedoresBuilder().comPessoa(pessoa).comOperacao(operacao)
                .comFormaDeRecebimento(formaDeRecebimento).comPrevisaoDeEntrega(previsaoDeEntrega)
                .comEmissao(emissao).comContato(contato).comEstado(estado).comMoedaPadrao(moeda)
                .comItens(itens).comParcelasDePedidos(parcelaDePedido).comDesconto(desconto)
                .comAcrescimo(acrescimo).comDespesaCobranca(despesaCobranca).comFrete(frete)
                .comTotalEmDinheiro(totalEmDinheiro).comObservacao(observacao).construir();
    }

    public PedidoAFornecedores construirComID() throws DadoInvalidoException {
        return new PedidoAFornecedoresBuilder().comId(id).comPessoa(pessoa).comOperacao(operacao)
                .comFormaDeRecebimento(formaDeRecebimento).comPrevisaoDeEntrega(previsaoDeEntrega)
                .comEmissao(emissao).comContato(contato).comEstado(estado).comMoedaPadrao(moeda)
                .comItens(itens).comParcelasDePedidos(parcelaDePedido).comDesconto(desconto)
                .comAcrescimo(acrescimo).comDespesaCobranca(despesaCobranca).comFrete(frete)
                .comTotalEmDinheiro(totalEmDinheiro).comObservacao(observacao).construir();
    }

}
