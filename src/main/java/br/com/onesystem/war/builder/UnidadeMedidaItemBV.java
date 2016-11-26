package br.com.onesystem.war.builder;

import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;

public class UnidadeMedidaItemBV implements Serializable {

    private Long id;
    private String nome;
    private String sigla;
    private Integer casasDecimais;

    public UnidadeMedidaItemBV(UnidadeMedidaItem unidadeMedidaItemSelecionada) {
        this.id = unidadeMedidaItemSelecionada.getId();
        this.nome = unidadeMedidaItemSelecionada.getNome();
        this.sigla = unidadeMedidaItemSelecionada.getSigla();
        this.casasDecimais = unidadeMedidaItemSelecionada.getCasasDecimais();
    }

    public UnidadeMedidaItemBV() {
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

    public void setSigla(String Sigla) {
        this.sigla = Sigla;
    }

    public Integer getCasasDecimais() {
        return casasDecimais;
    }

    public void setCasasDecimais(Integer casasDecimais) {
        this.casasDecimais = casasDecimais;
    }

    public UnidadeMedidaItem construir() throws DadoInvalidoException {
        return new UnidadeMedidaItem(null, nome, sigla, casasDecimais);
    }

    public UnidadeMedidaItem construirComID() throws DadoInvalidoException {
        return new UnidadeMedidaItem(id, nome, sigla, casasDecimais);
    }
}
