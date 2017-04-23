package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.domain.ConfiguracaoFinanceiro;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.ConfiguracaoFinanceiroBV;
import br.com.onesystem.war.service.ConfiguracaoFinanceiroService;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ConfiguracaoFinanceiroView implements Serializable {

    private ConfiguracaoFinanceiroBV configuracaoFinanceiroBV;
    private ConfiguracaoFinanceiro configuracao;

    @Inject
    private ConfiguracaoFinanceiroService service;

    @PostConstruct
    public void init() {
        configuracao = service.buscar();
        if (configuracao == null) {
            configuracaoFinanceiroBV = new ConfiguracaoFinanceiroBV();
        } else {
            configuracaoFinanceiroBV = new ConfiguracaoFinanceiroBV(configuracao);
        }
    }

    public void update() {
        try {
            ConfiguracaoFinanceiro conf = configuracaoFinanceiroBV.construir();
            if (configuracao == null) {
                new AdicionaDAO<ConfiguracaoFinanceiro>().adiciona(conf);
                configuracao = conf;
            } else {
                new AtualizaDAO<ConfiguracaoFinanceiro>().atualiza(conf);
            }
            InfoMessage.print("Configurações gravadas com sucesso!");
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void selecionaConta(SelectEvent event) {
        Conta conta = (Conta) event.getObject();
        configuracaoFinanceiroBV.setContaPadrao(conta);
    }

    public void removeConta() {
        configuracaoFinanceiroBV.setContaPadrao(null);
    }

    public ConfiguracaoFinanceiroBV getConfiguracaoBV() {
        return configuracaoFinanceiroBV;
    }

    public void setConfiguracaoBV(ConfiguracaoFinanceiroBV configuracaoBV) {
        this.configuracaoFinanceiroBV = configuracaoBV;
    }

    public ConfiguracaoFinanceiro getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(ConfiguracaoFinanceiro configuracao) {
        this.configuracao = configuracao;
    }

    public ConfiguracaoFinanceiroService getService() {
        return service;
    }

    public void setService(ConfiguracaoFinanceiroService service) {
        this.service = service;
    }

}
