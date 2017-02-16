package br.com.onesystem.domain;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, sequenceName = "SEQ_CONFIGURACAOFINANCEIO",
        name = "SEQ_CONFIGURACAOFINANCEIRO")
@NamedQueries({
    @NamedQuery(name = "ConfiguracaoFinanceiro.busca", query = "select c from ConfiguracaoFinanceiro c")
})
public class ConfiguracaoFinanceiro implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_CONFIGURACAOFINANCEIRO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToOne
    private Conta contaPadrao;

    public ConfiguracaoFinanceiro() {
    }

    public ConfiguracaoFinanceiro(Long id, Conta contaPadrao) {
        this.id = id;
        this.contaPadrao = contaPadrao;
    }

    public Long getId() {
        return id;
    }

    public Conta getContaPadrao() {
        return contaPadrao;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof ConfiguracaoFinanceiro)) {
            return false;
        }
        ConfiguracaoFinanceiro outro = (ConfiguracaoFinanceiro) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "ConfiguracaoFinanceiro{" + "id=" + id + ", contaPadrao=" + contaPadrao + '}';
    }

}
