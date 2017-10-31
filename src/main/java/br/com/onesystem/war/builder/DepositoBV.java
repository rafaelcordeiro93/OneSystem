package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.builder.DepositoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;
import java.util.List;

public class DepositoBV implements Serializable, BuilderView<Deposito> {

    private Long id;
    private String nome;  
    private List<Filial> filiais;

    public DepositoBV(Deposito d) {
        this.id = d.getId();
        this.nome = d.getNome();
    }

    public DepositoBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Filial> getFiliais() {
        return filiais;
    }

    public void setFiliais(List<Filial> filiais) {
        this.filiais = filiais;
    }
    
    public Deposito construir() throws DadoInvalidoException {
        return new DepositoBuilder().comNome(nome).construir();
    }

    public Deposito construirComID() throws DadoInvalidoException {
        return new DepositoBuilder().comID(id).comNome(nome).construir();
    }
}
