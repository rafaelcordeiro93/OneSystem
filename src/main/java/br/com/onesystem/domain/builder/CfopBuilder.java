package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Cfop;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFisica;

/**
 *
 * @author Rafael
 */
public class CfopBuilder {

    private Long id;
    private String nome;
    private Long cfop;
    private OperacaoFisica operacaoFisica;
    private String texto;

    public CfopBuilder comId(Long Id) {
        this.id = Id;
        return this;
    }

    public CfopBuilder comCfop(Long cfop) {
        this.cfop = cfop;
        return this;
    }

    public CfopBuilder comOperacaoFisica(OperacaoFisica operacaoFisica) {
        this.operacaoFisica = operacaoFisica;
        return this;
    }

    public CfopBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public CfopBuilder comTexto(String texto) {
        this.texto = texto;
        return this;
    }

    public Cfop construir() throws DadoInvalidoException {
        return new Cfop(id, cfop, nome, operacaoFisica, texto);
    }

}
