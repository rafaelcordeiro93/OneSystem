package br.com.onesystem.war.view;

import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.ConfiguracaoEstoqueBV;
import br.com.onesystem.war.service.ConfiguracaoEstoqueService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ConfiguracaoEstoqueView extends BasicMBImpl<ConfiguracaoEstoque, ConfiguracaoEstoqueBV> implements Serializable {

    private ConfiguracaoEstoqueBV configuracaoEstoqueBV;
    private ConfiguracaoEstoque configuracao;

    @Inject
    private ConfiguracaoEstoqueService service;

    @PostConstruct
    public void init() {
        configuracao = service.buscar();
        if (configuracao == null) {
            configuracaoEstoqueBV = new ConfiguracaoEstoqueBV();
        } else {
            configuracaoEstoqueBV = new ConfiguracaoEstoqueBV(configuracao);
        }
    }

    public void update() {
        try {
            if (configuracaoEstoqueBV.getId() == null) {
                ConfiguracaoEstoque c = configuracaoEstoqueBV.construir();
                addNoBanco(c);
                configuracaoEstoqueBV.setId(c.getId());
            } else {
                updateNoBanco(configuracaoEstoqueBV.construirComID());
            }
            InfoMessage.print(new BundleUtil().getLabel("Configuracoes_Gravadas"));
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof ContaDeEstoque) {
            ContaDeEstoque conta = (ContaDeEstoque) obj;
            configuracaoEstoqueBV.setContaDeEstoqueEmpresa(conta);
        } else if (obj instanceof ListaDePreco) {
            ListaDePreco lp = (ListaDePreco) obj;
            configuracaoEstoqueBV.setListaDePreco(lp);
        } else if (obj instanceof Deposito) {
            configuracaoEstoqueBV.setDepositoPadrao((Deposito) obj);
        }
    }

    public ConfiguracaoEstoqueBV getConfiguracaoBV() {
        return configuracaoEstoqueBV;
    }

    public void setConfiguracaoBV(ConfiguracaoEstoqueBV configuracaoBV) {
        this.configuracaoEstoqueBV = configuracaoBV;
    }

    public ConfiguracaoEstoque getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(ConfiguracaoEstoque configuracao) {
        this.configuracao = configuracao;
    }

    public ConfiguracaoEstoqueService getService() {
        return service;
    }

    public void setService(ConfiguracaoEstoqueService service) {
        this.service = service;
    }

    @Override
    public void limparJanela() {
    }

}
