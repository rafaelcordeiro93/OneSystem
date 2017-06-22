/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Rafael-Pc
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_MODULO",
        sequenceName = "SEQ_MODULO")
public class Modulo implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_MODULO", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    @OneToMany(mappedBy = "modulo")
    private List<Janela> janelas;

    public Modulo() {
    }

    public Modulo(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Modulo)) {
            return false;
        }
        Modulo outro = (Modulo) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.getId());
    }
}
