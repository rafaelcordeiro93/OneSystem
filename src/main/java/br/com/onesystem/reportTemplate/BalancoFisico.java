/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.reportTemplate;

import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Moeda;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class BalancoFisico {

    private Item item;
    private BigDecimal custoMedio;
    private BigDecimal saldo;
   

    public BalancoFisico(Item item, BigDecimal custoMedio, Date data) {
        this.item = item;
        this.custoMedio = custoMedio;
        this.saldo = item.getSaldo(data);
       
    }

    public Item getItem() {
        return item;
    }

    public BigDecimal getCustoMedio() {
        return custoMedio;
    }

    public BigDecimal getCustoTotal() {
        return custoMedio.multiply(getSaldo());
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof BalancoFisico)) {
            return false;
        }
        BalancoFisico outro = (BalancoFisico) objeto;
        if (this.item == null) {
            return false;
        }
        return this.item.equals(outro.item);
    }

}
