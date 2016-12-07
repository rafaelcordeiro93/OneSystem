package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.IVA;
import br.com.onesystem.exception.DadoInvalidoException;

/**
 *
 * @author Rafael
 */
public class GrupoFiscalBuilder {

    private Long id;
    private String nome;
    private IVA iva;

    public GrupoFiscalBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public GrupoFiscalBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public GrupoFiscalBuilder comIVA(IVA iva) {
        this.iva = iva;
        return this;
    }

    public GrupoFiscal construir() throws DadoInvalidoException {
        return new GrupoFiscal(id, nome, iva);
    }

}
