package br.com.onesystem.war.builder;

import br.com.onesystem.domain.ConfiguracaoFinanceiro;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;

public class ConfiguracaoFinanceiroBV implements Serializable {

    private Long id;
    private Conta contaPadrao;   

    public ConfiguracaoFinanceiroBV(ConfiguracaoFinanceiro configuracaoSelecionada) {
        this.id = configuracaoSelecionada.getId();
        this.contaPadrao = configuracaoSelecionada.getContaPadrao();
    }

    public ConfiguracaoFinanceiroBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Conta getContaPadrao() {
        return contaPadrao;
    }

    public void setContaPadrao(Conta contaPadrao) {
        this.contaPadrao = contaPadrao;
    }

    public ConfiguracaoFinanceiro construir() throws DadoInvalidoException {
        return new ConfiguracaoFinanceiro(id, contaPadrao);
    }

}
