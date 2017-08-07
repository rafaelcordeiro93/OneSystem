/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.reportTemplate;

import br.com.onesystem.domain.Moeda;
import java.math.BigDecimal;

/**
 *
 * @author Rafael
 */
public class ResumoDeMoeda {

    private Moeda moeda;
    private BigDecimal valor;
    private BigDecimal saldo;
    private BigDecimal valorBaixado;

    public ResumoDeMoeda(Moeda moeda, BigDecimal valor, BigDecimal saldo, BigDecimal valorBaixado) {
        this.moeda = moeda;
        this.valor = valor;
        this.saldo = saldo;
        this.valorBaixado = valorBaixado;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public BigDecimal getValorBaixado() {
        return valorBaixado;
    }

    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof ResumoDeMoeda)) {
            return false;
        }
        ResumoDeMoeda outro = (ResumoDeMoeda) objeto;
        if (this.moeda == null) {
            return false;
        }
        return this.moeda.equals(outro.moeda);
    }

}
