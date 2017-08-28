/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.domain.Condicional;
import br.com.onesystem.domain.LayoutDeImpressao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ImpressoraDeLayout;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.valueobjects.TipoLayout;
import br.com.onesystem.war.builder.CondicionalBV;
import br.com.onesystem.war.service.LayoutDeImpressaoService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author rauber
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ConsultaCondicionalView extends BasicMBImpl<Condicional, CondicionalBV> implements Serializable {

    private LayoutDeImpressao layout;

    @Inject
    private LayoutDeImpressaoService layoutService;

    @PostConstruct
    public void construir() {
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Condicional) {
            t = (Condicional) obj;
        }
    }

    public void imprimir() {
        try {
            if (t != null) {
                layout = layoutService.getLayoutPorTipoDeLayout(TipoLayout.CONDICIONAL);
                new ImpressoraDeLayout(t.getItensDeCondicional(), layout).addParametro("condicional", t).visualizarPDF();
                t = null; // libera memoria do objeto impresso.
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getLabel("Selecione_um_registro"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public String getZero() {
        if (t != null) {
            return MoedaFormatter.format(t.getCotacao().getConta().getMoeda(), BigDecimal.ZERO);
        } else {
            return "";
        }
    }

    public void cancela() {
        try {
            t.cancela();
            new AtualizaDAO<>().atualiza(t);
            InfoMessage.atualizado();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    @Override
    public void limparJanela() {
        t = null;
    }

}
