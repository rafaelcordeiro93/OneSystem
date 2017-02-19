package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.dao.UnidadeMedidaItemDAO;
import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.builder.UnidadeMedidaItemBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class UnidadeMedidaItemView extends BasicMBImpl<UnidadeMedidaItem> implements Serializable {

    private UnidadeMedidaItemBV unidadeMedidaItem;
    private UnidadeMedidaItem unidadeMedidaItemSelecionada;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            UnidadeMedidaItem novoRegistro = unidadeMedidaItem.construir();
            if (validaUnidadeMedidaExistente(novoRegistro)) {
                new AdicionaDAO<UnidadeMedidaItem>().adiciona(novoRegistro);
                InfoMessage.adicionado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("unidademedidaitem_ja_cadastrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            UnidadeMedidaItem unidadeMedidaItemExistente = unidadeMedidaItem.construirComID();
            if (unidadeMedidaItemExistente.getId() != null) {
                if (validaUnidadeMedidaExistente(unidadeMedidaItemExistente)) {
                    new AtualizaDAO<UnidadeMedidaItem>(UnidadeMedidaItem.class).atualiza(unidadeMedidaItemExistente);
                    InfoMessage.atualizado();
                    limparJanela();
                } else {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("unidademedidaitem_ja_cadastrado"));
                }
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("unidademedidaitem_nao_cadastrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (unidadeMedidaItemSelecionada != null) {
                new RemoveDAO<UnidadeMedidaItem>(UnidadeMedidaItem.class).remove(unidadeMedidaItemSelecionada, unidadeMedidaItemSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    private boolean validaUnidadeMedidaExistente(UnidadeMedidaItem novoRegistro) {
        List<UnidadeMedidaItem> lista = new UnidadeMedidaItemDAO().buscarUnidadeMedidaItem().porNome(novoRegistro).listaDeResultados();
        return lista.isEmpty();
    }

    @Override
    public void selecionar(SelectEvent e) {
        UnidadeMedidaItem u = (UnidadeMedidaItem) e.getObject();
        unidadeMedidaItem = new UnidadeMedidaItemBV(u);
        unidadeMedidaItemSelecionada = u;
    }

    @Override
    public void buscaPorId() {
        Long id = unidadeMedidaItem.getId();
        if (id != null) {
            try {
                UnidadeMedidaItemDAO dao = new UnidadeMedidaItemDAO();
                UnidadeMedidaItem c = dao.buscarUnidadeMedidaItem().porId(id).resultado();
                unidadeMedidaItemSelecionada = c;
                unidadeMedidaItem = new UnidadeMedidaItemBV(unidadeMedidaItemSelecionada);
            } catch (DadoInvalidoException die) {
                limparJanela();
                unidadeMedidaItem.setId(id);
                die.print();
            }
        }
    }

    public void desfazer() {
        if (unidadeMedidaItemSelecionada != null) {
            unidadeMedidaItem = new UnidadeMedidaItemBV(unidadeMedidaItemSelecionada);
        }
    }

    public void limparJanela() {
        unidadeMedidaItem = new UnidadeMedidaItemBV();
        unidadeMedidaItemSelecionada = new UnidadeMedidaItem();
    }

    public UnidadeMedidaItemBV getUnidadeMedidaItem() {
        return unidadeMedidaItem;
    }

    public void setUnidadeMedidaItem(UnidadeMedidaItemBV unidadeMedidaItem) {
        this.unidadeMedidaItem = unidadeMedidaItem;
    }

    public UnidadeMedidaItem getUnidadeMedidaItemSelecionada() {
        return unidadeMedidaItemSelecionada;
    }

    public void setUnidadeMedidaItemSelecionada(UnidadeMedidaItem unidadeMedidaItemSelecionada) {
        this.unidadeMedidaItemSelecionada = unidadeMedidaItemSelecionada;
    }

}
