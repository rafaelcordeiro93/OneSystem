package br.com.onesystem.war.view;

import br.com.onesystem.war.service.impl.BasicMBReportImpl;
import br.com.onesystem.dao.CobrancaDAO;
import java.io.Serializable;
import javax.inject.Named;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.util.FieldModel;
import javax.annotation.PostConstruct;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class RelatorioDeContasView extends BasicMBReportImpl<Cobranca> implements Serializable {

    @PostConstruct
    @Override
    protected void init() {
        addExtraClass(Pessoa.class, "pessoa");
        addExtraClass(Moeda.class, "cotacao.conta.moeda");
        addCampoPadrao(new FieldModel("Id", "Cobrança", "id", Cobranca.class, Long.class));
        addCampoPadrao(new FieldModel("Nome (Pessoa)", "Pessoa", "pessoa", "nome", Pessoa.class, String.class));
        addCampoPadrao(new FieldModel("Emissão Formatada Sem Horas", "Cobrança", "emissaoFormatadaSemHoras", Cobranca.class, String.class));
        addCampoPadrao(new FieldModel("Vencimento Formatado Sem Horas", "Cobrança", "vencimentoFormatadoSemHoras", Cobranca.class, String.class));
        addCampoPadrao(new FieldModel("Valor Formatado", "Cobrança", "valorFormatado", Cobranca.class, String.class));

        initialize(Cobranca.class, CobrancaDAO.class);
    }

}
