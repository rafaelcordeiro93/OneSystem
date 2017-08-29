package br.com.onesystem.war.view;

import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.TipoFormaDeRecebimento;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.valueobjects.TipoPeriodicidade;
import br.com.onesystem.war.builder.FormaDeRecebimentoBV;
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
public class FormaDeRecebimentoView extends BasicMBImpl<FormaDeRecebimento, FormaDeRecebimentoBV> implements Serializable {

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
        if (obj instanceof FormaDeRecebimento) {
            e = new FormaDeRecebimentoBV((FormaDeRecebimento) event.getObject());
        }
        if (obj instanceof Cartao) {
            e.setCartao((Cartao) obj);
        }
    }

    public List<TipoFormaDeRecebimento> getFormaDeRecebimentoPadrao() {
        return Arrays.asList(TipoFormaDeRecebimento.DINHEIRO, TipoFormaDeRecebimento.CREDITO, TipoFormaDeRecebimento.A_FATURAR);
    }

    public List<ModalidadeDeCobranca> getTipoFormaDeRecebimentoPadraoParcela() {
        return Arrays.asList(ModalidadeDeCobranca.CARTAO,ModalidadeDeCobranca.CHEQUE,ModalidadeDeCobranca.CREDITO, ModalidadeDeCobranca.TITULO);
    }

    public List<TipoPeriodicidade> getTipoPeriodicidade() {
        return Arrays.asList(TipoPeriodicidade.values());
    }

    public void limparJanela() {
        e = new FormaDeRecebimentoBV();
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
