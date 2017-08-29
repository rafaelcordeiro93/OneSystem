package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.domain.ItemDePedido;
import br.com.onesystem.domain.ItemDePedidoCancelado;
import br.com.onesystem.domain.Motivo;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.war.builder.ItemBV;
import br.com.onesystem.war.builder.ItemDePedidoCanceladoBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class DialogoCancelaItemDePedidoView extends BasicMBImpl<ItemDePedidoCancelado, ItemDePedidoCanceladoBV> implements Serializable {

    private Model<ItemDePedidoCancelado> model;
    private ItemDePedido itemDePedido;
    private ItemBV item;

    @Inject

    @PostConstruct
    public void init() {
        try {
            limparJanela();
            buscaDaSessao();
        } catch (EDadoInvalidoException die) {
            die.print();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private void buscaDaSessao() throws DadoInvalidoException {
        model = (Model<ItemDePedidoCancelado>) SessionUtil.getObject("model", FacesContext.getCurrentInstance());
        itemDePedido = (ItemDePedido) SessionUtil.getObject("itemDePedido", FacesContext.getCurrentInstance());

        if (model != null) {
            e = new ItemDePedidoCanceladoBV((ItemDePedidoCancelado) model.getObject());
            item = new ItemBV(e.getItemDePedido().getItem());
            return;
        } else if (itemDePedido != null) {
            item = new ItemBV(itemDePedido.getItem());
            e.setItemDePedido(itemDePedido);
        }
    }

    public void abrirDialogo() {
        exibeNaTela();
    }

    private void exibeNaTela() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("resizable", false);
        opcoes.put("width", 400);
        opcoes.put("draggable", true);
        opcoes.put("height", 300);
        opcoes.put("closable", true);
        opcoes.put("contentWidth", "100%");
        opcoes.put("contentHeight", "100%");
        opcoes.put("headerElement", "customheader");

        RequestContext.getCurrentInstance().openDialog("/dialogo/dialogoCancelaItemDePedido", opcoes, null);
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Motivo) {
            e.setMotivo((Motivo) obj);
        }
    }

    public void salvar() {
        try {
            removeDaSessao();
            if (model != null) {
                ItemDePedidoCancelado c = e.construirComID();
                model.setObject(c);
                RequestContext.getCurrentInstance().closeDialog(model);
            } else {
                ItemDePedidoCancelado c = e.construir();
                RequestContext.getCurrentInstance().closeDialog(c);
            }
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void removeDaSessao() throws FDadoInvalidoException {
        SessionUtil.remove("model", FacesContext.getCurrentInstance());
        SessionUtil.remove("itemDePedido", FacesContext.getCurrentInstance());
    }

    @Override
    public void limparJanela() {
        e = new ItemDePedidoCanceladoBV();
    }

    public ItemBV getItem() {
        return item;
    }

    public void setItem(ItemBV item) {
        this.item = item;
    }

}
