package br.com.onesystem.dao;

import br.com.onesystem.domain.LayoutDeImpressao;
import br.com.onesystem.valueobjects.TipoLayout;

public class LayoutDeImpressaoDAO extends GenericDAO<LayoutDeImpressao> {

    public LayoutDeImpressaoDAO() {
        super(LayoutDeImpressao.class);
        limpar();
    }

    public LayoutDeImpressaoDAO porTipoLayout(TipoLayout tipoLayout) {
        where += " and layoutDeImpressao.tipoLayout = :pTipoLayout ";
        parametros.put("pTipoLayout", tipoLayout);
        return this;
    }

}
