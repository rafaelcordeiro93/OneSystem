package br.com.onesystem.war.builder;

import br.com.onesystem.domain.ConfiguracaoVenda;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.builder.ConfiguracaoVendaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;

public class ConfiguracaoVendaBV implements Serializable, BuilderView<ConfiguracaoVenda> {

    private Long id;
    private FormaDeRecebimento formaDeRecebimentoDevolucaoEmpresa;
    private Operacao operacaoCondicional;
    private Operacao operacaoDevolucaoCondicional;

    public ConfiguracaoVendaBV(ConfiguracaoVenda c) {
        this.id = c.getId();
        this.formaDeRecebimentoDevolucaoEmpresa = c.getFormaDeRecebimentoDevolucaoEmpresa();
        this.operacaoCondicional = c.getOperacaoDeCondicional();
        this.operacaoDevolucaoCondicional = c.getOperacaoDeDevolucaoCondicional();
    }

    public ConfiguracaoVendaBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Operacao getOperacaoCondicional() {
        return operacaoCondicional;
    }

    public void setOperacaoCondicional(Operacao operacaoCondicional) {
        this.operacaoCondicional = operacaoCondicional;
    }

    public Operacao getOperacaoDevolucaoCondicional() {
        return operacaoDevolucaoCondicional;
    }

    public void setOperacaoDevolucaoCondicional(Operacao operacaoDevolucaoCondicional) {
        this.operacaoDevolucaoCondicional = operacaoDevolucaoCondicional;
    }

    public FormaDeRecebimento getFormaDeRecebimentoDevolucaoEmpresa() {
        return formaDeRecebimentoDevolucaoEmpresa;
    }

    public void setFormaDeRecebimentoDevolucaoEmpresa(FormaDeRecebimento formaDeRecebimentoDevolucaoEmpresa) {
        this.formaDeRecebimentoDevolucaoEmpresa = formaDeRecebimentoDevolucaoEmpresa;
    }

    public ConfiguracaoVenda construir() throws DadoInvalidoException {
        return new ConfiguracaoVendaBuilder().comFormaDeRecebimentoDevolucaoEmpresa(formaDeRecebimentoDevolucaoEmpresa)
                .comOperacaoDeCondicional(operacaoCondicional).comOperacaoDeDevolucaoCondicional(operacaoDevolucaoCondicional).construir();
    }

    @Override
    public ConfiguracaoVenda construirComID() throws DadoInvalidoException {
        return new ConfiguracaoVendaBuilder().comId(id).comFormaDeRecebimentoDevolucaoEmpresa(formaDeRecebimentoDevolucaoEmpresa)
                .comOperacaoDeCondicional(operacaoCondicional).comOperacaoDeDevolucaoCondicional(operacaoDevolucaoCondicional).construir();
    }

}
