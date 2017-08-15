package br.com.onesystem.war.view;

import br.com.onesystem.dao.ConhecimentoDeFreteDAO;
import br.com.onesystem.dao.GenericDAO;
import br.com.onesystem.dao.NotaDAO;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.war.service.impl.BasicMBReportImpl;
import java.io.Serializable;
import javax.inject.Named;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.valueobjects.TipoRelatorio;
import java.util.Date;
import javax.annotation.PostConstruct;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class RelatorioDeConhecimentoDeFreteView extends BasicMBReportImpl<ConhecimentoDeFrete> implements Serializable {

    @PostConstruct
    @Override
    protected void init() {
        addExtraClass(Pessoa.class, "pessoa");
        addExtraClass(Operacao.class, "operacao");
        
        addCampoPadrao(new Coluna(bundle.getLabel("Id"), bundle.getLabel("ConhecimentoDeFrete"), "id", ConhecimentoDeFrete.class, Long.class, null));
        addCampoPadrao(new Coluna(bundle.getLabel("Operacao") + "(" + bundle.getLabel("Operacao") + ")", bundle.getLabel("Operacao"), "operacao", "nome", Operacao.class, String.class, null));
        addCampoPadrao(new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Pessoa") + ")", bundle.getLabel("Pessoa"), "pessoa", "nome", Pessoa.class, String.class, null));
        addCampoPadrao(new Coluna(bundle.getLabel("Emissao"), bundle.getLabel("ConhecimentoDeFrete"), "emissao", ConhecimentoDeFrete.class, Date.class, null));
        addCampoPadrao(new Coluna(bundle.getLabel("Data"), bundle.getLabel("ConhecimentoDeFrete"), "data", ConhecimentoDeFrete.class, Date.class, null));

        initialize(ConhecimentoDeFrete.class, ConhecimentoDeFreteDAO.class, TipoRelatorio.CONHECIMENTO_DE_FRETE);

    }

}
