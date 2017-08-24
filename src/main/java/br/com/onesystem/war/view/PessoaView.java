package br.com.onesystem.war.view;

import br.com.onesystem.domain.Cep;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.war.builder.PessoaBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class PessoaView extends BasicMBImpl<Pessoa, PessoaBV> implements Serializable {

    private Date dataMaximaDeNascimento = new Date();

    @PostConstruct
    public void init() {
        limparJanela();
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Pessoa) {
            e = new PessoaBV((Pessoa) obj);
        } else if (obj instanceof Cep) {
            e.setCep((Cep) obj);
        }
    }

    public void limparJanela() {
        e = new PessoaBV();
    }

    public Date getDataMaximaDeNascimento() {
        return dataMaximaDeNascimento;
    }

    public void setDataMaximaDeNascimento(Date dataMaximaDeNascimento) {
        this.dataMaximaDeNascimento = dataMaximaDeNascimento;
    }

}
