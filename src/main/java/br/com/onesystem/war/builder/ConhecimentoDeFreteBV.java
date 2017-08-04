package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.NotaRecebida;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.ValorPorCotacao;
import br.com.onesystem.domain.builder.ConhecimentoDeFreteBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.war.service.ConfiguracaoService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ConhecimentoDeFreteBV implements Serializable, BuilderView<ConhecimentoDeFrete> {

    private Long id;
    private Pessoa pessoa;
    private Operacao operacao;
    private BigDecimal valorFrete;
    private BigDecimal outrasdespesas;
    private BigDecimal dinheiro;
    private Date data;
    private Date emissao = new Date();
    private List<Titulo> titulo;
    private List<ValorPorCotacao> valorPorCotacao;
    private List<NotaRecebida> notaRecebida;

    public ConhecimentoDeFreteBV() {
    }

    public ConhecimentoDeFreteBV(ConhecimentoDeFrete c) {
        this.id = c.getId();
        this.pessoa = c.getPessoa();
        this.operacao = c.getOperacao();
        this.valorFrete = c.getValorFrete();
        this.outrasdespesas = c.getOutrasdespesas();
        this.dinheiro = c.getDinheiro();
        this.data = c.getData();
        this.emissao = c.getEmissao();
        this.titulo = c.getTitulo();
        this.notaRecebida = c.getNotaRecebida();
        this.valorPorCotacao = c.getValorPorCotacao();
    }

    public ConhecimentoDeFreteBV(Long id, Pessoa pessoa, Operacao operacao, BigDecimal valorFrete, BigDecimal outrasdespesas, BigDecimal dinheiro,
            Date data, Date emissao, List<Titulo> titulo, List<NotaRecebida> notaRecebida, List<ValorPorCotacao> valorPorCotacao) {
        this.id = id;
        this.pessoa = pessoa;
        this.operacao = operacao;
        this.valorFrete = valorFrete;
        this.outrasdespesas = outrasdespesas;
        this.dinheiro = dinheiro;
        this.data = data;
        this.emissao = emissao;
        this.titulo = titulo;
        this.notaRecebida = notaRecebida;
        this.valorPorCotacao = valorPorCotacao;

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

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public BigDecimal getOutrasdespesas() {
        return outrasdespesas;
    }

    public void setOutrasdespesas(BigDecimal outrasdespesas) {
        this.outrasdespesas = outrasdespesas;
    }

    public BigDecimal getDinheiro() {
        return dinheiro;
    }

    public void setDinheiro(BigDecimal dinheiro) {
        this.dinheiro = dinheiro;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public List<Titulo> getTitulo() {
        return titulo;
    }

    public void setTitulo(List<Titulo> titulo) {
        this.titulo = titulo;
    }

    public List<ValorPorCotacao> getValorPorCotacao() {
        return valorPorCotacao;
    }

    public void setValorPorCotacao(List<ValorPorCotacao> valorPorCotacao) {
        this.valorPorCotacao = valorPorCotacao;
    }

    public List<NotaRecebida> getNotaRecebida() {
        return notaRecebida;
    }

    public void setNotaRecebida(List<NotaRecebida> notaRecebida) {
        this.notaRecebida = notaRecebida;
    }

    public Moeda getMoedaPadrao() throws EDadoInvalidoException {
        Configuracao cfg = new ConfiguracaoService().buscar();
        return cfg.getMoedaPadrao();
    }

    public ConhecimentoDeFrete construir() throws DadoInvalidoException {
        return new ConhecimentoDeFreteBuilder().comPessoa(pessoa).comOperacao(operacao)
                .comValorFrete(valorFrete).comOutrasDespesas(outrasdespesas).comDinheiro(dinheiro)
                .comData(data).comEmissao(emissao).comTitulo(titulo).comNotaRecebida(notaRecebida).comValorPorCotacao(valorPorCotacao).construir();
    }

    public ConhecimentoDeFrete construirComID() throws DadoInvalidoException {
        return new ConhecimentoDeFreteBuilder().comPessoa(pessoa).comOperacao(operacao).comID(id)
                .comValorFrete(valorFrete).comOutrasDespesas(outrasdespesas).comDinheiro(dinheiro)
                .comData(data).comEmissao(emissao).comTitulo(titulo).comNotaRecebida(notaRecebida).comValorPorCotacao(valorPorCotacao).construir();
    }
}
