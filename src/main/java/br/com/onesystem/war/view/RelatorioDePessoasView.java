package br.com.onesystem.war.view;

import br.com.onesystem.dao.PessoaDAO;
import br.com.onesystem.domain.Cidade;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.war.service.impl.BasicMBReportImpl;
import java.io.Serializable;
import javax.inject.Named;
import br.com.onesystem.domain.Pessoa;
import javax.annotation.PostConstruct;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class RelatorioDePessoasView extends BasicMBReportImpl<Pessoa> implements Serializable {

    @PostConstruct
    @Override
    protected void init() {
        addExtraClass(Cidade.class, "cidade");

        addCampoPadrao(new Coluna(bundle.getLabel("Id"), bundle.getLabel("Pessoa"), "id", Pessoa.class, Long.class, null));
        addCampoPadrao(new Coluna(bundle.getLabel("Nome"), bundle.getLabel("Pessoa"), "nome", Pessoa.class, String.class, null));
        addCampoPadrao(new Coluna(bundle.getLabel("Telefone"), bundle.getLabel("Pessoa"), "telefone", Pessoa.class, String.class, null));
        addCampoPadrao(new Coluna(bundle.getLabel("Endereco"), bundle.getLabel("Pessoa"), "direcao", Pessoa.class, String.class, null));
        addCampoPadrao(new Coluna(bundle.getLabel("Bairro"), bundle.getLabel("Pessoa"), "bairro", Pessoa.class, String.class, null));
        addCampoPadrao(new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Cidade") + ")", bundle.getLabel("Cidade"), "cidade", "nome", Cidade.class, String.class, null));

        initialize(Pessoa.class, PessoaDAO.class);
    }

}
