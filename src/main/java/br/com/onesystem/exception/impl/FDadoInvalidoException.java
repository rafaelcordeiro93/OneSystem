package br.com.onesystem.exception.impl;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoDadoInvalido;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

/**
 * @author Rafael Fernando Rauber
 * @Data: 15/04/2013
 * @funcao Enviar excessões que podem ocorrer durante a execução. Deve ser usado
 * como um padrão para todas informações.
 */
public class FDadoInvalidoException extends DadoInvalidoException {

    private String valor;
    private String cabecalho = "Erro Catastrófico!";
    private final int tipo = 4;

    //Construtor para receber a mensagem.
    public FDadoInvalidoException(String mensagem) {
        this.valor = mensagem;
        this.cabecalho = TipoDadoInvalido.FATAL.getMensagem();
    }

    //Construtor para receber a mensagem, e o cabecalho.
    public FDadoInvalidoException(String mensagem, String cabecalho) {
        this.valor = mensagem;
        this.cabecalho = cabecalho;
    }

    @Override
    public String getMessage() {
        return valor;
    }

    @Override
    public Severity getTipo() {
        return FacesMessage.SEVERITY_FATAL;
    }

    @Override
    public String getCabecalho() {
        return cabecalho;
    }
}
