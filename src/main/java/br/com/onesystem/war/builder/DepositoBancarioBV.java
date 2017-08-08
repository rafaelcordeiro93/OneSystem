package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.DepositoBancario;
import br.com.onesystem.domain.builder.DepositoBancarioBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.valueobjects.TipoLancamentoBancario;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class DepositoBancarioBV implements BuilderView<DepositoBancario>, Serializable {

    private Long id;
    private Date emissao;
    private Conta origem;
    private Conta destino;
    private Cotacao cotacaoDeOrigem;
    private Cotacao cotacaoDeDestino;
    private BigDecimal valor;
    private BigDecimal valorConvertido;
    private List<Baixa> baixas;
    private List<Cheque> cheques;
    private TipoLancamentoBancario tipoLancamentoBancario;
    private boolean estornado = false;
    private String observacao;
    private Long idRelacaoEstorno;

    public DepositoBancarioBV() {
    }

    public DepositoBancarioBV(DepositoBancario dp) {
        this.id = dp.getId();
        this.origem = dp.getOrigem();
        this.destino = dp.getDestino();
        this.valor = dp.getValor();
        this.valorConvertido = dp.getValorConvertido();
        this.baixas = dp.getBaixas();
        this.emissao = dp.getEmissao();
        this.tipoLancamentoBancario = dp.getTipoLancamentoBancario();
        this.estornado = dp.isEstornado();
        this.observacao = dp.getObservacao();
        this.idRelacaoEstorno = dp.getIdRelacaoEstorno();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Conta getOrigem() {
        return origem;
    }

    public void setOrigem(Conta origem) {
        this.origem = origem;
    }

    public Conta getDestino() {
        return destino;
    }

    public void setDestino(Conta destino) {
        this.destino = destino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValorConvertido() {
        return valorConvertido;
    }

    public Cotacao getCotacaoDeOrigem() {
        return cotacaoDeOrigem;
    }

    public void setCotacaoDeOrigem(Cotacao cotacaoDeOrigem) {
        this.cotacaoDeOrigem = cotacaoDeOrigem;
    }

    public Cotacao getCotacaoDeDestino() {
        return cotacaoDeDestino;
    }

    public void setCotacaoDeDestino(Cotacao cotacaoDeDestino) {
        this.cotacaoDeDestino = cotacaoDeDestino;
    }

    public void setValorConvertido(BigDecimal valorConvertido) {
        this.valorConvertido = valorConvertido;
    }

    public List<Baixa> getBaixas() {
        return baixas;
    }

    public void setBaixas(List<Baixa> baixas) {
        this.baixas = baixas;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public List<Cheque> getCheques() {
        return cheques;
    }

    public void setCheques(List<Cheque> cheques) {
        this.cheques = cheques;
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

    public DepositoBancario construir() throws DadoInvalidoException {
        return new DepositoBancarioBuilder().comDestino(destino).comOrigem(origem).comValor(valor)
                .comValorConvertido(valorConvertido).comBaixas(baixas).comEmissao(emissao).comCheques(cheques)
                .comTipoLancamentoBancario(tipoLancamentoBancario).comEstornado(estornado).comIdRelacaoEstorno(idRelacaoEstorno).construir();
    }

    @Override
    public DepositoBancario construirComID() throws DadoInvalidoException {
        return new DepositoBancarioBuilder().comId(id).comDestino(destino).comOrigem(origem).comCheques(cheques).
                comValor(valor).comValorConvertido(valorConvertido).comEmissao(emissao).comBaixas(baixas)
                .comTipoLancamentoBancario(tipoLancamentoBancario).comEstornado(estornado).comIdRelacaoEstorno(idRelacaoEstorno).construir();
    }

}
