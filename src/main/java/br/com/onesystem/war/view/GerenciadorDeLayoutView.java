package br.com.onesystem.war.view;

import br.com.onesystem.domain.LayoutDeImpressao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.valueobjects.TipoImpressao;
import br.com.onesystem.valueobjects.TipoLayout;
import br.com.onesystem.war.builder.LayoutDeImpressaoBV;
import br.com.onesystem.war.service.LayoutDeImpressaoService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class GerenciadorDeLayoutView extends BasicMBImpl<LayoutDeImpressao, LayoutDeImpressaoBV> implements Serializable {

    private List<LayoutDeImpressao> layouts;
    private TipoLayout layoutSelecionado;

    @Inject
    private LayoutDeImpressaoService service;

    @PostConstruct
    public void init() {
        limparJanela();
        layouts = service.buscaLayouts();
    }

    public void salvar() {
        try {
            if (e != null && e.getId() != null) {
                LayoutDeImpressao layout = e.construirComID();
                updateNoBanco(layout);
                layouts.set(layouts.indexOf(layout), layout);
            } else if (e != null && e.getId() == null && layoutSelecionado != null) {
                e.setTipoLayout(layoutSelecionado);
                LayoutDeImpressao layout = e.construir();
                addNoBanco(layout);
                layouts.add(layout);
            } else if (layoutSelecionado == null) {
                ErrorMessage.print(new BundleUtil().getMessage("Selecione_um_layout"));
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof LayoutDeImpressao) {
            e = new LayoutDeImpressaoBV((LayoutDeImpressao) obj);
        }
    }

    public void selecionaTipoLayout(SelectEvent event) {
        TipoLayout tipo = (TipoLayout) event.getObject();
        try {
            LayoutDeImpressao get = layouts.stream().filter(l -> l.getTipoLayout().equals(tipo)).findFirst().get();
            e = new LayoutDeImpressaoBV(get);
        } catch (NoSuchElementException nsee) {
            limparJanela();
        }
    }

    public void limparJanela() {
        e = new LayoutDeImpressaoBV();
        e.setTipoImpressao(TipoImpressao.VISUALIZAR);
    }

    public List<TipoLayout> getTipoLayouts() {
        return Arrays.asList(TipoLayout.values());
    }

    public List<TipoImpressao> getTiposImpressao() {
        if (e.isLayoutGraficoEhPadrao()) {
            return Arrays.asList(TipoImpressao.VISUALIZAR, TipoImpressao.NADA_A_FAZER);
        } else {
            return Arrays.asList(TipoImpressao.IMPRIMIR, TipoImpressao.NADA_A_FAZER);
        }
    }

    public List<LayoutDeImpressao> getLayouts() {
        return layouts;
    }

    public void setLayouts(List<LayoutDeImpressao> layouts) {
        this.layouts = layouts;
    }

    public TipoLayout getLayoutSelecionado() {
        return layoutSelecionado;
    }

    public void setLayoutSelecionado(TipoLayout layoutSelecionado) {
        this.layoutSelecionado = layoutSelecionado;
    }

    public LayoutDeImpressaoService getService() {
        return service;
    }

    public void setService(LayoutDeImpressaoService service) {
        this.service = service;
    }

}
