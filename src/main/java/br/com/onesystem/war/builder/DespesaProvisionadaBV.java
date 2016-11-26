package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Despesa;
import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.ClassificacaoFinanceira;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DespesaProvisionadaBV implements Serializable {

    private Long id;
    private Pessoa pessoa;
    private Despesa despesa;
    private BigDecimal valor;
    private Date vencimento;
    private Date emissao;
    private String historico;
    private boolean divisaoLucroCambioCaixa;
    private Moeda moeda;

    public DespesaProvisionadaBV(DespesaProvisionada despesaProvisionadaSelecionada) {
        this.id = despesaProvisionadaSelecionada.getId();
        this.pessoa = despesaProvisionadaSelecionada.getPessoa();
        this.despesa = despesaProvisionadaSelecionada.getDespesa();
        this.valor = despesaProvisionadaSelecionada.getValor();
        this.vencimento = despesaProvisionadaSelecionada.getVencimento();
        this.emissao = despesaProvisionadaSelecionada.getEmissao();
        this.historico = despesaProvisionadaSelecionada.getHistorico();
        this.divisaoLucroCambioCaixa = despesaProvisionadaSelecionada.isDivisaoLucroCambioCaixa();
        this.moeda = despesaProvisionadaSelecionada.getMoeda();
    }

    public DespesaProvisionadaBV(Long id, Pessoa pessoa, Despesa despesa, BigDecimal valor, Date vencimento,
            Date emissao, String historico, boolean divisaoLucroCambioCaixa, Moeda moeda) {
        this.id = id;
        this.pessoa = pessoa;
        this.despesa = despesa;
        this.valor = valor;
        this.vencimento = vencimento;
        this.emissao = emissao;
        this.historico = historico;
        this.divisaoLucroCambioCaixa = divisaoLucroCambioCaixa;
        this.moeda = moeda;
    }

    public DespesaProvisionadaBV() {
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

    public Despesa getDespesa() {
        return despesa;
    }

    public void setDespesa(Despesa despesa) {
        this.despesa = despesa;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public String getVencimentoFormatado() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(vencimento);
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

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public boolean isDivisaoLucroCambioCaixa() {
        return divisaoLucroCambioCaixa;
    }

    public void setDivisaoLucroCambioCaixa(boolean divisaoLucroCambioCaixa) {
        this.divisaoLucroCambioCaixa = divisaoLucroCambioCaixa;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }
    
    public DespesaProvisionada construir() throws DadoInvalidoException {
        return new DespesaProvisionada(null, pessoa, despesa, valor, vencimento, historico, null, false, moeda);
    }

    public DespesaProvisionada construirComID() throws DadoInvalidoException {
        return new DespesaProvisionada(id, pessoa, despesa, valor, vencimento, historico, null, divisaoLucroCambioCaixa, moeda);
    }
}
