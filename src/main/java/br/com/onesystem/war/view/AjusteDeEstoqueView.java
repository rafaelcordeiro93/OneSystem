package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Grupo;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.builder.EstoqueBuilder;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.AjusteDeEstoqueBV;
import br.com.onesystem.war.service.AjusteDeEstoqueService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.OperacaoFisica;
import br.com.onesystem.valueobjects.TipoItem;
import br.com.onesystem.war.builder.GrupoBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.ContaService;
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
public class AjusteDeEstoqueView implements Serializable {

    private AjusteDeEstoqueBV ajusteDeEstoque;
    private AjusteDeEstoque ajusteDeEstoqueSelecionada;
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
            
            AjusteDeEstoque novoRegistro = ajusteDeEstoque.construir();
            lancaEstoque(novoRegistro);
            new AdicionaDAO<AjusteDeEstoque>().adiciona(novoRegistro);
            InfoMessage.adicionado();
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {

            if (ajusteDeEstoqueSelecionada != null) {
                AjusteDeEstoque ajusteDeEstoqueExistente = ajusteDeEstoque.construirComID();
                lancaEstoque(ajusteDeEstoqueExistente);
                new AtualizaDAO<AjusteDeEstoque>(AjusteDeEstoque.class).atualiza(ajusteDeEstoqueExistente);
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
            if (ajusteDeEstoqueSelecionada != null) {
                new RemoveDAO<AjusteDeEstoque>(AjusteDeEstoque.class).remove(ajusteDeEstoqueSelecionada, ajusteDeEstoqueSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    private void lancaEstoque(AjusteDeEstoque ajusteDeEstoque) throws DadoInvalidoException {
        if (ajusteDeEstoque.getOperacaoFisica() != OperacaoFisica.SEM_ALTERACAO) {
            Estoque estoque = new EstoqueBuilder().comID(ajusteDeEstoque.getEstoque()
                    != null ? ajusteDeEstoque.getEstoque().getId() : null).
                    comDeposito(ajusteDeEstoque.getDeposito()).
                    comItem(ajusteDeEstoque.getItem()).
                    comEmissao(ajusteDeEstoque.getEmissao()).
                    comSaldo(ajusteDeEstoque.getQuantidade()).
                    comEmissao(ajusteDeEstoque.getEmissao()).
                    comOperacaoFisica(ajusteDeEstoque.getOperacaoFisica()).
                    comAjusteDeEstoque(ajusteDeEstoque).construir();
            ajusteDeEstoque.preparaInclusaoDe(estoque);
        }
    }

    public void selecionaItem(SelectEvent event) {
        Item itemSelecionado = (Item) event.getObject();
        ajusteDeEstoque.setItem(itemSelecionado);
    }

    public void selecionaDeposito(SelectEvent event) {
        Deposito depositoSelecionado = (Deposito) event.getObject();
        ajusteDeEstoque.setDeposito(depositoSelecionado);
    }

    public void selecionaAjusteDeEstoque(SelectEvent e) {
        AjusteDeEstoque a = (AjusteDeEstoque) e.getObject();
        ajusteDeEstoque = new AjusteDeEstoqueBV(a);
        ajusteDeEstoqueSelecionada = a;
    }

    public List<OperacaoFisica> getOperacao() {
        return Arrays.asList(OperacaoFisica.values());
    }

    public void limparJanela() {
        ajusteDeEstoque = new AjusteDeEstoqueBV();
        ajusteDeEstoqueSelecionada = null;
    }

    public void desfazer() {
        if (ajusteDeEstoqueSelecionada != null) {
            ajusteDeEstoque = new AjusteDeEstoqueBV(ajusteDeEstoqueSelecionada);
        }
    }

    public AjusteDeEstoqueBV getAjusteDeEstoque() {
        return ajusteDeEstoque;
    }

    public void setAjusteDeEstoque(AjusteDeEstoqueBV ajusteDeEstoque) {
        this.ajusteDeEstoque = ajusteDeEstoque;
    }

    public AjusteDeEstoque getAjusteDeEstoqueSelecionada() {
        return ajusteDeEstoqueSelecionada;
    }

    public void setAjusteDeEstoqueSelecionada(AjusteDeEstoque ajusteDeEstoqueSelecionada) {
        this.ajusteDeEstoqueSelecionada = ajusteDeEstoqueSelecionada;
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
