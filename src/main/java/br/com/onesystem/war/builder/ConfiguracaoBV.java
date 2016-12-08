package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Despesa;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;

public class ConfiguracaoBV implements Serializable {

    private Long id;
    private Moeda moedaPadrao;
    private Despesa despesaDeComissao;

    public ConfiguracaoBV(Configuracao configuracaoSelecionada) {
        this.id = configuracaoSelecionada.getId();
        this.moedaPadrao = configuracaoSelecionada.getMoedaPadrao();
    }

    public ConfiguracaoBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Moeda getMoedaPadrao() {
        return moedaPadrao;
    }

    public Despesa getDespesaDeComissao() {
        return despesaDeComissao;
    }

    public void setDespesaDeComissao(Despesa despesaDeComissao) {
        this.despesaDeComissao = despesaDeComissao;
    }

    public void setMoedaPadrao(Moeda moedaPadrao) {
        this.moedaPadrao = moedaPadrao;
    }

    public Configuracao construir() throws DadoInvalidoException {
        return new Configuracao(id, despesaDeComissao, moedaPadrao);
    }

}
