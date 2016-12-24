package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Cidade;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.FilialBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.service.ConfiguracaoService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class FilialView implements Serializable {

    private FilialBV filial;
    private Filial filialSelecionada;
    private Configuracao configuracao;

    @ManagedProperty("#{configuracaoService}")
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

    public void add() {
        try {
            Filial novoRegistro = filial.construir();
            new AdicionaDAO<Filial>().adiciona(novoRegistro);
            InfoMessage.adicionado();
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {

            if (filialSelecionada != null) {
                Filial filialExistente = filial.construirComID();
                new AtualizaDAO<Filial>(Filial.class).atualiza(filialExistente);
                InfoMessage.atualizado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("ajuste_estoque_nao_encontrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (filialSelecionada != null) {
                new RemoveDAO<Filial>(Filial.class).remove(filialSelecionada, filialSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void selecionaCidade(SelectEvent event) {
        Cidade cidadeSelecionado = (Cidade) event.getObject();
        filial.setCidade(cidadeSelecionado);
    }

    public void selecionaFilial(SelectEvent e) {
        Filial a = (Filial) e.getObject();
        filial = new FilialBV(a);
        filialSelecionada = a;
    }

    public void limparJanela() {
        filial = new FilialBV();
        filialSelecionada = null;
    }

    public void desfazer() {
        if (filialSelecionada != null) {
            filial = new FilialBV(filialSelecionada);
        }
    }

    public FilialBV getFilial() {
        return filial;
    }

    public void setFilial(FilialBV filial) {
        this.filial = filial;
    }

    public Filial getFilialSelecionada() {
        return filialSelecionada;
    }

    public void setFilialSelecionada(Filial filialSelecionada) {
        this.filialSelecionada = filialSelecionada;
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
