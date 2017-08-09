package br.com.onesystem.war.builder;

import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.TabelaDeTributacao;
import br.com.onesystem.domain.builder.GrupoFiscalBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;

public class GrupoFiscalBV implements Serializable, BuilderView<GrupoFiscal> {

    private Long id;
    private String nome;
    private TabelaDeTributacao tabelaDeTributacaoPadrao;

    public GrupoFiscalBV(GrupoFiscal grupoFiscalSelecionada) {
        this.id = grupoFiscalSelecionada.getId();
        this.tabelaDeTributacaoPadrao = grupoFiscalSelecionada.getTabelaDeTributacaoPadrao();
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

    public TabelaDeTributacao getIva() {
        return tabelaDeTributacaoPadrao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIva(TabelaDeTributacao iva) {
        this.tabelaDeTributacaoPadrao = iva;
    }

    public GrupoFiscal construir() throws DadoInvalidoException {
        return new GrupoFiscalBuilder().comNome(nome).comTabelaDeTributacaoPadrao(tabelaDeTributacaoPadrao).construir();
    }

    public GrupoFiscal construirComID() throws DadoInvalidoException {
        return new GrupoFiscalBuilder().comID(id).comNome(nome).comTabelaDeTributacaoPadrao(tabelaDeTributacaoPadrao).construir();
    }
}
