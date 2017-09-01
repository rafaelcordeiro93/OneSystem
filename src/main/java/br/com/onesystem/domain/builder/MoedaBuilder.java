package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Moeda;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoBandeira;

/**
 *
 * @author Rafael
 */
public class MoedaBuilder {

    private Long ID;
    private String nome;
    private String nomePlural;
    private String nomeCasasDecimais;
    private String nomeCasasDecimaisPlural;
    private String sigla;
    private TipoBandeira bandeira;

    public MoedaBuilder comID(Long ID) {
        this.ID = ID;
        return this;
    }

    public MoedaBuilder comNome(String nombre) {
        this.nome = nombre;
        return this;
    }

    public MoedaBuilder comNomePlural(String nomePlural) {
        this.nomePlural = nomePlural;
        return this;
    }

    public MoedaBuilder comNomeCasasDecimais(String nomeCasasDecimais) {
        this.nomeCasasDecimais = nomeCasasDecimais;
        return this;
    }

    public MoedaBuilder comNomeCasasDecimaisPlural(String nomeCasasDecimaisPlural) {
        this.nomeCasasDecimaisPlural = nomeCasasDecimaisPlural;
        return this;
    }

    public MoedaBuilder comSigla(String sigla) {
        this.sigla = sigla;
        return this;
    }

    public MoedaBuilder comBandeira(TipoBandeira bandeira) {
        this.bandeira = bandeira;
        return this;
    }

    public Moeda construir() throws DadoInvalidoException {
        return new Moeda(ID, nome, sigla, nomePlural, nomeCasasDecimais, nomeCasasDecimaisPlural, bandeira);
    }

}
