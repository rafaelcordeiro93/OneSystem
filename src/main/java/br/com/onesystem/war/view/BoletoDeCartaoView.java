package br.com.onesystem.war.view;

import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.SituacaoDeCartao;
import br.com.onesystem.war.builder.BoletoDeCartaoBV;
import br.com.onesystem.war.service.ConfiguracaoService;
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
public class BoletoDeCartaoView extends BasicMBImpl<BoletoDeCartao, BoletoDeCartaoBV> implements Serializable {

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

    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof NotaEmitida) {
            e.setNota((Nota) event.getObject());
        } else if (obj instanceof Cartao) {
            e.setCartao((Cartao) event.getObject());
        } else if (obj instanceof BoletoDeCartao) {
            e = new BoletoDeCartaoBV((BoletoDeCartao) event.getObject());
        }
    }

    public List<SituacaoDeCartao> getTipoSituacao() {
        return Arrays.asList(SituacaoDeCartao.values());
    }

    public void limparJanela() {
        e = new BoletoDeCartaoBV();
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
