package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.ConfiguracaoBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.TipoDeCalculoDeCusto;
import br.com.onesystem.valueobjects.TipoDeFormacaoDePreco;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ConfiguracaoView extends BasicMBImpl<Configuracao, ConfiguracaoBV> implements Serializable {

    private ConfiguracaoBV configuracaoBV;
    private Configuracao configuracao;

    @Inject
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
            Configuracao conf = configuracaoBV.construirComID();
            if (configuracao == null) {
                new AdicionaDAO<>().adiciona(conf);
                configuracao = conf;
            } else {
                new AtualizaDAO<>().atualiza(conf);
            }
            InfoMessage.print(new BundleUtil().getMessage("Configuracao_gravada"));
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Moeda) {
            configuracaoBV.setMoedaPadrao((Moeda) obj);
        } else if (obj instanceof TipoDespesa) {
            configuracaoBV.setDespesaDeComissao((TipoDespesa) obj);
        }
    }

    public List<TipoDeFormacaoDePreco> getTipoFormacaoDePreco() {
        return Arrays.asList(TipoDeFormacaoDePreco.values());
    }

    public List<TipoDeCalculoDeCusto> getTipoDeCalculoDeCusto() {
        return Arrays.asList(TipoDeCalculoDeCusto.values());
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

    @Override
    public void limparJanela() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
