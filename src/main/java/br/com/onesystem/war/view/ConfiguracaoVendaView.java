package br.com.onesystem.war.view;

import br.com.onesystem.domain.ConfiguracaoVenda;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.ConfiguracaoVendaBV;
import br.com.onesystem.war.service.ConfiguracaoVendaService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ConfiguracaoVendaView extends BasicMBImpl<ConfiguracaoVenda, ConfiguracaoVendaBV> implements Serializable {

    private ConfiguracaoVendaBV configuracaoVendaBV;
    private ConfiguracaoVenda configuracao;

    @Inject
    private ConfiguracaoVendaService service;

    @PostConstruct
    public void init() {
        try {
            configuracao = service.buscar();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
        if (configuracao == null) {
            configuracaoVendaBV = new ConfiguracaoVendaBV();
        } else {
            configuracaoVendaBV = new ConfiguracaoVendaBV(configuracao);
        }
    }

    public void update() {
        try {
            if (configuracaoVendaBV.getId() == null) {
                ConfiguracaoVenda c = configuracaoVendaBV.construir();
                addNoBanco(c);
                configuracaoVendaBV.setId(c.getId());
            } else {
                updateNoBanco(configuracaoVendaBV.construirComID());
            }
            InfoMessage.print(new BundleUtil().getLabel("Configuracoes_Gravadas"));
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof FormaDeRecebimento) {
            FormaDeRecebimento formaDeRecebimento = (FormaDeRecebimento) obj;
            configuracaoVendaBV.setFormaDeRecebimentoDevolucaoEmpresa(formaDeRecebimento);
        }
    }

    public ConfiguracaoVendaBV getConfiguracaoBV() {
        return configuracaoVendaBV;
    }

    public void setConfiguracaoBV(ConfiguracaoVendaBV configuracaoBV) {
        this.configuracaoVendaBV = configuracaoBV;
    }

    public ConfiguracaoVenda getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(ConfiguracaoVenda configuracao) {
        this.configuracao = configuracao;
    }

    public ConfiguracaoVendaService getService() {
        return service;
    }

    public void setService(ConfiguracaoVendaService service) {
        this.service = service;
    }

    @Override
    public void limparJanela() {
    }

}
