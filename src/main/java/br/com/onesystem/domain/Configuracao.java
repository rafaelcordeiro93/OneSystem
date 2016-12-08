package br.com.onesystem.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1,
        sequenceName = "SEQ_CONFIGUCARAO", name = "SEQ_CONFIGUCARAO")
@NamedQueries({
    @NamedQuery(name = "Configuracao.busca", query = "select c from Configuracao c")})
public class Configuracao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONFIGUCARAO")
    private Long id;

    @OneToOne
    private Despesa despesaDeComissao;

    @OneToOne
    private Moeda moedaPadrao;

    public Configuracao() {
    }

    public Configuracao(Long id, Despesa despesaDeComissao, Moeda moedaPadrao) {
        this.id = id;
        this.despesaDeComissao = despesaDeComissao;
        this.moedaPadrao = moedaPadrao;
    }

    public Long getId() {
        return id;
    }

    public Despesa getDespesaDeComissao() {
        return despesaDeComissao;
    }

    public Moeda getMoedaPadrao() {
        return moedaPadrao;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Configuracao)) {
            return false;
        }
        Configuracao outro = (Configuracao) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Configuracao{" + "id=" + id + ", despesaDeComissao=" + despesaDeComissao + ", moedaPadrao=" + moedaPadrao + '}';
    }

}
