package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.inject.Any;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Any
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, sequenceName = "SEQ_CONFIGURACAOESTOQUE",
        name = "SEQ_CONFIGURACAOESTOQUE")
public class ConfiguracaoEstoque implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_CONFIGURACAOESTOQUE", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{Conta_de_estoque_empresa_not_null}")
    @OneToOne
    private ContaDeEstoque contaDeEstoqueEmpresa;
    @OneToOne
    private ListaDePreco listaDePreco;
    @OneToOne
    private Deposito depositoPadrao;
    @OneToOne
    private Operacao ajusteDeEstoquePadrao;

    public ConfiguracaoEstoque() {
    }

    public ConfiguracaoEstoque(Long id, ContaDeEstoque contaDeEstoque, ListaDePreco listaDePreco, Deposito depositoPadrao, Operacao ajusteDeEstoquePadrao) throws DadoInvalidoException {
        this.id = id;
        this.contaDeEstoqueEmpresa = contaDeEstoque;
        this.listaDePreco = listaDePreco;
        this.depositoPadrao = depositoPadrao;
        this.ajusteDeEstoquePadrao = ajusteDeEstoquePadrao;
        ehValido();
    }
    
    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("contaDeEstoqueEmpresa");
        new ValidadorDeCampos<>().valida(this, campos);
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

    public Operacao getAjusteDeEstoquePadrao() {
        return ajusteDeEstoquePadrao;
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
