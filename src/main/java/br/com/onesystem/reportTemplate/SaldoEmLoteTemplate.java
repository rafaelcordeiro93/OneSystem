/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.reportTemplate;

import br.com.onesystem.domain.LoteItem;
import br.com.onesystem.domain.Item;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Rafael
 */
public class SaldoEmLoteTemplate {
    
    private LoteItem loteItem;
    
    private Item item;
    
    private BigDecimal saldo;

    public SaldoEmLoteTemplate(LoteItem loteItem, Item item, BigDecimal saldo) {
        this.loteItem = loteItem;
        this.item = item;
        this.saldo = saldo;
    }

    public LoteItem getLoteItem() {
        return loteItem;
    }

    public Item getItem() {
        return item;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    @Override
    public String toString() {
        return "SaldoPorLoteItemTemplate{" + "loteItem=" + loteItem + ", item=" + item + ", saldo=" + saldo + '}';
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SaldoEmLoteTemplate other = (SaldoEmLoteTemplate) obj;
        if (!Objects.equals(this.loteItem.getId(), other.loteItem.getId())) {
            return false;
        }
        return true;
    }
    
    
}
