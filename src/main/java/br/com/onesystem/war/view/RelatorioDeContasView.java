package br.com.onesystem.war.view;

import br.com.onesystem.war.service.impl.BasicMBReportImpl;
import br.com.onesystem.dao.CobrancaDAO;
import java.io.Serializable;
import javax.inject.Named;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.PostConstruct;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class RelatorioDeContasView extends BasicMBReportImpl<Cobranca> implements Serializable {

    @PostConstruct
    @Override
    protected void init() {
        Coluna pessoa = new Coluna("Nome (Pessoa)", "Pessoa", "pessoa", "nome", Pessoa.class, String.class, null);
        pessoa.setTamanho(30);
        
        addExtraClass(Pessoa.class, "pessoa");
        addExtraClass(Moeda.class, "cotacao.conta.moeda");
        addCampoPadrao(new Coluna("Id", "Cobrança", "id", Cobranca.class, Long.class, null));
        addCampoPadrao(pessoa);
        addCampoPadrao(new Coluna("Emissão", "Cobrança", "emissao", Cobranca.class, Date.class, null));
        addCampoPadrao(new Coluna("Vencimento", "Cobrança", "vencimento", Cobranca.class, Date.class, null));
        addCampoPadrao(new Coluna("Valor", "Cobrança", "valor", Cobranca.class, BigDecimal.class, null));

        initialize(Cobranca.class, CobrancaDAO.class);
    }

}
