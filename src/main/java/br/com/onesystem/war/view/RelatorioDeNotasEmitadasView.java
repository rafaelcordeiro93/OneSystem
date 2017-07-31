package br.com.onesystem.war.view;

import br.com.onesystem.dao.NotaDAO;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.war.service.impl.BasicMBReportImpl;
import java.io.Serializable;
import javax.inject.Named;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Pessoa;
import java.util.Date;
import javax.annotation.PostConstruct;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class RelatorioDeNotasEmitadasView extends BasicMBReportImpl<Nota> implements Serializable {

    @PostConstruct
    @Override
    protected void init() {
        addExtraClass(Pessoa.class, "pessoa");
        addExtraClass(Operacao.class, "operacao");
        addExtraClass(NotaEmitida.class, "notaEmitida");
        addCampoPadrao(new Coluna(bundle.getLabel("Id"), bundle.getLabel("Nota"), "id", Nota.class, Long.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Operacao") + "(" + bundle.getLabel("Operacao") + ")", bundle.getLabel("Operacao"), "operacao", "nome", Operacao.class, String.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Pessoa") + ")", bundle.getLabel("Pessoa"), "pessoa", "nome", Pessoa.class, String.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Emissao"), bundle.getLabel("Nota"), "emissao", Nota.class, Date.class));

        initialize(Nota.class, NotaDAO.class);
    }

}
