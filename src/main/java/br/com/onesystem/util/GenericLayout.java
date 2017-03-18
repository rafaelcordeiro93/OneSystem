/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

/**
 *
 * @author Rafael
 */
public class GenericLayout {

    private String coluna;
    private Integer left;
    private Integer top;

    public GenericLayout(String coluna, Integer left, Integer top) {
        this.coluna = coluna;
        this.left = left;
        this.top = top;
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
        return "GenericLayout{" + "coluna=" + coluna + ", left=" + left + ", top=" + top + '}';
    }

}
