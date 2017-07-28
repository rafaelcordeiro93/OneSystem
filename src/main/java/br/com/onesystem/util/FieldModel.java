/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import java.util.Objects;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class FieldModel {

    private String header;
    private String table;
    private Integer size;
    private Class clazz;
    private Class type;
    private String property;
    private String propertyTwo;
    private String propertyThree;
    private String propertyFour;

    public FieldModel(String header, String table, String property, Class clazz, Class type) {
        this.header = header;
        this.table = table;
        this.property = property;
        this.clazz = clazz;
        this.type = type;
        size = 20;
    }

    public FieldModel(String header, String table, String property, String propertyTwo, Class clazz, Class type) {
        this.header = header;
        this.table = table;
        this.property = property;
        this.propertyTwo = propertyTwo;
        this.clazz = clazz;
        this.type = type;
        size = 20;
    }

    public FieldModel(String header, String table, String property, String propertyTwo, String propertyThree, Class clazz, Class type) {
        this.header = header;
        this.table = table;
        this.property = property;
        this.propertyTwo = propertyTwo;
        this.propertyThree = propertyThree;
        this.clazz = clazz;
        this.type = type;
        size = 20;
    }

    public FieldModel(String header, String table, String property, String propertyTwo, String propertyThree, String propertyFour, Class clazz, Class type) {
        this.header = header;
        this.table = table;
        this.property = property;
        this.propertyTwo = propertyTwo;
        this.propertyThree = propertyThree;
        this.propertyFour = propertyFour;
        this.clazz = clazz;
        this.type = type;
        size = 20;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getFormatSize() {
        return String.valueOf(size) + "px";
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getPropertyTwo() {
        return propertyTwo;
    }

    public void setPropertyTwo(String propertyTwo) {
        this.propertyTwo = propertyTwo;
    }

    public String getPropertyThree() {
        return propertyThree;
    }

    public void setPropertyThree(String propertyThree) {
        this.propertyThree = propertyThree;
    }

    public String getPropertyFour() {
        return propertyFour;
    }

    public void setPropertyFour(String propertyFour) {
        this.propertyFour = propertyFour;
    }

    public Class getType() {
        return type;
    }
    
    public String getPropertyComplete() {
        String propertyComplete = property;
        if (propertyTwo != null) {
            propertyComplete += "." + propertyTwo;
        }
        if (propertyThree != null) {
            propertyComplete += "." + propertyThree;
        }
        if (propertyFour != null) {
            propertyComplete += "." + propertyFour;
        }
        return propertyComplete;
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
        final FieldModel other = (FieldModel) obj;
        if (!Objects.equals(this.property, other.property)) {
            return false;
        }
        if (!Objects.equals(this.clazz, other.clazz)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FieldModel{" + "header=" + header + ", table=" + table + ", size=" + size + ", clazz=" + clazz + ", property=" + property + ", propertyTwo=" + propertyTwo + ", propertyThree=" + propertyThree + ", propertyFour=" + propertyFour + '}';
    }

}
