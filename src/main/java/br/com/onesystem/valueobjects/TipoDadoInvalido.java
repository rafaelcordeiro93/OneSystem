/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.valueobjects;

/**
 *
 * @author Rauber
 */
public enum TipoDadoInvalido {

    ERRO(0, "Erro!"),
    INFORMACAO(1, "Informação"),
    AVISO(2, "Aviso!"),
    PERGUNTA(3, "Pergunta"),
    FATAL(4,"Fatal");
    
    private int codigo;
    private String mensagem;

    TipoDadoInvalido(int codigo, String mensagem) {
        this.codigo = codigo;
        this.mensagem = mensagem;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getMensagem() {
        return mensagem;
    }
}
