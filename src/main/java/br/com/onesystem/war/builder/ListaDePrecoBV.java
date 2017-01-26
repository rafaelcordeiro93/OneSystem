package br.com.onesystem.war.builder;

import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.builder.ListaPrecoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;

public class ListaDePrecoBV implements Serializable {

    private Long id;
    private String nome;  

    public ListaDePrecoBV(ListaDePreco listaPrecoSelecionada) {
        this.id = listaPrecoSelecionada.getId();
        this.nome = listaPrecoSelecionada.getNome();
    }

    public ListaDePrecoBV() {
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

    public ListaDePreco construir() throws DadoInvalidoException {
        return new ListaPrecoBuilder().comNome(nome).construir();
    }

    public ListaDePreco construirComID() throws DadoInvalidoException {
        return new ListaPrecoBuilder().comID(id).comNome(nome).construir();
    }
}
