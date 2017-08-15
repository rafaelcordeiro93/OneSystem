package br.com.onesystem.war.view;

import br.com.onesystem.dao.GenericDAO;
import br.com.onesystem.dao.CondicionalDAO;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.war.service.impl.BasicMBReportImpl;
import java.io.Serializable;
import javax.inject.Named;
import br.com.onesystem.domain.Condicional;
import br.com.onesystem.domain.Condicional;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.valueobjects.EstadoDeCondicional;
import br.com.onesystem.valueobjects.TipoRelatorio;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.PostConstruct;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class RelatorioDeCondicionalView extends BasicMBReportImpl<Condicional> implements Serializable {

    @PostConstruct
    @Override
    protected void init() {
        addExtraClass(Pessoa.class, "pessoa");
        addExtraClass(ListaDePreco.class, "listaDePreco");
        
        addCampoPadrao(new Coluna(bundle.getLabel("Id"), bundle.getLabel("Condicional"), "id", Condicional.class, Long.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Pessoa") + ")", bundle.getLabel("Pessoa"), "pessoa", "nome", Pessoa.class, String.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Emissao"), bundle.getLabel("Condicional"), "emissao", Condicional.class, Date.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Total"), bundle.getLabel("Condicional"), "total", Condicional.class, BigDecimal.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Estado"), bundle.getLabel("Condicional"), "estado", Condicional.class, EstadoDeCondicional.class));

        initialize(Condicional.class, CondicionalDAO.class, TipoRelatorio.CONDICIONAL);

    }

}
