package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.LancamentoBancario;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.domain.builder.LancamentoBancarioBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.valueobjects.TipoLancamentoBancario;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class LancamentoBancarioBV implements BuilderView<LancamentoBancario>, Serializable {

    private Long id;
    private Date emissao;
    private Conta conta;
    private Cotacao cotacaoDeConta;
    private TipoReceita receita;
    private BigDecimal valor;
    private TipoDespesa despesa;
    private List<Baixa> baixas;
    private TipoLancamentoBancario tipoLancamentoBancario;
    private boolean estornado = false;
    private String observacao;
    private Long idRelacaoEstorno;
    private Filial filial;

    public LancamentoBancarioBV() {
    }

    public LancamentoBancarioBV(LancamentoBancario dp) {
        this.id = dp.getId();
        this.conta = dp.getConta();
        this.despesa = dp.getDespesa();
        this.receita = dp.getReceita();
        this.valor = dp.getValor();
        this.baixas = dp.getBaixas();
        this.emissao = dp.getEmissao();
        this.tipoLancamentoBancario = dp.getTipoLancamentoBancario();
        this.estornado = dp.isEstornado();
        this.observacao = dp.getObservacao();
        this.idRelacaoEstorno = dp.getIdRelacaoEstorno();
        this.filial = filial;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Cotacao getCotacaoDeConta() {
        return cotacaoDeConta;
    }

    public void setCotacaoDeConta(Cotacao cotacaoDeConta) {
        this.cotacaoDeConta = cotacaoDeConta;
    }

    public TipoReceita getReceita() {
        return receita;
    }

    public void setReceita(TipoReceita receita) {
        this.receita = receita;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public TipoDespesa getDespesa() {
        return despesa;
    }

    public void setDespesa(TipoDespesa despesa) {
        this.despesa = despesa;
    }

    public List<Baixa> getBaixas() {
        return baixas;
    }

    public void setBaixas(List<Baixa> baixas) {
        this.baixas = baixas;
    }

    public TipoLancamentoBancario getTipoLancamentoBancario() {
        return tipoLancamentoBancario;
    }

    public void setTipoLancamentoBancario(TipoLancamentoBancario tipoLancamentoBancario) {
        this.tipoLancamentoBancario = tipoLancamentoBancario;
    }

    public boolean isEstornado() {
        return estornado;
    }

    public void setEstornado(boolean estornado) {
        this.estornado = estornado;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Long getIdRelacaoEstorno() {
        return idRelacaoEstorno;
    }

    public void setIdRelacaoEstorno(Long idRelacaoEstorno) {
        this.idRelacaoEstorno = idRelacaoEstorno;
    }

    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }
    
    @Override
    public LancamentoBancario construir() throws DadoInvalidoException {
        return new LancamentoBancarioBuilder().comConta(conta).comValor(valor).comDespesa(despesa).comReceita(receita).comEmissao(emissao).comBaixas(baixas)
                .comTipoLancamentoBancario(tipoLancamentoBancario).comEstornado(estornado).comIdRelacaoEstorno(idRelacaoEstorno).comObservacao(observacao).
                comFilial(filial).construir();
    }

    @Override
    public LancamentoBancario construirComID() throws DadoInvalidoException {
        return new LancamentoBancarioBuilder().comId(id).comConta(conta).comValor(valor).comDespesa(despesa).comReceita(receita).comEmissao(emissao).comBaixas(baixas)
                .comTipoLancamentoBancario(tipoLancamentoBancario).comEstornado(estornado).comIdRelacaoEstorno(idRelacaoEstorno).comObservacao(observacao).
                comFilial(filial).construir();
    }

}
