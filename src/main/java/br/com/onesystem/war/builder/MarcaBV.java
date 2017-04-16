package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Marca;
import br.com.onesystem.domain.builder.MarcaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;

public class MarcaBV implements Serializable, BuilderView<Marca> {
    
    private Long id;
    private String nome;    
    
    public MarcaBV(Marca marcaSelecionada) {
        this.id = marcaSelecionada.getId();
        this.nome = marcaSelecionada.getNome();
    }
    
    public MarcaBV() {
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
    
    public Marca construir() throws DadoInvalidoException {
        return new MarcaBuilder().comNome(nome).construir();
    }
    
    public Marca construirComID() throws DadoInvalidoException {
        return new MarcaBuilder().comID(id).comNome(nome).construir();
    }
}
