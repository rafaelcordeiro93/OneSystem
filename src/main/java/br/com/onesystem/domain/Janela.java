/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Rafael-Pc
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_JANELA",
        sequenceName = "SEQ_JANELA")
public class Janela implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_JANELA", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    private String endereco;
    @ManyToOne
    private Modulo modulo;

    public Janela() {
    }

    public Janela(Long id, String nome, String endereco, Modulo modulo) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.modulo = modulo;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public Modulo getModulo() {
        return modulo;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Janela)) {
            return false;
        }
        Janela outro = (Janela) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.getId());
    }
}
