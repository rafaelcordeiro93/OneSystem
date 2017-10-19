package br.com.onesystem.war.builder;

import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.builder.ConfiguracaoEstoqueBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;

public class ConfiguracaoEstoqueBV implements Serializable, BuilderView<ConfiguracaoEstoque> {

    private Long id;
    private ContaDeEstoque contaDeEstoqueEmpresa;
    private ListaDePreco listaDePreco;
    private Deposito depositoPadrao;
    private Operacao operacaoDeAjusteDeEstoque;

    public ConfiguracaoEstoqueBV(ConfiguracaoEstoque c) {
        this.id = c.getId();
        this.contaDeEstoqueEmpresa = c.getContaDeEstoqueEmpresa();
        this.listaDePreco = c.getListaDePreco();
        this.depositoPadrao = c.getDepositoPadrao();
        this.operacaoDeAjusteDeEstoque = c.getAjusteDeEstoquePadrao();
    }

    public ConfiguracaoEstoqueBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContaDeEstoque getContaDeEstoqueEmpresa() {
        return contaDeEstoqueEmpresa;
    }

    public void setContaDeEstoqueEmpresa(ContaDeEstoque contaDeEstoqueEmpresa) {
        this.contaDeEstoqueEmpresa = contaDeEstoqueEmpresa;
    }

    public ListaDePreco getListaDePreco() {
        return listaDePreco;
    }

    public void setListaDePreco(ListaDePreco listaDePreco) {
        this.listaDePreco = listaDePreco;
    }

    public Deposito getDepositoPadrao() {
        return depositoPadrao;
    }

    public void setDepositoPadrao(Deposito depositoPadrao) {
        this.depositoPadrao = depositoPadrao;
    }

    public Operacao getOperacaoDeAjusteDeEstoque() {
        return operacaoDeAjusteDeEstoque;
    }

    public void setOperacaoDeAjusteDeEstoque(Operacao operacaoDeAjusteDeEstoque) {
        this.operacaoDeAjusteDeEstoque = operacaoDeAjusteDeEstoque;
    }
    
    public ConfiguracaoEstoque construir() throws DadoInvalidoException {
        return new ConfiguracaoEstoqueBuilder().comContaDeEstoque(contaDeEstoqueEmpresa)
                .comListaDePreco(listaDePreco).comOperacaoDeAjusteDeEstoque(operacaoDeAjusteDeEstoque).construir();
    }

    @Override
    public ConfiguracaoEstoque construirComID() throws DadoInvalidoException {
        return new ConfiguracaoEstoqueBuilder().comId(id).comContaDeEstoque(contaDeEstoqueEmpresa).comDepositoPadrao(depositoPadrao)
                .comListaDePreco(listaDePreco).comOperacaoDeAjusteDeEstoque(operacaoDeAjusteDeEstoque).construir();
    }

}
