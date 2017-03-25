/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import java.util.Objects;

/**
 *
 * @author Rafael
 */
public class GenericLayout {

    private String tabela;
    private String coluna;
    private Integer left;
    private Integer top;

    public GenericLayout(String tabela, String coluna, Integer left, Integer top) {
        this.tabela = tabela;
        this.coluna = coluna;
        this.left = left;
        this.top = top;
    }

    public String getTabela() {
        return tabela;
    }
    
    public String getColuna() {
        return coluna;
    }

    public Integer getLeft() {
        return left;
    }

    public Integer getTop() {
        return top;
    }

    @Override
    public String toString() {
        return "GenericLayout{" + "tabela=" + tabela + ", coluna=" + coluna + ", left=" + left + ", top=" + top + '}';
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
        final GenericLayout other = (GenericLayout) obj;
        if (!Objects.equals(this.coluna, other.coluna)) {
            return false;
        }
        return true;
    }
    
    
}
