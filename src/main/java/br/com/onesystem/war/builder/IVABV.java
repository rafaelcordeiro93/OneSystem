package br.com.onesystem.war.builder;

import br.com.onesystem.domain.TabelaDeTributacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;
import java.math.BigDecimal;

public class IVABV implements Serializable, BuilderView<TabelaDeTributacao> {

    private Long id;
    private String nome;   
    private BigDecimal iva;

    public IVABV(TabelaDeTributacao ivaSelecionado) {
        this.id = ivaSelecionado.getId();
        this.nome = ivaSelecionado.getNome();
        this.iva = ivaSelecionado.getIva();
    }

    public IVABV() {
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

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

     public TabelaDeTributacao construir() throws DadoInvalidoException {
        return new TabelaDeTributacao(null, iva, nome);
    }

    public TabelaDeTributacao construirComID() throws DadoInvalidoException {
        return new TabelaDeTributacao(id, iva, nome);
    }
}
