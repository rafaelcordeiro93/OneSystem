package br.com.onesystem.war.service;

import br.com.onesystem.dao.LayoutDeImpressaoDAO;
import br.com.onesystem.domain.LayoutDeImpressao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.TipoLayout;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class LayoutDeImpressaoService implements Serializable {

    @Inject
    private LayoutDeImpressaoDAO dao;
    
    public List<LayoutDeImpressao> buscaLayouts() {
        return dao.listaDeResultados();
    }

    public LayoutDeImpressao getLayoutPorTipoDeLayout(TipoLayout tipoLayout) throws DadoInvalidoException {
        LayoutDeImpressao layout = dao.porTipoLayout(tipoLayout).resultado();
        if (layout == null) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("Layout_nao_definido_no_gerenciador_de_layouts"));
        }
        return layout;
    }

}
