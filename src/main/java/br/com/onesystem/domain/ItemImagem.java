/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Rafael
 */
@Entity
@SequenceGenerator(sequenceName = "SEQ_ITEMIMAGEM", initialValue = 1, allocationSize = 1,
        name = "SEQ_ITEMIMAGEM")
public class ItemImagem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEMIMAGEM")
    private Long id;
    private String nome;
    private String tipo;
    @Lob
    @Column(columnDefinition = "BIGINT")
    private byte[] imagem;
    @ManyToOne
    private Item item;

    public ItemImagem() {
    }

    public ItemImagem(Long id, String nome, String tipo, byte[] imagem, Item item) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.item = item;
        this.imagem = imagem;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    public byte[] getImagem() {
        return imagem;
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
        final ItemImagem other = (ItemImagem) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ItemImagem{" + "id=" + id + ", nome=" + nome + ", tipo=" + tipo + '}';
    }
}
