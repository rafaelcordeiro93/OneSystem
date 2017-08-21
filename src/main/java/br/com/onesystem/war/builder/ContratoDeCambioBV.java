package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.ContratoDeCambio;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ContratoDeCambioBV implements Serializable {

    private Long id;
    private Pessoa pessoa;
    private BigDecimal valorNegociado;
    private BigDecimal taxaNegociada;
    private BigDecimal valorCalculado;
    private boolean status;
    private Cambio cambio;
    private Moeda origem;
    private Moeda destino;

    public ContratoDeCambioBV(ContratoDeCambio contratoSelecionado) {
        this.id = contratoSelecionado.getId();
        this.pessoa = contratoSelecionado.getPessoa();
        this.valorNegociado = contratoSelecionado.getValorNegociado();
        this.taxaNegociada = contratoSelecionado.getTaxaNegociada();
        this.valorCalculado = contratoSelecionado.getValorCalculado();
        this.status = contratoSelecionado.isStatus();
        this.cambio = contratoSelecionado.getCambio();
        this.origem = contratoSelecionado.getOrigem();
        this.destino = contratoSelecionado.getDestino();
    }

    public ContratoDeCambioBV() {
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

    public BigDecimal getValorNegociado() {
        return valorNegociado;
    }

    public void setValorNegociado(BigDecimal valorNegociado) {
        this.valorNegociado = valorNegociado;
    }

    public BigDecimal getTaxaNegociada() {
        return taxaNegociada;
    }

    public void setTaxaNegociada(BigDecimal taxaNegociada) {
        this.taxaNegociada = taxaNegociada;
    }

    public BigDecimal getValorCalculado() {
        return valorCalculado;
    }

    public void setValorCalculado(BigDecimal valorCalculado) {
        this.valorCalculado = valorCalculado;
    }

    public boolean isStatus() {
        return status;
    }

    public Cambio getCambio() {
        return cambio;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }

    public Moeda getOrigem() {
        return origem;
    }

    public void setOrigem(Moeda origem) {
        this.origem = origem;
    }

    public Moeda getDestino() {
        return destino;
    }

    public void setDestino(Moeda destino) {
        this.destino = destino;
    }

    public void setCambio(Cambio cambio) {
        this.cambio = cambio;
    }
    
    public ContratoDeCambio construir() throws DadoInvalidoException {
        return new ContratoDeCambio(null, pessoa, valorNegociado, taxaNegociada, valorCalculado, status, origem, destino);
    }

    public ContratoDeCambio construirComID() throws DadoInvalidoException {
        return new ContratoDeCambio(id, pessoa, valorNegociado, taxaNegociada, valorCalculado, status, origem, destino);
    }
}
