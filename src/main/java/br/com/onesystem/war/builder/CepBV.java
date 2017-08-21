package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Cep;
import br.com.onesystem.domain.Cidade;
import br.com.onesystem.domain.builder.CepBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;

public class CepBV implements Serializable, BuilderView<Cep> {
    
    private Long id;
    private String cep;
    private Cidade cidade;
    
    public CepBV(Cep cepSelecionada) {
        this.id = cepSelecionada.getId();
        this.cep = cepSelecionada.getCep();
        this.cidade = cepSelecionada.getCidade();
    }
    
    public CepBV() {
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCep() {
        return cep;
    }
    
    public void setCep(String cep) {
        this.cep = cep;
    }
    
    public Cidade getCidade() {
        return cidade;
    }
    
    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
    
    public Cep construir() throws DadoInvalidoException {
        return new CepBuilder().comCep(cep).comCidade(cidade).construir();
    }
    
    public Cep construirComID() throws DadoInvalidoException {
        return new CepBuilder().comID(id).comCep(cep).comCidade(cidade).construir();
    }
}
