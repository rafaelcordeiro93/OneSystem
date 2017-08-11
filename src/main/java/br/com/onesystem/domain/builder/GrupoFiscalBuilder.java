package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.TabelaDeTributacao;
import br.com.onesystem.exception.DadoInvalidoException;

/**
 *
 * @author Rafael
 */
public class GrupoFiscalBuilder {

    private Long id;
    private String nome;
    private TabelaDeTributacao tabelaDeTributacaoPadrao;

    public GrupoFiscalBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public GrupoFiscalBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public GrupoFiscalBuilder comTabelaDeTributacaoPadrao(TabelaDeTributacao tabelaDeTributacaoPadrao) {
        this.tabelaDeTributacaoPadrao = tabelaDeTributacaoPadrao;
        return this;
    }

    public GrupoFiscal construir() throws DadoInvalidoException {
        return new GrupoFiscal(id, nome, tabelaDeTributacaoPadrao);
    }

}
