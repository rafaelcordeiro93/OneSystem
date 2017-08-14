package br.com.onesystem.war.view;

import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.EstadoDeCheque;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.war.builder.ChequeBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ChequeView extends BasicMBImpl<Cheque, ChequeBV> implements Serializable {

    private Configuracao configuracao;
    private List<Cotacao> listaCotacao;

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
        Object obj = (Object) event.getObject();
        if (obj instanceof Cheque) {
            e = new ChequeBV((Cheque) obj);
        } else if (obj instanceof Banco) {
            e.setBanco((Banco) obj);
        } else if (obj instanceof NotaEmitida) {
            e.setVenda((NotaEmitida) obj);
        } else if (obj instanceof Pessoa) {
            e.setPessoa((Pessoa) obj);
        }

    }

    public List<EstadoDeCheque> getTipoSituacao() {
        return Arrays.asList(EstadoDeCheque.values());
    }

    public List<OperacaoFinanceira> getOperacaoFinanceira() {
        return Arrays.asList(OperacaoFinanceira.values());
    }

    public List<TipoLancamento> getTipoLancemento() {
        return Arrays.asList(TipoLancamento.values());
    }

    public List<Cotacao> buscarListaDeCotacao() {
        listaCotacao = new CotacaoDAO().naEmissao(new Date()).listaDeResultados();
        return listaCotacao;
    }

    @Override
    public void limparJanela() {
        e = new ChequeBV();
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

    public List<Cotacao> getListaCotacao() {
        return listaCotacao;
    }

    public void setListaCotacao(List<Cotacao> listaCotacao) {
        this.listaCotacao = listaCotacao;
    }

}
