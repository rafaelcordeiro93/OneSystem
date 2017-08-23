package br.com.onesystem.war.view;

import br.com.onesystem.dao.GenericDAO;
import br.com.onesystem.dao.OrcamentoDAO;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.war.service.impl.BasicMBReportImpl;
import java.io.Serializable;
import javax.inject.Named;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.valueobjects.EstadoDeOrcamento;
import br.com.onesystem.valueobjects.TipoRelatorio;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.PostConstruct;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class RelatorioDeOrcamentoView extends BasicMBReportImpl<Orcamento> implements Serializable {

    @PostConstruct
    @Override
    protected void init() {
        addExtraClass(Pessoa.class, "pessoa");
        addExtraClass(ListaDePreco.class, "listaDePreco");
        addExtraClass(FormaDeRecebimento.class, "formaDeRecebimento");
        
        addCampoPadrao(new Coluna(bundle.getLabel("Id"), bundle.getLabel("Orcamento"), "id", Orcamento.class, Long.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Pessoa") + ")", bundle.getLabel("Pessoa"), "pessoa", "nome", Pessoa.class, String.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Emissao"), bundle.getLabel("Orcamento"), "emissao", Orcamento.class, Date.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Total"), bundle.getLabel("Orcamento"), "total", Orcamento.class, BigDecimal.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Estado"), bundle.getLabel("Orcamento"), "estado", Orcamento.class, EstadoDeOrcamento.class));

        initialize(Orcamento.class, OrcamentoDAO.class, TipoRelatorio.ORCAMENTO);

    }

}
