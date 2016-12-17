package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.FormaDeRecebimentoBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.OperacaoFisica;
import br.com.onesystem.valueobjects.TipoFormaDeRecebimento;
import br.com.onesystem.valueobjects.TipoPeriodicidade;
import br.com.onesystem.war.service.ConfiguracaoService;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class FormaDeRecebimentoView implements Serializable {

    private FormaDeRecebimentoBV formaDeRecebimento;
    private FormaDeRecebimento formaDeRecebimentoSelecionada;
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
            FormaDeRecebimento novoRegistro = formaDeRecebimento.construir();
            new AdicionaDAO<FormaDeRecebimento>().adiciona(novoRegistro);
            InfoMessage.adicionado();
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            
            if (formaDeRecebimentoSelecionada != null) {
                FormaDeRecebimento formaDeRecebimentoExistente = formaDeRecebimento.construirComID();
                new AtualizaDAO<FormaDeRecebimento>(FormaDeRecebimento.class).atualiza(formaDeRecebimentoExistente);
                InfoMessage.atualizado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("forma_recebimento_nao_encontrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (formaDeRecebimentoSelecionada != null) {
                new RemoveDAO<FormaDeRecebimento>(FormaDeRecebimento.class).remove(formaDeRecebimentoSelecionada, formaDeRecebimentoSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void selecionaFormaDeRecebimento(SelectEvent e) {
        FormaDeRecebimento a = (FormaDeRecebimento) e.getObject();
        formaDeRecebimento = new FormaDeRecebimentoBV(a);
        formaDeRecebimentoSelecionada = a;
    }

    public List<TipoFormaDeRecebimento> getFormaDeRecebimentoPadrao() {
        return Arrays.asList(TipoFormaDeRecebimento.values());
    }

     public List<TipoPeriodicidade> getTipoPeriodicidade() {
        return Arrays.asList(TipoPeriodicidade.values());
    }
    
    public void limparJanela() {
        formaDeRecebimento = new FormaDeRecebimentoBV();
        formaDeRecebimentoSelecionada = null;
    }

    public void desfazer() {
        if (formaDeRecebimentoSelecionada != null) {
            formaDeRecebimento = new FormaDeRecebimentoBV(formaDeRecebimentoSelecionada);
        }
    }

    public FormaDeRecebimentoBV getFormaDeRecebimento() {
        return formaDeRecebimento;
    }

    public void setFormaDeRecebimento(FormaDeRecebimentoBV formaDeRecebimento) {
        this.formaDeRecebimento = formaDeRecebimento;
    }

    public FormaDeRecebimento getFormaDeRecebimentoSelecionada() {
        return formaDeRecebimentoSelecionada;
    }

    public void setFormaDeRecebimentoSelecionada(FormaDeRecebimento formaDeRecebimentoSelecionada) {
        this.formaDeRecebimentoSelecionada = formaDeRecebimentoSelecionada;
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
