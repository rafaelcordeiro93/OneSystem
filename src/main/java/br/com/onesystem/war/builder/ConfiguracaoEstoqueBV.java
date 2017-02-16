package br.com.onesystem.war.builder;

import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;

public class ConfiguracaoEstoqueBV implements Serializable {

    private Long id;
    private ContaDeEstoque contaDeEstoqueEmpresa;

    public ConfiguracaoEstoqueBV(ConfiguracaoEstoque configuracaoSelecionada) {
        this.id = configuracaoSelecionada.getId();
        this.contaDeEstoqueEmpresa = configuracaoSelecionada.getContaDeEstoqueEmpresa();
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

    public ConfiguracaoEstoque construir() throws DadoInvalidoException {
        return new ConfiguracaoEstoque(id, contaDeEstoqueEmpresa);
    }

}
