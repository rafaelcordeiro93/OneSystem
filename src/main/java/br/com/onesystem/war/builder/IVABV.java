package br.com.onesystem.war.builder;

import br.com.onesystem.domain.IVA;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;
import java.math.BigDecimal;

public class IVABV implements Serializable, BuilderView<IVA> {

    private Long id;
    private String nome;   
    private BigDecimal iva;

    public IVABV(IVA ivaSelecionado) {
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

     public IVA construir() throws DadoInvalidoException {
        return new IVA(null, iva, nome);
    }

    public IVA construirComID() throws DadoInvalidoException {
        return new IVA(id, iva, nome);
    }
}
