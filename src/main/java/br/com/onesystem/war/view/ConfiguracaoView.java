package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Despesa;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.ConfiguracaoBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class ConfiguracaoView implements Serializable {

    private ConfiguracaoBV configuracaoBV;
    private Configuracao configuracao;

    @ManagedProperty("#{configuracaoService}")
    private ConfiguracaoService service;

    @PostConstruct
    public void init() {
        try {
            configuracao = service.buscar();
            if (configuracao != null) {
                configuracaoBV = new ConfiguracaoBV(configuracao);
            }
        } catch (EDadoInvalidoException ex) {
            configuracaoBV = new ConfiguracaoBV();
        }
    }

    public void update() {
        try {
            Configuracao conf = configuracaoBV.construir();
            if (configuracao == null) {
                new AdicionaDAO<Configuracao>().adiciona(conf);
                configuracao = conf;
            } else {
                new AtualizaDAO<Configuracao>(Configuracao.class).atualiza(conf);
            }
            InfoMessage.print(new BundleUtil().getMessage("Configuracao_gravada"));
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void selecionaMoeda(SelectEvent event) {
        Moeda moeda = (Moeda) event.getObject();
        configuracaoBV.setMoedaPadrao(moeda);
    }

    public void removeMoeda() {
        configuracaoBV.setMoedaPadrao(null);
    }

    public void selecionaDespesaDeComissao(SelectEvent event) {
        Despesa despesa = (Despesa) event.getObject();
        configuracaoBV.setDespesaDeComissao(despesa);
    }

    public void removeDespesaDeComissao() {
        configuracaoBV.setMoedaPadrao(null);
    }

    public ConfiguracaoBV getConfiguracaoBV() {
        return configuracaoBV;
    }

    public void setConfiguracaoBV(ConfiguracaoBV configuracaoBV) {
        this.configuracaoBV = configuracaoBV;
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public ConfiguracaoService getService() {
        return service;
    }

    public void setService(ConfiguracaoService service) {
        this.service = service;
    }

}
