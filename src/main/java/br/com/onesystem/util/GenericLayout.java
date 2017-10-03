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

    private String clazz;
    private String column;
    private Integer left;
    private Integer top;
    private String align;
    private Integer size;

    public GenericLayout(String clazz, String column, Integer left, Integer top, String align, Integer size) {
        this.clazz = clazz;
        this.column = column;
        this.left = left;
        this.top = top;
        this.align = align;
        this.size = size;
    }

    public String getClazz() {
        return clazz;
    }
    
    public String getColumn() {
        return column;
    }

    public Integer getLeft() {
        return left;
    }

    public Integer getTop() {
        return top;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
    
    @Override
    public String toString() {
        return "GenericLayout{" + "clazz=" + clazz + ", column=" + column + ", left=" + left + ", top=" + top + '}';
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
        if (!Objects.equals(this.column, other.column)) {
            return false;
        }
        return true;
    }
    
    
}
