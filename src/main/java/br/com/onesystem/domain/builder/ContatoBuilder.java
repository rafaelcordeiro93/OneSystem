/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.domain.Contato;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.valueobjects.TipoTelefone;

/**
 *
 * @author Usuário
 */
public class ContatoBuilder {

    private String telefone;
    private TipoTelefone tipo;
    private Pessoa pessoa;
    private Long ID;
    private String contato;
    private String email;

    public ContatoBuilder comID(Long ID) {
        this.ID = ID;
        return this;
    }

    public ContatoBuilder comTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public ContatoBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public ContatoBuilder comTipo(TipoTelefone tipo) {
        this.tipo = tipo;
        return this;
    }

    public ContatoBuilder deContato(String contato) {
        this.contato = contato;
        return this;
    }

    public ContatoBuilder comEmail(String email) {
        this.email = email;
        return this;
    }

    public Contato construir() {
        return new Contato(ID,
                telefone != null ? telefone.replaceAll("-", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(" ", "").trim() : null,
                pessoa,
                tipo,
                contato,
                email);
    }
}
