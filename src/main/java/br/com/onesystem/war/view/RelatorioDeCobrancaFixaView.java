package br.com.onesystem.war.view;

import br.com.onesystem.war.service.impl.BasicMBReportImpl;
import br.com.onesystem.dao.CobrancaFixaDAO;
import java.io.Serializable;
import javax.inject.Named;
import br.com.onesystem.domain.CobrancaFixa;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.valueobjects.TipoFormatacaoNumero;
import br.com.onesystem.valueobjects.TipoRelatorio;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.PostConstruct;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class RelatorioDeCobrancaFixaView extends BasicMBReportImpl<CobrancaFixa> implements Serializable {

    @PostConstruct
    @Override
    protected void init() {
        Coluna pessoa = new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Pessoa") + ")", bundle.getLabel("Pessoa"), "pessoa", "nome", Pessoa.class, String.class);

        pessoa.setTamanho(30);

        addExtraClass(Pessoa.class, "pessoa");
        addExtraClass(Moeda.class, "cotacao.conta.moeda");
        addCampoPadrao(new Coluna(bundle.getLabel("Id"), bundle.getLabel("Cobranca_Fixa"), "id", CobrancaFixa.class, Long.class));
        addCampoPadrao(pessoa);
        addCampoPadrao(new Coluna(bundle.getLabel("Emissao"), bundle.getLabel("Cobranca_Fixa"), "emissao", CobrancaFixa.class, Date.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Vencimento"), bundle.getLabel("Cobranca_Fixa"), "vencimento", CobrancaFixa.class, Date.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Valor"), bundle.getLabel("Cobranca_Fixa"), "valor", CobrancaFixa.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA));

        initialize(CobrancaFixa.class, CobrancaFixaDAO.class, TipoRelatorio.COBRANCA_FIXA);
    }

}
