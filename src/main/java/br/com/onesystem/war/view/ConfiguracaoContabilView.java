package br.com.onesystem.war.view;

import br.com.onesystem.domain.ConfiguracaoContabil;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.ConfiguracaoContabilBV;
import br.com.onesystem.war.service.ConfiguracaoContabilService;
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
public class ConfiguracaoContabilView extends BasicMBImpl<ConfiguracaoContabil, ConfiguracaoContabilBV> implements Serializable {

    private ConfiguracaoContabil configuracao;

    @Inject
    private ConfiguracaoContabilService service;

    @PostConstruct
    public void init() {
        configuracao = service.buscar();
        if (configuracao == null) {
            e = new ConfiguracaoContabilBV();
        } else {
            e = new ConfiguracaoContabilBV(configuracao);
        }
    }

    public void update() {
        try {
            if (e.getId() == null) {
                ConfiguracaoContabil c = e.construir();
                addNoBanco(c);
                e.setId(c.getId());
            } else {
                updateNoBanco(e.construirComID());
            }
            InfoMessage.print(new BundleUtil().getLabel("Configuracoes_Gravadas"));
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        String id = event.getComponent().getId();
        Object obj = event.getObject();
        if (obj instanceof TipoReceita && id.equals("receitaJuros-search")) {
            e.setReceitaDeJuros((TipoReceita) obj);
        } else if (obj instanceof TipoReceita && id.equals("receitaMultas-search")) {
            e.setReceitaDeMultas((TipoReceita) obj);
        } else if (obj instanceof TipoReceita && id.equals("receitaDescontosObtidos-search")) {
            e.setReceitaDeDescontosObtidos((TipoReceita) obj);
        } else if (obj instanceof TipoReceita && id.equals("receitaVariacaoCambial-search")) {
            e.setReceitaDeVariacaoCambial((TipoReceita) obj);
        } else if (obj instanceof TipoDespesa && id.equals("despesaJuros-search")) {
            e.setDespesaDeJuros((TipoDespesa) obj);
        } else if (obj instanceof TipoDespesa && id.equals("despesaMultas-search")) {
            e.setDespesaDeMultas((TipoDespesa) obj);
        } else if (obj instanceof TipoDespesa && id.equals("despesaDescontosObtidos-search")) {
            e.setDespesaDeDescontosConcedidos((TipoDespesa) obj);
        } else if (obj instanceof TipoDespesa && id.equals("despesaVariacaoCambial-search")) {
            e.setDespesaDeVariacaoCambial((TipoDespesa) obj);
        }
    }

    public ConfiguracaoContabil getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(ConfiguracaoContabil configuracao) {
        this.configuracao = configuracao;
    }

    public ConfiguracaoContabilService getService() {
        return service;
    }

    public void setService(ConfiguracaoContabilService service) {
        this.service = service;
    }

    @Override
    public void limparJanela() {
    }
}
