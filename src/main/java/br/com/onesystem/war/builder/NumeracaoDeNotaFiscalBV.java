package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.LoteNotaFiscal;
import br.com.onesystem.domain.NumeracaoDeNotaFiscal;
import br.com.onesystem.domain.builder.NumeracaoDeNotaFiscalBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;

public class NumeracaoDeNotaFiscalBV implements Serializable, BuilderView<NumeracaoDeNotaFiscal> {

    private Long id;
    private Long numeroNF;
    private LoteNotaFiscal loteNotaFiscal;
    private Filial filial;

    public NumeracaoDeNotaFiscalBV(NumeracaoDeNotaFiscal n) {
        this.id = n.getId();
        this.numeroNF = n.getNumeroNF();
        this.loteNotaFiscal = n.getLoteNotaFiscal();
        this.filial = n.getFilial();
    }

    public NumeracaoDeNotaFiscalBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroNF() {
        return numeroNF;
    }

    public void setNumeroNF(Long numeroNF) {
        this.numeroNF = numeroNF;
    }

    public LoteNotaFiscal getLoteNotaFiscal() {
        return loteNotaFiscal;
    }

    public void setLoteNotaFiscal(LoteNotaFiscal loteNotaFiscal) {
        this.loteNotaFiscal = loteNotaFiscal;
    }

    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }

    public NumeracaoDeNotaFiscal construir() throws DadoInvalidoException {
        return new NumeracaoDeNotaFiscalBuilder().comNumeroNF(numeroNF).comLoteNotaFiscal(loteNotaFiscal).comFilial(filial).construir();
    }

    public NumeracaoDeNotaFiscal construirComID() throws DadoInvalidoException {
        return new NumeracaoDeNotaFiscalBuilder().comId(id).comNumeroNF(numeroNF).comLoteNotaFiscal(loteNotaFiscal).comFilial(filial).construir();
    }
}
