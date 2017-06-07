package br.com.onesystem.war.view;

import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.war.builder.OperacaoBV;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.TipoContabil;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoOperacao;
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
public class OperacaoView extends BasicMBImpl<Operacao, OperacaoBV> implements Serializable {

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

    public void limparJanela() {
        e = new OperacaoBV();
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        String idComponent = event.getComponent().getId();
        if (obj instanceof Operacao) {
            e = new OperacaoBV((Operacao) obj);
        } else if (obj instanceof TipoReceita && "receitaAVista-search".equals(idComponent)) {
            e.setVendaAVista((TipoReceita) obj);
        } else if (obj instanceof TipoReceita && "receitaAPrazo-search".equals(idComponent)) {
            e.setVendaAPrazo((TipoReceita) obj);
        } else if (obj instanceof TipoReceita && "servicoAVista-search".equals(idComponent)) {
            e.setServicoAVista((TipoReceita) obj);
        } else if (obj instanceof TipoReceita && "servicoAPrazo-search".equals(idComponent)) {
            e.setServicoAPrazo((TipoReceita) obj);
        } else if (obj instanceof TipoReceita && "receitaFrete-search".equals(idComponent)) {
            e.setReceitaFrete((TipoReceita) obj);
        } else if (obj instanceof TipoDespesa && "despesaCMV-search".equals(idComponent)) {
            e.setDespesaCMV((TipoDespesa) obj);
        } else if (obj instanceof TipoDespesa && "despesaAVista-search".equals(idComponent)) {
            e.setCompraAVista((TipoDespesa) obj);
        } else if (obj instanceof TipoDespesa && "despesaAPrazo-search".equals(idComponent)) {
            e.setCompraAPrazo((TipoDespesa) obj);
        }
    }

    public List<OperacaoFinanceira> getOperacaoFinanceira() {
        return Arrays.asList(OperacaoFinanceira.values());
    }

    public List<TipoLancamento> getTipoNota() {
        return Arrays.asList(TipoLancamento.values());
    }

    public List<TipoOperacao> getTipoDeOperacao() {
        return Arrays.asList(TipoOperacao.values());
    }

    public List<TipoContabil> getTipoContabil() {
        return Arrays.asList(TipoContabil.values());
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
