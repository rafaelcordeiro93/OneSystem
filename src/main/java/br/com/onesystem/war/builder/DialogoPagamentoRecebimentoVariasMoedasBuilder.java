/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Conta;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Rafael
 */
public class DialogoPagamentoRecebimentoVariasMoedasBuilder implements Serializable {

    private Long id;
    private Conta conta;
    private BigDecimal valor;
    private BigDecimal valorConvertido;

    public DialogoPagamentoRecebimentoVariasMoedasBuilder() {
    }

    public DialogoPagamentoRecebimentoVariasMoedasBuilder(DialogoPagamentoRecebimentoVariasMoedasBuilder dlgSelecionado) {
        this.id = dlgSelecionado.getId();
        this.conta = dlgSelecionado.getConta();
        this.valor = dlgSelecionado.getValor();
        this.valorConvertido = dlgSelecionado.getValorConvertido();
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
