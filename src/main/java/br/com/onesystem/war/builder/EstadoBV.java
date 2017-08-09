package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Estado;
import br.com.onesystem.domain.Pais;
import br.com.onesystem.domain.builder.EstadoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;

public class EstadoBV implements Serializable, BuilderView<Estado> {
    
    private Long id;
    private String nome;
    private String sigla;
    private Pais pais;
    
    public EstadoBV(Estado estadoSelecionada) {
        this.id = estadoSelecionada.getId();
        this.nome = estadoSelecionada.getNome();
        this.sigla = estadoSelecionada.getSigla();
        this.pais = estadoSelecionada.getPais();
    }
    
    public EstadoBV() {
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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
    
    public Estado construir() throws DadoInvalidoException {
        return new EstadoBuilder().comNome(nome).comPais(pais).comSigla(sigla).construir();
    }
    
    public Estado construirComID() throws DadoInvalidoException {
                return new EstadoBuilder().comNome(nome).comPais(pais).comSigla(sigla).comId(id).construir();
    }
}
