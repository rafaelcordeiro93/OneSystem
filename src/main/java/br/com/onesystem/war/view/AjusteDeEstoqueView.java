package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AjusteDeEstoqueDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.domain.builder.EstoqueBuilder;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.AjusteDeEstoqueBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class AjusteDeEstoqueView extends BasicMBImpl<AjusteDeEstoque> implements Serializable {

    private AjusteDeEstoqueBV ajusteDeEstoque;
    private AjusteDeEstoque ajusteDeEstoqueSelecionada;
    private Configuracao configuracao;

    @ManagedProperty("#{configuracaoService}")
    private ConfiguracaoService serviceConfigurcao;

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
        for (OperacaoDeEstoque operacaoDeEstoque : ajusteDeEstoque.getOperacao().getOperacaoDeEstoque()) {
            Estoque estoque = new EstoqueBuilder().comID(ajusteDeEstoque.getEstoque()
                    != null ? ajusteDeEstoque.getEstoque().getId() : null).
                    comDeposito(ajusteDeEstoque.getDeposito()).
                    comItem(ajusteDeEstoque.getItem()).
                    comEmissao(ajusteDeEstoque.getEmissao()).
                    comSaldo(ajusteDeEstoque.getQuantidade()).
                    comEmissao(ajusteDeEstoque.getEmissao()).
                    comOperacaoDeEstoque(operacaoDeEstoque).
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

    public void buscaPorId() {
        Long id = ajusteDeEstoque.getId();
        if (id != null) {
            try {
                AjusteDeEstoqueDAO dao = new AjusteDeEstoqueDAO();
                AjusteDeEstoque c = dao.buscarAjustesDeEstoque().porId(id).resultado();
                ajusteDeEstoqueSelecionada = c;
                ajusteDeEstoque = new AjusteDeEstoqueBV(ajusteDeEstoqueSelecionada);
            } catch (DadoInvalidoException die) {
                limparJanela();
                ajusteDeEstoque.setId(id);
                die.print();
            }
        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Operacao) {
            if (((Operacao) obj).getOperacaoDeEstoque().isEmpty()) {
                RequestContext rc = RequestContext.getCurrentInstance();
                rc.execute("PF('operacaoAjusteDialog').show()");
            } else {
                ajusteDeEstoque.setOperacao((Operacao) obj);
            }
        }
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
