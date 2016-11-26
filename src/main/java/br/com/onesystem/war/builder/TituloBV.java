package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoFormaPagRec;
import br.com.onesystem.valueobjects.UnidadeFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TituloBV implements Serializable {

    private Long id;
    private Pessoa pessoa;
    private String historico;
    private BigDecimal valor;
    private BigDecimal saldo;
    private Date vencimento;
    private Date emissao;
    private Date ultimoPagamento;
    private UnidadeFinanceira unidadeFinanceira;
    private Recepcao recepcao;
    private Cambio cambio;
    private TipoFormaPagRec tipoFormaPagRec;
    private Moeda moeda;

    public TituloBV(Titulo tituloSelecionado) {
        this.id = tituloSelecionado.getId();
        this.pessoa = tituloSelecionado.getPessoa();
        this.historico = tituloSelecionado.getHistorico();
        this.valor = tituloSelecionado.getValor();
        this.saldo = tituloSelecionado.getSaldo();
        this.vencimento = tituloSelecionado.getVencimento();
        this.emissao = tituloSelecionado.getEmissao();
        this.ultimoPagamento = tituloSelecionado.getUltimoPagamento();
        this.unidadeFinanceira = tituloSelecionado.getUnidadeFinanceira();
        this.recepcao = tituloSelecionado.getRecepcao();
        this.cambio = tituloSelecionado.getCambio();
        this.tipoFormaPagRec = tituloSelecionado.getTipoFormaPagRec();
        this.moeda = tituloSelecionado.getMoeda();
    }

    public TituloBV(Long id, Pessoa pessoa, String historico, BigDecimal valor, BigDecimal saldo, 
            Date vencimento, Date emissao, Date ultimoPagamento, UnidadeFinanceira unidadeFinanceira, 
            Recepcao recepcao, Cambio cambio, TipoFormaPagRec tipoFormaPagRec, Moeda moeda) {
        this.id = id;
        this.pessoa = pessoa;
        this.historico = historico;
        this.valor = valor;
        this.saldo = saldo;
        this.vencimento = vencimento;
        this.emissao = emissao;
        this.ultimoPagamento = ultimoPagamento;
        this.unidadeFinanceira = unidadeFinanceira;
        this.recepcao = recepcao;
        this.cambio = cambio;
        this.tipoFormaPagRec = tipoFormaPagRec;
        this.moeda = moeda;
    }

    public TituloBV() {
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

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public Date getUltimoPagamento() {
        return ultimoPagamento;
    }

    public void setUltimoPagamento(Date ultimoPagamento) {
        this.ultimoPagamento = ultimoPagamento;
    }

    public UnidadeFinanceira getUnidadeFinanceira() {
        return unidadeFinanceira;
    }

    public void setUnidadeFinanceira(UnidadeFinanceira unidadeFinanceira) {
        this.unidadeFinanceira = unidadeFinanceira;
    }

    public Recepcao getRecepcao() {
        return recepcao;
    }

    public void setRecepcao(Recepcao recepcao) {
        this.recepcao = recepcao;
    }

    public Cambio getCambio() {
        return cambio;
    }

    public void setCambio(Cambio cambio) {
        this.cambio = cambio;
    }

    public TipoFormaPagRec getTipoFormaPagRec() {
        return tipoFormaPagRec;
    }

    public void setTipoFormaPagRec(TipoFormaPagRec tipoFormaPagRec) {
        this.tipoFormaPagRec = tipoFormaPagRec;
    }

    public Moeda getMoeda() {
        return moeda;
    }
    
    public Titulo construir() throws DadoInvalidoException {
        return new Titulo(null, pessoa, historico, valor, saldo, emissao, unidadeFinanceira,
                tipoFormaPagRec, vencimento, recepcao, cambio, ultimoPagamento, moeda);
    }

    public Titulo construirComID() throws DadoInvalidoException {
        return new Titulo(id, pessoa, historico, valor, saldo, emissao, unidadeFinanceira,
                tipoFormaPagRec, vencimento, recepcao, cambio, ultimoPagamento, moeda);
    }
}
