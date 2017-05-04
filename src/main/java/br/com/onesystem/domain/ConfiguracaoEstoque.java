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
@SequenceGenerator(initialValue = 1, allocationSize = 1, sequenceName = "SEQ_CONFIGURACAOESTOQUE",
        name = "SEQ_CONFIGURACAOESTOQUE")
@NamedQueries({
    @NamedQuery(name = "ConfiguracaoEstoque.busca", query = "select c from ConfiguracaoEstoque c")
})
public class ConfiguracaoEstoque implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_CONFIGURACAOESTOQUE", strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToOne
    private ContaDeEstoque contaDeEstoqueEmpresa;
    @OneToOne
    private ListaDePreco listaDePreco;
    @OneToOne
    private Deposito depositoPadrao;

    public ConfiguracaoEstoque() {
    }

    public ConfiguracaoEstoque(Long id, ContaDeEstoque contaDeEstoque, ListaDePreco listaDePreco, Deposito depositoPadrao) {
        this.id = id;
        this.contaDeEstoqueEmpresa = contaDeEstoque;
        this.listaDePreco = listaDePreco;
        this.depositoPadrao = depositoPadrao;
    }

    public Long getId() {
        return id;
    }

    public ContaDeEstoque getContaDeEstoqueEmpresa() {
        return contaDeEstoqueEmpresa;
    }

    public ListaDePreco getListaDePreco() {
        return listaDePreco;
    }

    public Deposito getDepositoPadrao() {
        return depositoPadrao;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof ConfiguracaoEstoque)) {
            return false;
        }
        ConfiguracaoEstoque outro = (ConfiguracaoEstoque) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "ConfiguracaoEstoque{" + "id=" + id + ", contaDeEstoqueEmpresa=" + contaDeEstoqueEmpresa + '}';
    }

}
