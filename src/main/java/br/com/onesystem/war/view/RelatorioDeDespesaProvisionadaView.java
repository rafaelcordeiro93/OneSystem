package br.com.onesystem.war.view;

import br.com.onesystem.dao.CobrancaFixaDAO;
import br.com.onesystem.dao.GenericDAO;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.war.service.impl.BasicMBReportImpl;
import java.io.Serializable;
import javax.inject.Named;
import br.com.onesystem.domain.CobrancaFixa;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.valueobjects.TipoRelatorio;
import java.util.Date;
import javax.annotation.PostConstruct;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class RelatorioDeDespesaProvisionadaView extends BasicMBReportImpl<CobrancaFixa> implements Serializable {

    @PostConstruct
    @Override
    protected void init() {

        addExtraClass(Cotacao.class, "cotacao");
        addExtraClass(Moeda.class, "cotacao.conta.moeda");
        addExtraClass(DespesaProvisionada.class, "despesaProvisionada");
        addExtraClass(Pessoa.class, "pessoa");
        addCampoPadrao(new Coluna(bundle.getLabel("Id"), bundle.getLabel("Cobranca_Fixa"), "id", CobrancaFixa.class, Long.class, null));
        addCampoPadrao(new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Moeda") + ")", bundle.getLabel("Moeda"), "cotacao", "conta", "moeda", "nome", Cotacao.class, String.class, null));
        addCampoPadrao(new Coluna(bundle.getLabel("Valor") + "(" + bundle.getLabel("Cotacao") + ")", bundle.getLabel("Cotacao"), "cotacao", "valor", Cotacao.class, String.class, null));
        addCampoPadrao(new Coluna(bundle.getLabel("Valor"), bundle.getLabel("Cobranca_Fixa"), "valor", CobrancaFixa.class, Long.class, null));
        addCampoPadrao(new Coluna(bundle.getLabel("Tipo_Despesa") + "(" + bundle.getLabel("Despesa_Provisionada") + ")", bundle.getLabel("Despesa_Provisionada"), "tipoDespesa", "nome", DespesaProvisionada.class, String.class, null));
        addCampoPadrao(new Coluna(bundle.getLabel("Emissao"), bundle.getLabel("Cobranca_Fixa"), "emissao", CobrancaFixa.class, Date.class, null));

        initialize(CobrancaFixa.class, CobrancaFixaDAO.class, TipoRelatorio.DESPESAS_PROVISIONADAS);

    }

    @Override
    protected void alteraConsulta(GenericDAO gDao) {
        gDao = ((CobrancaFixaDAO) gDao).consultaDespesasProvionadas();
    }

}
