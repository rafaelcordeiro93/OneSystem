/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.util.MatrixPrinter;
import br.com.onesystem.services.ImpressoraService;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.GerenciadorDeImpressoraDeTexto;
import br.com.onesystem.util.MatricialPrintable;
import br.com.onesystem.valueobjects.FormatPage;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Rafael
 */
@Named
@ViewScoped
public class ImpressoraView implements Serializable{
    
    public void imprimir() {
        try {
            System.out.println("Imprimir - Criando Printable");
            GerenciadorDeImpressoraDeTexto
            MatrixPrinter printer = new MatrixPrinter(FormatPage.FORMAT_A5);
            
//            System.out.println("Criando Serviço");
//            ImpressoraService service = new ImpressoraService();

//            System.out.println("Imprimindo");
//            service.printPage(printable);

            System.out.println("Fim");
        } catch (Exception ex) {
            ErrorMessage.print(ex.getMessage());
        }
        
    }
    
}
