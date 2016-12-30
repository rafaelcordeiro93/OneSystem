package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Despesa;
import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Receita;
import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.domain.builder.BaixaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BaixaBV implements Serializable {

    private Long id;
    private Conta conta;
    private Pessoa pessoa;
    private BigDecimal valor = BigDecimal.ZERO;
    private int numeroParcela;
    private Despesa despesa;
    private OperacaoFinanceira unidadeFinanciera;
    private String historico;
    private Date emissao = new Date();
    private BigDecimal desconto = BigDecimal.ZERO;
    private BigDecimal multas = BigDecimal.ZERO;
    private BigDecimal total;
    private BigDecimal juros = BigDecimal.ZERO;
    private boolean cancelada;
    private Cambio cambio;
    private Titulo titulo;
    private Recepcao recepcao;
    private Receita receita;
    private Transferencia transferencia;
    private DespesaProvisionada despesaProvisionada;
    private ReceitaProvisionada receitaProvisionada;
    private ConhecimentoDeFrete conhecimentoDeFrete;
    private NotaEmitida notaEmitida;

    public BaixaBV() {
    }

    public BaixaBV(Baixa baixa) {
        this.id = baixa.getId();
        this.pessoa = baixa.getPessoa();
        this.conta = baixa.getConta();
        this.numeroParcela = baixa.getNumeroParcela();
        this.cancelada = baixa.isCancelada();
        this.valor = baixa.getValor();
        this.total = baixa.getValor();
        this.desconto = baixa.getDesconto();
        this.emissao = baixa.getEmissao();
        this.historico = baixa.getHistorico();
        this.unidadeFinanciera = baixa.getNaturezaFinanceira();
        this.despesa = baixa.getDespesa();
        this.cambio = baixa.getCambio();
        this.recepcao = baixa.getRecepcao();
        this.titulo = baixa.getTitulo();
        this.despesaProvisionada = baixa.getDespesaProvisionada();
        this.receitaProvisionada = baixa.getReceitaProvisionada();
        this.receita = baixa.getReceita();
        this.transferencia = baixa.getTransferencia();
        this.conhecimentoDeFrete = baixa.getConhecimentoDeFrete();
        this.notaEmitida = baixa.getNotaEmitida();
    }

    public void selecionaTitulo(Titulo titulo) {
        this.pessoa = titulo.getPessoa();
        this.total = titulo.getSaldo();
        this.emissao = titulo.getEmissao();
        this.historico = titulo.getHistorico();
        this.unidadeFinanciera = titulo.getUnidadeFinanceira();
        this.recepcao = titulo.getRecepcao();
        this.titulo = titulo;
        this.valor = titulo.getSaldo();
        this.cambio = titulo.getCambio();
    }

    public void selecionaDespesaProvisionada(DespesaProvisionada despesaProvisionada) {
        this.pessoa = despesaProvisionada.getPessoa();
        this.valor = despesaProvisionada.getValor();
        this.emissao = despesaProvisionada.getEmissao();
        this.historico = despesaProvisionada.getHistorico();
        this.despesaProvisionada = despesaProvisionada;
        this.despesa = despesaProvisionada.getDespesa();
        this.cambio = despesaProvisionada.getCambio();
    }

    public void selecionaReceitaProvisionada(ReceitaProvisionada receitaProvisionada) {
        this.pessoa = receitaProvisionada.getPessoa();
        this.valor = receitaProvisionada.getValor();
        this.emissao = receitaProvisionada.getEmissao();
        this.historico = receitaProvisionada.getHistorico();
        this.receitaProvisionada = receitaProvisionada;
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

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public int getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(int numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public void setDespesa(Despesa despesa) {
        this.despesa = despesa;
    }

    public OperacaoFinanceira getUnidadeFinanciera() {
        return unidadeFinanciera;
    }

    public void setUnidadeFinanceira(OperacaoFinanceira unidadeFinanciera) {
        this.unidadeFinanciera = unidadeFinanciera;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getMultas() {
        return multas;
    }

    public void setMultas(BigDecimal multas) {
        this.multas = multas;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public void setJuros(BigDecimal juros) {
        this.juros = juros;
    }

    public boolean isCancelada() {
        return cancelada;
    }

    public void setCancelada(boolean cancelada) {
        this.cancelada = cancelada;
    }

    public Cambio getCambio() {
        return cambio;
    }

    public void setCambio(Cambio cambio) {
        this.cambio = cambio;
    }

    public Titulo getTitulo() {
        return titulo;
    }

    public void setTitulo(Titulo titulo) {
        this.titulo = titulo;
    }

    public Recepcao getRecepcao() {
        return recepcao;
    }

    public void setRecepcao(Recepcao recepcao) {
        this.recepcao = recepcao;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public Transferencia getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(Transferencia transferencia) {
        this.transferencia = transferencia;
    }

    public DespesaProvisionada getDespesaProvisionada() {
        return despesaProvisionada;
    }

    public void setDespesaProvisionada(DespesaProvisionada despesaProvisionada) {
        this.despesaProvisionada = despesaProvisionada;
    }

    public String getEmissaoFormatada() {
        SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy");
        return emissaoFormatada.format(getEmissao().getTime());
    }

    public ReceitaProvisionada getReceitaProvisionada() {
        return receitaProvisionada;
    }

    public void setReceitaProvisionada(ReceitaProvisionada receitaProvisionada) {
        this.receitaProvisionada = receitaProvisionada;
    }

    public Baixa construir() throws DadoInvalidoException {
        return new BaixaBuilder().cancelada(cancelada).comCambio(cambio).comConhecimentoDeFrete(conhecimentoDeFrete)
                .comConta(conta).comDesconto(desconto).comDespesa(despesa).comDespesaProvisionada(despesaProvisionada)
                .comEmissao(emissao).comHistorico(historico).comJuros(juros).comMultas(multas)
                .comNaturezaFinanceira(unidadeFinanciera).comNotaEmitida(notaEmitida).comNumeroParcela(numeroParcela)
                .comPessoa(pessoa).comReceita(receita).comReceitaProvisionada(receitaProvisionada).comRecepcao(recepcao)
                .comTitulo(titulo).comTotal(total).comTransferencia(transferencia).comValor(valor).construir();
    }

    public Baixa construirComID() throws DadoInvalidoException {
        return new BaixaBuilder().cancelada(cancelada).comCambio(cambio).comConhecimentoDeFrete(conhecimentoDeFrete)
                .comConta(conta).comDesconto(desconto).comDespesa(despesa).comDespesaProvisionada(despesaProvisionada)
                .comEmissao(emissao).comHistorico(historico).comId(id).comJuros(juros).comMultas(multas)
                .comNaturezaFinanceira(unidadeFinanciera).comNotaEmitida(notaEmitida).comNumeroParcela(numeroParcela)
                .comPessoa(pessoa).comReceita(receita).comReceitaProvisionada(receitaProvisionada).comRecepcao(recepcao)
                .comTitulo(titulo).comTotal(total).comTransferencia(transferencia).comValor(valor).construir();
    }

}
