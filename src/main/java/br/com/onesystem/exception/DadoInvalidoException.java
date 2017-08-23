package br.com.onesystem.exception;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import static javax.faces.context.FacesContext.getCurrentInstance;

/**
 * @author Rafael Fernando Rauber
 * @Data: 16/04/2013
 * @funcao
 */
public abstract class DadoInvalidoException extends Exception {

    //Utilizado para retornar o texto da excessão.
    @Override
    public abstract String getMessage();

    //Utilizado para retornar o tipo da mensagem.
    public abstract Severity getTipo();

    //Utilizado para retornar o texto do cabeçalho.
    public abstract String getCabecalho();

    //Exibe a mensagem na janela.
    public void print() {
        getCurrentInstance().addMessage(null,
                new FacesMessage(getTipo(), getCabecalho(), getMessage()));
    }
    
    //Exibe a mensagem no console.
    public void printConsole() {
        System.out.println(getMessage());
    }
}
