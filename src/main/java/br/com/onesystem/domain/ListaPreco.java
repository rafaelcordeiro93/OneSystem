package br.com.onesystem.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_LISTAPRECO",
        sequenceName = "SEQ_LISTAPRECO")
public class ListaPreco implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_LISTAPRECO", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;

    public ListaPreco() {
    }

    public ListaPreco(Long id, String nombre) {
        this.id = id;
        this.nome = nombre;
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
        if (!(objeto instanceof ListaPreco)) {
            return false;
        }
        ListaPreco outro = (ListaPreco) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
