package br.com.onesystem.war.view;

import br.com.onesystem.dao.ItemDAO;
import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.DetalhamentoDeItem;
import br.com.onesystem.war.builder.AjusteDeEstoqueBV;
import br.com.onesystem.war.builder.LoteItemBV;
import br.com.onesystem.war.service.OperacaoDeEstoqueService;
import br.com.onesystem.war.service.AjusteDeEstoqueService;
import br.com.onesystem.war.service.LoteItemService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class AjusteDeEstoqueView extends BasicMBImpl<AjusteDeEstoque, AjusteDeEstoqueBV> implements Serializable {

    private LoteItemBV loteItemBV;

    @Inject
    private Configuracao configuracao;

    @Inject
    private OperacaoDeEstoqueService operacaoDeEstoqueService;

    @Inject
    private AjusteDeEstoqueService ajusteDeEstoqueService;

    @Inject
    private LoteItemService loteItemService;

    @Inject
    private ItemDAO ItemDAO;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            t = e.construir();
            ajusteDeEstoqueService.atualizaEstoque(t);
            addNoBanco(t);
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            BigDecimal valor = loteItemService.calculaQuantidade(t.getQuantidade(), e.getQuantidade());
            t = e.construirComID();
            ajusteDeEstoqueService.atualizaEstoque(t);
            updateNoBancoSemLimpar(t);
            loteItemService.atualizaSaldoLote(e.getItem(), loteItemBV, valor, operacaoDeEstoqueService.buscarOperacaoFisicaPor(t.getOperacao()));
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void remove() {
        try {
            t = e.construirComID();
            AjusteDeEstoque es = t;
            deleteNoBanco(t, t.getId());
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof AjusteDeEstoque) {
            e = new AjusteDeEstoqueBV((AjusteDeEstoque) obj);
            setAjuste();
            setItem();
            setupItem();
        } else if (obj instanceof Operacao) {
            Operacao operacao = (Operacao) obj;
            List<OperacaoDeEstoque> operacoesDeEstoque = operacao.getOperacaoDeEstoque();
            if (operacoesDeEstoque == null || operacoesDeEstoque.isEmpty()) {
                RequestContext rc = RequestContext.getCurrentInstance();
                rc.execute("PF('operacaoAjusteDialog').show()");
            } else {
                e.setOperacao((Operacao) obj);
            }
        } else if (obj instanceof Deposito) {
            e.setDeposito((Deposito) obj);
        } else if (obj instanceof Item) {
            e.setItem((Item) obj);
            setItem();
            setupItem();
        }
    }

    /**
     * Este método está aqui devido a um Lazy Loading
     */
    public void setItem() {
        e.setItem(ItemDAO.porId(e.getItem().getId()).resultado());
    }

    public void setAjuste() {
        try {
            t = e.construirComID();
           // setupItem();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void setupItem() {
        if (e.getItem().getDetalhamento() == DetalhamentoDeItem.LOTES) {
            if (e.getItem().getLoteItem().size() == 0 || e.getItem().getLoteItem() == null) {//Tera que criar o lote
            } else if (e.getItem().getLoteItem().size() == 1) {//ja seta o lote pois so tem 1                
                //loteItemBV = new LoteItemBV(loteItemService.buscarLoteItemPorID(e.getItem().getLoteItem().get(0).getId()));
                loteItemBV = new LoteItemBV(e.getItem().getLoteItem().get(0));
            } else if (e.getItem().getLoteItem().size() > 1) {//Abre dialogo para escolher o lote desejado
            }
        }
    }

    public void limparJanela() {
        e = new AjusteDeEstoqueBV();
        t = null;
        loteItemBV = new LoteItemBV();
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }
}
