package br.com.onesystem.war.view;

import br.com.onesystem.dao.GenericDAO;
import br.com.onesystem.dao.ComandaDAO;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.war.service.impl.BasicMBReportImpl;
import java.io.Serializable;
import javax.inject.Named;
import br.com.onesystem.domain.Comanda;
import br.com.onesystem.domain.Comanda;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.valueobjects.EstadoDeComanda;
import br.com.onesystem.valueobjects.TipoRelatorio;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.PostConstruct;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class RelatorioDeComandaView extends BasicMBReportImpl<Comanda> implements Serializable {

    @PostConstruct
    @Override
    protected void init() {
        addExtraClass(Pessoa.class, "pessoa");
        addExtraClass(ListaDePreco.class, "listaDePreco");
        
        addCampoPadrao(new Coluna(bundle.getLabel("Id"), bundle.getLabel("Comanda"), "id", Comanda.class, Long.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Pessoa") + ")", bundle.getLabel("Pessoa"), "pessoa", "nome", Pessoa.class, String.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Emissao"), bundle.getLabel("Comanda"), "emissao", Comanda.class, Date.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Total"), bundle.getLabel("Comanda"), "total", Comanda.class, BigDecimal.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Estado"), bundle.getLabel("Comanda"), "estado", Comanda.class, EstadoDeComanda.class));

        initialize(Comanda.class, ComandaDAO.class, TipoRelatorio.COMANDA);

    }

}
