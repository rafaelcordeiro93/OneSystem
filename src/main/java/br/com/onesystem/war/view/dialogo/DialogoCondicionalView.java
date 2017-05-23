package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.domain.Condicional;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.MoedaFomatter;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.TipoOperacao;
import br.com.onesystem.war.builder.ItemDeCondicionalBV;
import br.com.onesystem.war.service.ConfiguracaoEstoqueService;
import br.com.onesystem.war.service.ItemDeNotaService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped
public class DialogoCondicionalView extends BasicMBImpl<Condicional, ItemDeCondicionalBV> implements Serializable {

    private Condicional condicional;
    private List<ItemDeCondicionalBV> ItensDeCondicional;
    private ItemDeCondicionalBV itemDeCondicionalBV;

    @Inject
    private ItemDeNotaService serviceItemDeNota;

    @Inject
    private ConfiguracaoEstoqueService serviceConfiguracaoEstoque;

    @PostConstruct
    public void init() {
        try {
            limparJanela();
            buscaDaSessao();
            populaCampos();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void buscaDaSessao() throws FDadoInvalidoException {
        condicional = (Condicional) SessionUtil.getObject("condicional", FacesContext.getCurrentInstance());
    }

    private void populaCampos() {
        if (condicional != null) {
            condicional.getItensDeCondicional().forEach((io) -> {
                try {
                    ItemDeCondicionalBV iobv = new ItemDeCondicionalBV(io);
                    iobv.setSaldo(iobv.getQuantidade().subtract(serviceItemDeNota.buscaQuantidadeFaturadaPor(io, condicional)));
                    ItensDeCondicional.add(iobv);
                } catch (DadoInvalidoException ex) {
                    ex.print();
                    ex.printConsole();
                    throw new RuntimeException(ex);
                }
            });
        }
    }

    public void abrirDialogo() {
        exibeNaTela();
    }

    private void exibeNaTela() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("resizable", false);
        opcoes.put("width", "90%");
        opcoes.put("draggable", false);
        opcoes.put("height", 600);
        opcoes.put("closable", true);
        opcoes.put("contentWidth", "100%");
        opcoes.put("contentHeight", "100%");
        opcoes.put("headerElement", "customheader");

        RequestContext.getCurrentInstance().openDialog("dialogo/dialogoCondicional", opcoes, null);
    }

    private void abrirJanelaQuantidade() {
        RequestContext.getCurrentInstance().execute("document.getElementById(\"tempDialog:tabs:ItensDeCondicionalCondicional:0:exibeQuantidade-btn\").click();");
    }

    public void salvar() {
        try {
            List<ItemDeCondicionalBV> lista = preparaItens();
            removeDaSessao();
            RequestContext.getCurrentInstance().closeDialog(lista);
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private List<ItemDeCondicionalBV> preparaItens() throws EDadoInvalidoException {
        for (ItemDeCondicionalBV ib : ItensDeCondicional) {
            if (ib.getSaldo().compareTo(ib.getAFaturar()) == -1) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Existem_quantidade_a_faturar_maior_que_saldo"));
            }
        }
        return ItensDeCondicional;
    }

    private void removeDaSessao() throws FDadoInvalidoException {
        SessionUtil.remove("nota", FacesContext.getCurrentInstance());
        SessionUtil.remove("tipoOperacao", FacesContext.getCurrentInstance());
    }

    public String getZero() {
        if (condicional != null) {
            return MoedaFomatter.format(condicional.getCotacao().getConta().getMoeda(), BigDecimal.ZERO);
        } else {
            return "";
        }
    }

    @Override
    public void limparJanela() {
        itemDeCondicionalBV = new ItemDeCondicionalBV();
        ItensDeCondicional = new ArrayList<>();
    }

    public Condicional getCondicional() {
        return condicional;
    }

    public void setCondicional(Condicional condicional) {
        this.condicional = condicional;
    }

    public List<ItemDeCondicionalBV> getItensDeCondicional() {
        return ItensDeCondicional;
    }

    public void setItensDeCondicional(List<ItemDeCondicionalBV> ItensDeCondicional) {
        this.ItensDeCondicional = ItensDeCondicional;
    }

    public ItemDeCondicionalBV getItemDeCondicionalBV() {
        return itemDeCondicionalBV;
    }

    public void setItemDeCondicionalBV(ItemDeCondicionalBV itemDeCondicionalBV) {
        this.itemDeCondicionalBV = itemDeCondicionalBV;
    }

    public ConfiguracaoEstoqueService getServiceConfiguracaoEstoque() {
        return serviceConfiguracaoEstoque;
    }

    public void setServiceConfiguracaoEstoque(ConfiguracaoEstoqueService serviceConfiguracaoEstoque) {
        this.serviceConfiguracaoEstoque = serviceConfiguracaoEstoque;
    }

    @Override
    public void selecionar(SelectEvent event) {
    }

}
