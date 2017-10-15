/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.ArmazemDeRegistrosConsole;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.ImpressoraDeLayoutTexto;
import br.com.onesystem.util.MatrixPrinter;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Rafael
 */
@Named
@ViewScoped
public class ImpressoraView implements Serializable {

    private String text;

    @Inject
    private ArmazemDeRegistros<NotaEmitida> armazem;
    
    public void imprimir() {
        try {

            NotaEmitida titulo = (NotaEmitida) armazem.daClasse(NotaEmitida.class).find(new Long(7));
            ImpressoraDeLayoutTexto gerenciador = new ImpressoraDeLayoutTexto("atendimento.json", Nota.class, titulo);
            gerenciador.imprimir("epson");
        } catch (Exception ex) {
            ErrorMessage.print(ex.getMessage());
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
