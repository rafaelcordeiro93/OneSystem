package br.com.onesystem.war.view;

import br.com.onesystem.dao.CobrancaDAO;
import br.com.onesystem.dao.GenericDAO;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.war.service.impl.BasicMBReportImpl;
import java.io.Serializable;
import javax.inject.Named;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.valueobjects.TipoRelatorio;
import java.util.Date;
import javax.annotation.PostConstruct;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class RelatorioDeChequesView extends BasicMBReportImpl<Cobranca> implements Serializable {

    @PostConstruct
    @Override
    protected void init() {

        addExtraClass(Cotacao.class, "cotacao");
        addExtraClass(Moeda.class, "cotacao.conta.moeda");
        addExtraClass(Cheque.class, "cheque");
        addExtraClass(Pessoa.class, "pessoa");
        addCampoPadrao(new Coluna(bundle.getLabel("Id"), bundle.getLabel("Cobranca"), "id", Cobranca.class, Long.class, null));
        addCampoPadrao(new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Moeda") + ")", bundle.getLabel("Moeda"), "cotacao", "conta", "moeda", "nome", Cotacao.class, String.class, null));
        addCampoPadrao(new Coluna(bundle.getLabel("Valor") + "(" + bundle.getLabel("Cotacao") + ")", bundle.getLabel("Cotacao"), "cotacao", "valor", Cotacao.class, String.class, null));
        addCampoPadrao(new Coluna(bundle.getLabel("Valor"), bundle.getLabel("Cobranca"), "valor", Cobranca.class, Long.class, null));
        addCampoPadrao(new Coluna(bundle.getLabel("Emitente") + "(" + bundle.getLabel("Cheque") + ")", bundle.getLabel("Cheque"), "emitente", Cheque.class, String.class, null));
        addCampoPadrao(new Coluna(bundle.getLabel("Emissao"), bundle.getLabel("Cobranca"), "emissao", Cobranca.class, Date.class, null));

        initialize(Cobranca.class, CobrancaDAO.class, TipoRelatorio.CHEQUES);

    }

    @Override
    protected void alteraConsulta(GenericDAO gDao) {
        gDao = ((CobrancaDAO) gDao).consultaCheques();
    }

}
