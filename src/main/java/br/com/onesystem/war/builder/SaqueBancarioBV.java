package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.SaqueBancario;
import br.com.onesystem.domain.builder.SaqueBancarioBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SaqueBancarioBV implements BuilderView<SaqueBancario>, Serializable {

    private Long id;
    private Date emissao;
    private Conta origem;
    private Conta destino;
    private Cotacao cotacaoDeOrigem;
    private Cotacao cotacaoDeDestino;
    private BigDecimal valor;
    private BigDecimal valorConvertido;
    private List<Baixa> baixas;

    public SaqueBancarioBV() {
    }

    public SaqueBancarioBV(SaqueBancario dp) {
        this.id = dp.getId();
        this.origem = dp.getOrigem();
        this.destino = dp.getDestino();
        this.valor = dp.getValor();
        this.valorConvertido = dp.getValorConvertido();
        this.baixas = dp.getBaixas();
        this.emissao = dp.getEmissao();
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

    public SaqueBancario construir() throws DadoInvalidoException {
        return new SaqueBancarioBuilder().comDestino(destino).comOrigem(origem).comValor(valor)
                .comValorConvertido(valorConvertido).comBaixas(baixas).comEmissao(emissao).construir();
    }

    @Override
    public SaqueBancario construirComID() throws DadoInvalidoException {
        return new SaqueBancarioBuilder().comId(id).comDestino(destino).comOrigem(origem).
                comValor(valor).comValorConvertido(valorConvertido).comEmissao(emissao).comBaixas(baixas).construir();
    }

}
