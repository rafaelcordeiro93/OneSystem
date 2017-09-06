package br.com.onesystem.war.service;

import br.com.onesystem.dao.LayoutDeImpressaoDAO;
import br.com.onesystem.domain.LayoutDeImpressao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.TipoLayout;
import java.io.Serializable;
import java.util.List;

public class LayoutDeImpressaoService implements Serializable {

    public List<LayoutDeImpressao> buscaLayouts() {
        return new LayoutDeImpressaoDAO().listaDeResultados();
    }

    public LayoutDeImpressao getLayoutPorTipoDeLayout(TipoLayout tipoLayout) throws DadoInvalidoException {
        LayoutDeImpressao layout = new LayoutDeImpressaoDAO().porTipoLayout(tipoLayout).resultado();
        if (layout == null) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("Layout_nao_definido_no_gerenciador_de_layouts"));
        }
        return layout;
    }

}
