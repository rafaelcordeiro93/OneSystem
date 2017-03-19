package br.com.onesystem.war.builder;

import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.builder.ConfiguracaoEstoqueBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;

public class ConfiguracaoEstoqueBV implements Serializable {

    private Long id;
    private ContaDeEstoque contaDeEstoqueEmpresa;
    private ListaDePreco listaDePreco;

    public ConfiguracaoEstoqueBV(ConfiguracaoEstoque configuracaoSelecionada) {
        this.id = configuracaoSelecionada.getId();
        this.contaDeEstoqueEmpresa = configuracaoSelecionada.getContaDeEstoqueEmpresa();
        this.listaDePreco = configuracaoSelecionada.getListaDePreco();
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

    public ConfiguracaoEstoque construir() throws DadoInvalidoException {
        return new ConfiguracaoEstoqueBuilder().comId(id).comContaDeEstoque(contaDeEstoqueEmpresa)
                .comListaDePreco(listaDePreco).construir();
    }

}
