package br.com.onesystem.war.view;

import br.com.onesystem.dao.UnidadeMedidaItemDAO;
import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.builder.UnidadeMedidaItemBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class UnidadeMedidaItemView extends BasicMBImpl<UnidadeMedidaItem, UnidadeMedidaItemBV> implements Serializable {

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            UnidadeMedidaItem novoRegistro = e.construir();
            if (validaUnidadeMedidaExistente(novoRegistro)) {
                addNoBanco(novoRegistro);
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("unidademedidaitem_ja_cadastrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            UnidadeMedidaItem unidadeMedidaItemExistente = e.construirComID();
            if (unidadeMedidaItemExistente.getId() != null) {
                if (validaUnidadeMedidaExistente(unidadeMedidaItemExistente)) {
                    updateNoBanco(unidadeMedidaItemExistente);
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
   
    private boolean validaUnidadeMedidaExistente(UnidadeMedidaItem novoRegistro) {
        List<UnidadeMedidaItem> lista = new UnidadeMedidaItemDAO().buscarUnidadeMedidaItem().porNome(novoRegistro).listaDeResultados();
        return lista.isEmpty();
    }

    @Override
    public void selecionar(SelectEvent event) {
        e = new UnidadeMedidaItemBV((UnidadeMedidaItem) event.getObject());
    }
    
    public void limparJanela() {
        e = new UnidadeMedidaItemBV();
    }

}
