package br.com.onesystem.war.view;

import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.domain.builder.EstoqueBuilder;
import br.com.onesystem.war.builder.AjusteDeEstoqueBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.OperacaoDeEstoqueService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class AjusteDeEstoqueView extends BasicMBImpl<AjusteDeEstoque, AjusteDeEstoqueBV> implements Serializable {

    private Configuracao configuracao;

    @Inject
    private ConfiguracaoService serviceConfigurcao;

    @Inject
    private OperacaoDeEstoqueService operacaoDeEstoqueService;

    @PostConstruct
    public void init() {
        inicializarConfiguracoes();
        limparJanela();
    }

    private void inicializarConfiguracoes() {
        try {
            configuracao = serviceConfigurcao.buscar();
        } catch (EDadoInvalidoException ex) {
            ex.print();
        }
    }

    public void add() {
        try {
            AjusteDeEstoque t = e.construir(); 
            addNoBanco(t);
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            if (e != null && e.getId() != null) {
                AjusteDeEstoque t = e.construirComID();
                updateNoBanco(t);
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (e != null && e.getId() != null) {
                deleteNoBanco(e.construirComID(), e.getId());
            }
        } catch (DadoInvalidoException di) {
            di.print();
        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof AjusteDeEstoque) {
            e = new AjusteDeEstoqueBV((AjusteDeEstoque) obj);
        } else if (obj instanceof Operacao) {
            e.setOperacao((Operacao) obj);
        } else if (obj instanceof Deposito) {
            e.setDeposito((Deposito) event.getObject());
        } else if (obj instanceof Item) {
            e.setItem((Item) event.getObject());
        }
    }

    public void limparJanela() {
        e = new AjusteDeEstoqueBV();
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
