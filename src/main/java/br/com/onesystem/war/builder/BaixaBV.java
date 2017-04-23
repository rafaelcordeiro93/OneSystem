package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.MovimentoFixo;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.Parcela;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.domain.builder.BaixaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class BaixaBV implements Serializable {

    private Long id;
    private Integer numeroParcela;
    private BigDecimal juros = BigDecimal.ZERO;
    private BigDecimal valor = BigDecimal.ZERO;
    private BigDecimal multas = BigDecimal.ZERO;
    private BigDecimal desconto = BigDecimal.ZERO;
    private String historico = "";
    private Date emissao = Calendar.getInstance().getTime();
    private OperacaoFinanceira naturezaFinanceira;
    private Cotacao cotacao;
    private Parcela perfilDeValor;
    private TipoDespesa despesa;
    private TipoReceita receita;
    private Pessoa pessoa;
    private Cambio cambio;
    private ConhecimentoDeFrete conhecimentoDeFrete;
    private Transferencia transferencia;
    private Recepcao recepcao;
    private boolean cancelada = false;
    private Nota nota;
    private MovimentoFixo movimentoFixo;

    public BaixaBV() {
    }

    public BaixaBV(Baixa baixa) {
        this.id = baixa.getId();
        this.pessoa = baixa.getPessoa();
        this.numeroParcela = baixa.getNumeroParcela();
        this.cancelada = baixa.isCancelada();
        this.valor = baixa.getValor();
        this.desconto = baixa.getDesconto();
        this.emissao = baixa.getEmissao();
        this.historico = baixa.getHistorico();
        this.naturezaFinanceira = baixa.getNaturezaFinanceira();
        this.despesa = baixa.getDespesa();
        this.cambio = baixa.getCambio();
        this.recepcao = baixa.getRecepcao();
        this.perfilDeValor = baixa.getParcela();
        this.receita = baixa.getReceita();
        this.transferencia = baixa.getTransferencia();
        this.conhecimentoDeFrete = baixa.getConhecimentoDeFrete();
        this.nota = baixa.getNota();
        this.juros = baixa.getJuros();
        this.multas = baixa.getMultas();
        this.cotacao = baixa.getCotacao();
    }

    public void selecionaTitulo(Titulo titulo) {
        this.pessoa = titulo.getPessoa();
        this.emissao = titulo.getEmissao();
        this.historico = titulo.getHistorico();
        this.naturezaFinanceira = titulo.getOperacaoFinanceira();
        this.recepcao = titulo.getRecepcao();
        this.perfilDeValor = titulo;
        this.valor = titulo.getSaldo();
        this.cambio = titulo.getCambio();
    }

    public void selecionaDespesaProvisionada(DespesaProvisionada despesaProvisionada) {
        this.pessoa = despesaProvisionada.getPessoa();
        this.valor = despesaProvisionada.getValor();
        this.emissao = despesaProvisionada.getEmissao();
        this.historico = despesaProvisionada.getHistorico();
        this.movimentoFixo = despesaProvisionada;
        this.despesa = despesaProvisionada.getTipoDespesa();
        this.cambio = despesaProvisionada.getCambio();
    }

    public void selecionaReceitaProvisionada(ReceitaProvisionada receitaProvisionada) {
        this.pessoa = receitaProvisionada.getPessoa();
        this.valor = receitaProvisionada.getValor();
        this.emissao = receitaProvisionada.getEmissao();
        this.historico = receitaProvisionada.getHistorico();
        this.movimentoFixo = receitaProvisionada;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(Integer numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public void setJuros(BigDecimal juros) {
        this.juros = juros;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getMultas() {
        return multas;
    }

    public void setMultas(BigDecimal multas) {
        this.multas = multas;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
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

    public OperacaoFinanceira getNaturezaFinanceira() {
        return naturezaFinanceira;
    }

    public MovimentoFixo getMovimentoFixo() {
        return movimentoFixo;
    }

    public void setMovimentoFixo(MovimentoFixo movimentoFixo) {
        this.movimentoFixo = movimentoFixo;
    }

    public void setNaturezaFinanceira(OperacaoFinanceira naturezaFinanceira) {
        this.naturezaFinanceira = naturezaFinanceira;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public void setCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
    }

    public Parcela getPerfilDeValor() {
        return perfilDeValor;
    }

    public void setParcela(Parcela perfilDeValor) {
        this.perfilDeValor = perfilDeValor;
    }

    public TipoDespesa getDespesa() {
        return despesa;
    }

    public void setDespesa(TipoDespesa despesa) {
        this.despesa = despesa;
    }

    public TipoReceita getReceita() {
        return receita;
    }

    public void setReceita(TipoReceita receita) {
        this.receita = receita;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Cambio getCambio() {
        return cambio;
    }

    public void setCambio(Cambio cambio) {
        this.cambio = cambio;
    }

    public ConhecimentoDeFrete getConhecimentoDeFrete() {
        return conhecimentoDeFrete;
    }

    public void setConhecimentoDeFrete(ConhecimentoDeFrete conhecimentoDeFrete) {
        this.conhecimentoDeFrete = conhecimentoDeFrete;
    }

    public Transferencia getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(Transferencia transferencia) {
        this.transferencia = transferencia;
    }

    public Recepcao getRecepcao() {
        return recepcao;
    }

    public void setRecepcao(Recepcao recepcao) {
        this.recepcao = recepcao;
    }

    public boolean isCancelada() {
        return cancelada;
    }

    public void setCancelada(boolean cancelada) {
        this.cancelada = cancelada;
    }

    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    public Baixa construir() throws DadoInvalidoException {
        return new BaixaBuilder().cancelada(cancelada).comCambio(cambio).comConhecimentoDeFrete(conhecimentoDeFrete)
                .comCotacao(cotacao).comDesconto(desconto).comDespesa(despesa)
                .comEmissao(emissao).comHistorico(historico).comJuros(juros).comMultas(multas)
                .comNaturezaFinanceira(naturezaFinanceira).comNota(nota).comNumeroParcela(numeroParcela)
                .comPessoa(pessoa).comReceita(receita).comRecepcao(recepcao).comPerfilDeValor(perfilDeValor).comMovimentoFixo(movimentoFixo)
                .comTransferencia(transferencia).comValor(valor).construir();
    }

    public Baixa construirComID() throws DadoInvalidoException {
        return new BaixaBuilder().cancelada(cancelada).comCambio(cambio).comConhecimentoDeFrete(conhecimentoDeFrete)
                .comCotacao(cotacao).comDesconto(desconto).comDespesa(despesa).comId(id)
                .comEmissao(emissao).comHistorico(historico).comJuros(juros).comMultas(multas).comMovimentoFixo(movimentoFixo)
                .comNaturezaFinanceira(naturezaFinanceira).comNota(nota).comNumeroParcela(numeroParcela)
                .comPessoa(pessoa).comReceita(receita).comRecepcao(recepcao).comPerfilDeValor(perfilDeValor)
                .comTransferencia(transferencia).comValor(valor).construir();
    }

}
