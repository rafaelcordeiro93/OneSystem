/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author rauber
 */
@ManagedBean
@ViewScoped
public class ConsultaNotaEmitidaView extends BasicMBImpl<NotaEmitida> implements Serializable {

    private NotaEmitida notaEmitida;
    
    @PostConstruct
    public void construir(){
   }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        System.out.println("1");
        if (obj instanceof NotaEmitida) {
            notaEmitida = (NotaEmitida) obj;
        }
    }

    public void limpar() {
        notaEmitida = null;
    }

    public NotaEmitida getNotaEmitida() {
        return notaEmitida;
    }

    public void setNotaEmitida(NotaEmitida notaEmitida) {
        this.notaEmitida = notaEmitida;
    }
}
