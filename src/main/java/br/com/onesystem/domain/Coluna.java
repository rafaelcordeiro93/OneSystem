/*
 * To change this license nome, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_COLUNA",
        sequenceName = "SEQ_COLUNA")
public class Coluna implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COLUNA")
    private Long id;
    private String nome;
    private String tabela;
    private Integer tamanho;
    private Class classeDeDeclaracao;
    private Class classeOriginal;
    private String propriedade;
    private String propriedadeDois;
    private String propriedadeTres;
    private String propriedadeQuatro;
    @ManyToOne
    private ModeloDeRelatorio modelo;

    public Coluna() {
    }

    public Coluna(String nome, String tabela, String propriedade, Class classeDeDeclaracao, Class classeOriginal) {
        this.nome = nome;
        this.tabela = tabela;
        this.propriedade = propriedade;
        this.classeDeDeclaracao = classeDeDeclaracao;
        this.classeOriginal = classeOriginal;
        tamanho = 20;
    }

    public Coluna(String nome, String tabela, String propriedade, String propriedadeDois, Class classeDeDeclaracao, Class classeOriginal) {
        this.nome = nome;
        this.tabela = tabela;
        this.propriedade = propriedade;
        this.propriedadeDois = propriedadeDois;
        this.classeDeDeclaracao = classeDeDeclaracao;
        this.classeOriginal = classeOriginal;
        tamanho = 20;
    }

    public Coluna(String nome, String tabela, String propriedade, String propriedadeDois, String propriedadeTres, Class classeDeDeclaracao, Class classeOriginal) {
        this.nome = nome;
        this.tabela = tabela;
        this.propriedade = propriedade;
        this.propriedadeDois = propriedadeDois;
        this.propriedadeTres = propriedadeTres;
        this.classeDeDeclaracao = classeDeDeclaracao;
        this.classeOriginal = classeOriginal;
        tamanho = 20;
    }

    public Coluna(String nome, String tabela, String propriedade, String propriedadeDois, String propriedadeTres, String propriedadeQuatro, Class classeDeDeclaracao, Class classeOriginal) {
        this.nome = nome;
        this.tabela = tabela;
        this.propriedade = propriedade;
        this.propriedadeDois = propriedadeDois;
        this.propriedadeTres = propriedadeTres;
        this.propriedadeQuatro = propriedadeQuatro;
        this.classeDeDeclaracao = classeDeDeclaracao;
        this.classeOriginal = classeOriginal;
        tamanho = 20;
    }

    public Long getId() {
        return id;
    }

    public ModeloDeRelatorio getModelo() {
        return modelo;
    }

    public String getNome() {
        return nome;
    }

    public String getTabela() {
        return tabela;
    }

    public Integer getTamanho() {
        return tamanho;
    }

    public Class getClasseDeDeclaracao() {
        return classeDeDeclaracao;
    }

    public Class getClasseOriginal() {
        return classeOriginal;
    }

    public String getPropriedade() {
        return propriedade;
    }

    public String getPropriedadeDois() {
        return propriedadeDois;
    }

    public String getPropriedadeTres() {
        return propriedadeTres;
    }

    public String getPropriedadeQuatro() {
        return propriedadeQuatro;
    }

    public String getTamanhoFormatado() {
        return String.valueOf(tamanho) + "px";
    }

    public String getPropriedadeCompleta() {
        String propriedadeCompleta = propriedade;
        if (propriedadeDois != null) {
            propriedadeCompleta += "." + propriedadeDois;
        }
        if (propriedadeTres != null) {
            propriedadeCompleta += "." + propriedadeTres;
        }
        if (propriedadeQuatro != null) {
            propriedadeCompleta += "." + propriedadeQuatro;
        }
        return propriedadeCompleta;
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
        final Coluna other = (Coluna) obj;
        if (!Objects.equals(this.propriedade, other.propriedade)) {
            return false;
        }
        if (!Objects.equals(this.propriedadeDois, other.propriedadeDois)) {
            return false;
        }
        if (!Objects.equals(this.propriedadeTres, other.propriedadeTres)) {
            return false;
        }
        if (!Objects.equals(this.propriedadeQuatro, other.propriedadeQuatro)) {
            return false;
        }
        if (!Objects.equals(this.classeOriginal, other.classeOriginal)) {
            return false;
        }
        return true;
    }

}
