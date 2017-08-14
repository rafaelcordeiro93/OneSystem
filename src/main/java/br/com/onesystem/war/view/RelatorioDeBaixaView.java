package br.com.onesystem.war.view;

import br.com.onesystem.war.service.impl.BasicMBReportImpl;
import br.com.onesystem.dao.BaixaDAO;
import java.io.Serializable;
import javax.inject.Named;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.valueobjects.TipoFormatacaoNumero;
import br.com.onesystem.valueobjects.TipoRelatorio;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.PostConstruct;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class RelatorioDeBaixaView extends BasicMBReportImpl<Baixa> implements Serializable {

    @PostConstruct
    @Override
    protected void init() {
        Coluna pessoa = new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Pessoa") + ")", bundle.getLabel("Pessoa"), "pessoa", "nome", Pessoa.class, String.class);
        pessoa.setTamanho(30);

        addExtraClass(Pessoa.class, "pessoa");
        addExtraClass(Moeda.class, "cotacao.conta.moeda");
        addExtraClass(TipoDespesa.class, "tipoDespesa");
        addExtraClass(TipoReceita.class, "tipoReceita");
        addExtraClass(Caixa.class, "caixa");
        addCampoPadrao(new Coluna(bundle.getLabel("Id"), bundle.getLabel("Baixa"), "id", Baixa.class, Long.class));
        addCampoPadrao(pessoa);
        addCampoPadrao(new Coluna(bundle.getLabel("Emissao"), bundle.getLabel("Baixa"), "emissao", Baixa.class, Date.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Id") + "(" + bundle.getLabel("Caixa") + ")", bundle.getLabel("Baixa"), "vencimento", Baixa.class, Date.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Valor"), bundle.getLabel("Baixa"), "valor", Baixa.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA));

        initialize(Baixa.class, BaixaDAO.class, TipoRelatorio.BAIXA);
    }

}
