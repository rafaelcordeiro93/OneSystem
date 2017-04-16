package br.com.onesystem.war.view;

import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.war.builder.CotacaoBV;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class CotacaoView extends BasicMBImpl<Cotacao, CotacaoBV> implements Serializable {

    private Configuracao configuracao;

    @Inject
    private ConfiguracaoService serviceConfigurcao;

    @PostConstruct
    public void init() {
        limparJanela();
        inicializarConfiguracoes();
    }

    private void inicializarConfiguracoes() {
        try {
            configuracao = serviceConfigurcao.buscar();
            if (configuracao.getMoedaPadrao() == null) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Configuracao_nao_definida"));
            }
        } catch (EDadoInvalidoException ex) {
            ex.print();
        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Cotacao) {
            e = new CotacaoBV((Cotacao) event.getObject());
        } else if (obj instanceof Conta) {
            e.setConta((Conta) obj);
        }
    }

    public void limparJanela() {
        e = new CotacaoBV();
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public ConfiguracaoService getServiceConfigurcao() {
        return serviceConfigurcao;
    }

    public void setServiceConfigurcao(ConfiguracaoService serviceConfigurcao) {
        this.serviceConfigurcao = serviceConfigurcao;
    }

}
