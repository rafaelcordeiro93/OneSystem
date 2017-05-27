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
public class Model<T> {

    private Long id;
    private T t;

    public Model() {
    }

    public Model(Long id, T t) {
        this.id = id;
        this.t = t;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public T getObject() {
        return t;
    }

    public void setObject(T t) {
        this.t = t;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Model<?> other = (Model<?>) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model{" + "id=" + id + ", t=" + t + '}';
    }

}
