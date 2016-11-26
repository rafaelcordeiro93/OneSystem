package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.ContratoDeCambio;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class CambioBV implements Serializable {

    private Long id;
    private Conta conta;
    private BigDecimal taxaEfetivada;
    private ContratoDeCambio contrato;
    private BigDecimal valorBruto;
    private BigDecimal valorLiquido = BigDecimal.ZERO;
    private BigDecimal porcentagemDeComissao;
    private String processo;
    private Date emissao = new Date();
    private Pessoa pessoaComissionada;
    private BigDecimal comissaoCalculada;
    private BigDecimal porcentualLucroTaxa;
    private Moeda moeda;

    public CambioBV(Cambio cambioSelecionado) {
        this.id = cambioSelecionado.getId();
        this.contrato = cambioSelecionado.getContrato();
        this.conta = cambioSelecionado.getConta();
        this.taxaEfetivada = cambioSelecionado.getTaxaEfetivada();
        this.valorBruto = cambioSelecionado.getValorBruto();
        this.porcentagemDeComissao = cambioSelecionado.getPorcentagemDeComissao();
        this.valorLiquido = cambioSelecionado.getValorLiquido();
        this.processo = cambioSelecionado.getProcesso();
        this.emissao = cambioSelecionado.getEmissao();
        this.pessoaComissionada = cambioSelecionado.getPessoaComissionada();
        this.comissaoCalculada = cambioSelecionado.getComissaoCalculada();
        this.porcentualLucroTaxa = cambioSelecionado.getPorcentagemDeLucroEmTaxa();
    }

    public CambioBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public BigDecimal getTaxaEfetivada() {
        return taxaEfetivada;
    }

    public void setTaxaEfetivada(BigDecimal taxaEfetivada) {
        this.taxaEfetivada = taxaEfetivada;
    }

    public ContratoDeCambio getContrato() {
        return contrato;
    }

    public void setContrato(ContratoDeCambio contrato) {
        this.contrato = contrato;
    }

    public BigDecimal getValorBruto() {
        return valorBruto;
    }

    public void setValorBruto(BigDecimal valorBruto) {
        this.valorBruto = valorBruto;
    }

    public BigDecimal getValorLiquido() {
        return valorLiquido;
    }

    public BigDecimal getPorcentagemDeComissao() {
        return porcentagemDeComissao;
    }

    public void setPorcentagemDeComissao(BigDecimal porcentagemDeComissao) {
        this.porcentagemDeComissao = porcentagemDeComissao;
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public Pessoa getPessoaComissionada() {
        return pessoaComissionada;
    }

    public void setPessoaComissionada(Pessoa pessoaComissionada) {
        this.pessoaComissionada = pessoaComissionada;
    }

    public BigDecimal getComissaoCalculada() {
        return comissaoCalculada;
    }

    public void setComissaoCalculada(BigDecimal comissaoCalculada) {
        this.comissaoCalculada = comissaoCalculada;
    }

    public String getValorLiquidoFormatado() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(valorLiquido);
    }

    public BigDecimal getPorcentualLucroTaxa() {
        return porcentualLucroTaxa;
    }

    public void setPorcentualLucroTaxa(BigDecimal porcentualLucroTaxa) {
        this.porcentualLucroTaxa = porcentualLucroTaxa;
    }
    
    public void setValorLiquido(BigDecimal valorLiquido) {
        this.valorLiquido = valorLiquido;
    }
    
    public Cambio construir() throws DadoInvalidoException {
        return new Cambio(null, contrato, conta, taxaEfetivada, valorBruto, valorLiquido, porcentagemDeComissao, processo, emissao, pessoaComissionada, comissaoCalculada, porcentualLucroTaxa);
    }

    public Cambio construirComID() throws DadoInvalidoException {
        return new Cambio(id, contrato, conta, taxaEfetivada, valorBruto, valorLiquido, porcentagemDeComissao, processo, emissao, pessoaComissionada, comissaoCalculada, porcentualLucroTaxa);
    }
}
