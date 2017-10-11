/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.ArmazemDeRegistrosConsole;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.GerenciadorDeImpressoraDeTexto;
import br.com.onesystem.util.MatrixPrinter;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Rafael
 */
@Named
@ViewScoped
public class ImpressoraView implements Serializable {

    public void imprimir() {
        try {
            System.out.println("Imprimir - Criando Printable");

            Titulo titulo = new ArmazemDeRegistrosConsole<Titulo>(Titulo.class).find(new Long(4));
            GerenciadorDeImpressoraDeTexto gerenciador = new GerenciadorDeImpressoraDeTexto("titulo.json", Titulo.class, titulo);
            gerenciador.imprimir("\\\\localhost\\epson");

            System.out.println("Fim");
        } catch (Exception ex) {
            ErrorMessage.print(ex.getMessage());
        }

    }

}
