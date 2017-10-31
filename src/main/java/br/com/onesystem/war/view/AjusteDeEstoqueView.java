package br.com.onesystem.war.view;

import br.com.onesystem.dao.ItemDAO;
import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.LoteItem;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.GeradorDeEstoque;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.util.WarningMessage;
import br.com.onesystem.valueobjects.DetalhamentoDeItem;
import br.com.onesystem.war.builder.AjusteDeEstoqueBV;
import br.com.onesystem.war.builder.LoteItemBV;
import br.com.onesystem.war.service.LoteItemService;
import br.com.onesystem.war.service.OperacaoDeEstoqueService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
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
    private LoteItemService loteService;

    @Inject
    private OperacaoDeEstoqueService operacaoDeEstoqueService;

    @Inject
    private GeradorDeEstoque geradorDeEstoque;

    @Inject
    private ItemDAO ItemDAO;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            e.setEmissao(new Date());
            t = e.construir();
            geradorDeEstoque.geraEstoque(t);
            addNoBanco(t);
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            AjusteDeEstoque ae = t;//pega o ajuste de estoque na hora do selecionar, antes de ser feita as alteracoes
            t = e.construirComID();
            geradorDeEstoque.geraEstoque(t);
            updateNoBancoSemLimpar(t);
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            t = e.construirComID();
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
            e.setItem(ItemDAO.porId(e.getItem().getId()).resultado());
            setAjuste();
        } else if (obj instanceof Operacao) {
            Operacao operacao = (Operacao) obj;
            List<OperacaoDeEstoque> operacoesDeEstoque = operacaoDeEstoqueService.buscarOperacoesDeEstoquePor(operacao);
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
            e.setItem(ItemDAO.porId(e.getItem().getId()).resultado());
            setupItem();
        }
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
            List<LoteItem> lotes = getLoteDoItem(e.getItem());
            if (lotes.isEmpty()) {
                WarningMessage.print(new BundleUtil().getLabel("Nao_Foi_Encontrado_Lote_Ativo_Para_O_Item_favor_cadastre_ou_ative_um_lote_na_janela_de_item"));
                loteItemBV = null;
            } else if (lotes.size() == 1) {//ja seta o lote pois so tem 1                
                loteItemBV = new LoteItemBV(lotes.get(0));
            } else if (lotes.size() > 1) {//Abre dialogo para escolher o lote desejado
                if (e.getId() != null) {
                    loteItemBV = new LoteItemBV(e.getLoteItem());//se o ajuste ja existir ele pega o lote do ajuste
                } else {
                    loteItemBV = new LoteItemBV(lotes.get(0));//se for um ajuste novo ele seta o primeio lote da lista
                }
            }
        }
    }

    public void selecionaLoteItem() {
        loteItemBV = new LoteItemBV(e.getLoteItem());
    }

    public void limparJanela() {
        try {
            e = new AjusteDeEstoqueBV();
            e.setFilial((Filial) SessionUtil.getObject("filial", FacesContext.getCurrentInstance()));
            t = null;
            loteItemBV = new LoteItemBV();
        } catch (FDadoInvalidoException ex) {
            Logger.getLogger(AjusteDeEstoqueView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<LoteItem> getLoteDoItem(Item item) {
        List<LoteItem> lotes = new ArrayList<>();
        if (item != null) {
            lotes = loteService.buscarLotesAtivosPorItem(item);
            if (e.getLoteItem() != null) {
                lotes.add(e.getLoteItem());
            }
        }
        return lotes;
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public Date getMaxDate() {
        return new Date();
    }

}
