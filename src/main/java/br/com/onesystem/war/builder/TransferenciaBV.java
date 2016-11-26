package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.math.BigDecimal;

public class TransferenciaBV implements Serializable {

    private Conta origem;
    private Conta destino;
    private BigDecimal valor;
    private BigDecimal valorConvertido;
   
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

    public void setValorConvertido(BigDecimal valorConvertido) {
        this.valorConvertido = valorConvertido;
    }
    
    public Transferencia construir() throws DadoInvalidoException{
        return new Transferencia(null, origem, destino, valor, valorConvertido);
    }
    
}
