package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.domain.builder.TransferenciaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.valueobjects.TipoLancamentoBancario;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TransferenciaBV implements BuilderView<Transferencia>, Serializable {
    
    private Long id;
    private Date emissao;
    private Conta origem;
    private Conta destino;
    private Cotacao cotacaoDeOrigem;
    private Cotacao cotacaoDeDestino;
    private BigDecimal valor;
    private BigDecimal valorConvertido;
    private List<Baixa> baixas;
    private TipoLancamentoBancario tipoLancamentoBancario;
    private boolean estornado = false;
    
    public TransferenciaBV() {
    }
    
    public TransferenciaBV(Transferencia transferencia) {
        this.id = transferencia.getId();
        this.origem = transferencia.getOrigem();
        this.destino = transferencia.getDestino();
        this.valor = transferencia.getValor();
        this.valorConvertido = transferencia.getValorConvertido();
        this.baixas = transferencia.getBaixas();
        this.emissao = transferencia.getEmissao();
        this.tipoLancamentoBancario = transferencia.getTipoLancamentoBancario();
        this.estornado = transferencia.isEstornado();
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
    
    public Transferencia construir() throws DadoInvalidoException {
        return new TransferenciaBuilder().comDestino(destino).comOrigem(origem).comValor(valor)
                .comValorConvertido(valorConvertido).comBaixas(baixas).comEmissao(emissao)
                .comTipoLancamentoBancario(tipoLancamentoBancario).comEstornado(estornado).construir();
    }
    
    @Override
    public Transferencia construirComID() throws DadoInvalidoException {
        return new TransferenciaBuilder().comId(id).comDestino(destino).comOrigem(origem).
                comValor(valor).comValorConvertido(valorConvertido).comEmissao(emissao)
                .comTipoLancamentoBancario(tipoLancamentoBancario).comBaixas(baixas).comEstornado(estornado).construir();
    }
    
}
