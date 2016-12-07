package br.com.onesystem.war.builder;

import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.IVA;
import br.com.onesystem.domain.builder.GrupoFiscalBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;

public class GrupoFiscalBV implements Serializable {

    private Long id;
    private String nome;
    private IVA iva;

    public GrupoFiscalBV(GrupoFiscal grupoFiscalSelecionada) {
        this.id = grupoFiscalSelecionada.getId();        
        this.iva = grupoFiscalSelecionada.getIva();
        this.nome = grupoFiscalSelecionada.getNome();
    }

    public GrupoFiscalBV() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public IVA getIva() {
        return iva;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIva(IVA iva) {
        this.iva = iva;
    }
    
    public GrupoFiscal construir() throws DadoInvalidoException {
        return new GrupoFiscalBuilder().comNome(nome).comIVA(iva).construir();
    }

    public GrupoFiscal construirComID() throws DadoInvalidoException {
        return new GrupoFiscalBuilder().comID(id).comNome(nome).comIVA(iva).construir();
    }
}
