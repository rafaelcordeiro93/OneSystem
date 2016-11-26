package br.com.onesystem.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1,
        sequenceName = "SEQ_CONFCAMBIO", name = "SEQ_CONFCAMBIO")
@NamedQueries({    
    @NamedQuery(name = "ConfiguracaoCambio.busca", query = "select c from ConfiguracaoCambio c"),
    @NamedQuery(name = "ConfiguracaoCambio.buscaPessoas", query = "select p from Pessoa p where p.configuracaoCambio is not null"),
})
public class ConfiguracaoCambio implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private boolean ativo;

    @OneToOne
    private Conta contaCaixa;

    @OneToOne
    private Pessoa pessoaCaixa;

    @OneToOne
    private Despesa despesaDivisaoLucro;

    @OneToMany(mappedBy = "configuracaoCambio", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private List<Pessoa> pessoaDivisaoLucro;

    public ConfiguracaoCambio() {
    }

    public ConfiguracaoCambio(Long id, Despesa despesaDivisaoLucro, boolean ativo, Pessoa pessoaCaixa, Conta contaCaixa) {
        this.id = id;
        this.despesaDivisaoLucro = despesaDivisaoLucro;
        this.ativo = ativo;
        this.pessoaCaixa = pessoaCaixa;
        this.contaCaixa = contaCaixa;
    }

    public Long getId() {
        return id;
    }

    public Despesa getDespesaDivisaoLucro() {
        return despesaDivisaoLucro;
    }

    public List<Pessoa> getPessoaDivisaoLucro() {
        return pessoaDivisaoLucro;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public Pessoa getPessoaCaixa() {
        return pessoaCaixa;
    }

    public Conta getContaCaixa() {
        return contaCaixa;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof ConfiguracaoCambio)) {
            return false;
        }
        ConfiguracaoCambio outro = (ConfiguracaoCambio) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
