package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Item;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.AjusteDeEstoqueBV;
import br.com.onesystem.war.service.AjusteDeEstoqueService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.io.Serializable;
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

    private boolean panel;
    private AjusteDeEstoqueBV ajusteDeEstoque;
    private AjusteDeEstoque ajusteDeEstoqueSelecionada;
    private List<AjusteDeEstoque> ajusteDeEstoqueLista;
    private List<AjusteDeEstoque> ajusteDeEstoquesFiltradas;

    @ManagedProperty("#{ajusteDeEstoqueService}")
    private AjusteDeEstoqueService service;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        ajusteDeEstoqueLista = service.buscarAjusteDeEstoques();
    }

    public void add() {
        try {
            AjusteDeEstoque novoRegistro = ajusteDeEstoque.construir();

            new AdicionaDAO<AjusteDeEstoque>().adiciona(novoRegistro);
            ajusteDeEstoqueLista.add(novoRegistro);
            InfoMessage.print(new BundleUtil().getMessage("ajuste_estoque_sucesso") + ajusteDeEstoque.getItem().getNome());
            limparJanela();

        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            AjusteDeEstoque ajusteDeEstoqueExistente = ajusteDeEstoque.construirComID();
            if (ajusteDeEstoqueExistente.getId() != null) {
                new AtualizaDAO<AjusteDeEstoque>(AjusteDeEstoque.class).atualiza(ajusteDeEstoqueExistente);
                ajusteDeEstoqueLista.set(ajusteDeEstoqueLista.indexOf(ajusteDeEstoqueExistente),
                        ajusteDeEstoqueExistente);
                if (ajusteDeEstoquesFiltradas != null && ajusteDeEstoquesFiltradas.contains(ajusteDeEstoqueExistente)) {
                    ajusteDeEstoquesFiltradas.set(ajusteDeEstoquesFiltradas.indexOf(ajusteDeEstoqueExistente), ajusteDeEstoqueExistente);
                }

                InfoMessage.print(new BundleUtil().getMessage("ajuste_estoque_atualizado"));
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
            if (ajusteDeEstoqueLista != null && ajusteDeEstoqueLista.contains(ajusteDeEstoqueSelecionada)) {
                new RemoveDAO<AjusteDeEstoque>(AjusteDeEstoque.class).remove(ajusteDeEstoqueSelecionada, ajusteDeEstoqueSelecionada.getId());
                ajusteDeEstoqueLista.remove(ajusteDeEstoqueSelecionada);
                if (ajusteDeEstoquesFiltradas != null && ajusteDeEstoquesFiltradas.contains(ajusteDeEstoqueSelecionada)) {
                    ajusteDeEstoquesFiltradas.remove(ajusteDeEstoqueSelecionada);
                }
                InfoMessage.print(new BundleUtil().getMessage("ajuste_estoque_removido"));
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
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

    public void limparJanela() {
        ajusteDeEstoque = new AjusteDeEstoqueBV();
        ajusteDeEstoqueSelecionada = new AjusteDeEstoque();
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        ajusteDeEstoque = new AjusteDeEstoqueBV(ajusteDeEstoqueSelecionada);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void desfazer() {
        if (ajusteDeEstoqueSelecionada != null) {
            ajusteDeEstoque = new AjusteDeEstoqueBV(ajusteDeEstoqueSelecionada);
        }
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
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

    public List<AjusteDeEstoque> getAjusteDeEstoqueLista() {
        return ajusteDeEstoqueLista;
    }

    public void setAjusteDeEstoqueLista(List<AjusteDeEstoque> ajusteDeEstoqueLista) {
        this.ajusteDeEstoqueLista = ajusteDeEstoqueLista;
    }

    public List<AjusteDeEstoque> getAjusteDeEstoquesFiltradas() {
        return ajusteDeEstoquesFiltradas;
    }

    public void setAjusteDeEstoquesFiltradas(List<AjusteDeEstoque> ajusteDeEstoquesFiltradas) {
        this.ajusteDeEstoquesFiltradas = ajusteDeEstoquesFiltradas;
    }

    public AjusteDeEstoqueService getService() {
        return service;
    }

    public void setService(AjusteDeEstoqueService service) {
        this.service = service;
    }

}
