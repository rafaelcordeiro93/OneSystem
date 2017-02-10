/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.util.BundleUtil;
import java.util.Objects;

/**
 *
 * @author Rafael
 */
public class Coluna {

    private String key;
    private String nome;

    public Coluna(String key) {
        this.key = key;
        this.nome = new BundleUtil().getLabel(key);
    }

    public String getKey() {
        return key;
    }

    public String getNome() {
        return nome;
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
        final Coluna other = (Coluna) obj;
        if (!Objects.equals(this.key, other.key)) {
            return false;
        }
        return true;
    }

}
