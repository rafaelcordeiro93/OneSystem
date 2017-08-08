package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Cidade;
import br.com.onesystem.domain.Estado;
import br.com.onesystem.domain.builder.CidadeBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;

public class CidadeBV implements Serializable, BuilderView<Cidade> {
    
    private Long id;
    private String nome;
    private Estado estado;
    
    public CidadeBV(Cidade cidadeSelecionada) {
        this.id = cidadeSelecionada.getId();
        this.nome = cidadeSelecionada.getNome();
        this.estado = cidadeSelecionada.getEstado();
    }
    
    public CidadeBV() {
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
    
    public Estado getEstado() {
        return estado;
    }
    
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    public Cidade construir() throws DadoInvalidoException {
        return new CidadeBuilder().comNome(nome).comEstado(estado).construir();
    }
    
    public Cidade construirComID() throws DadoInvalidoException {
        return new CidadeBuilder().comID(id).comNome(nome).comEstado(estado).construir();
    }
}
