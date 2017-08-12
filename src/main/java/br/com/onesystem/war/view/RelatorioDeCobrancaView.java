package br.com.onesystem.war.view;

import br.com.onesystem.war.service.impl.BasicMBReportImpl;
import br.com.onesystem.dao.CobrancaDAO;
import java.io.Serializable;
import javax.inject.Named;
import br.com.onesystem.domain.Cobranca;
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
public class RelatorioDeCobrancaView extends BasicMBReportImpl<Cobranca> implements Serializable {

    @PostConstruct
    @Override
    protected void init() {
        Coluna pessoa = new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Pessoa") + ")", bundle.getLabel("Pessoa"), "pessoa", "nome", Pessoa.class, String.class);
        pessoa.setTamanho(30);

        addExtraClass(Pessoa.class, "pessoa");
        addExtraClass(Moeda.class, "cotacao.conta.moeda");
        addCampoPadrao(new Coluna(bundle.getLabel("Id"), bundle.getLabel("Cobranca"), "id", Cobranca.class, Long.class));
        addCampoPadrao(pessoa);
        addCampoPadrao(new Coluna(bundle.getLabel("Emissao"), bundle.getLabel("Cobranca"), "emissao", Cobranca.class, Date.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Vencimento"), bundle.getLabel("Cobranca"), "vencimento", Cobranca.class, Date.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Valor"), bundle.getLabel("Cobranca"), "valor", Cobranca.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA));

        initialize(Cobranca.class, CobrancaDAO.class, TipoRelatorio.COBRANCA);
    }

}
