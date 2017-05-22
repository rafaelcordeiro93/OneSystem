/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.builder.CobrancaBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author rauber
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ConsultaCobrancaView extends BasicMBImpl<Cobranca, CobrancaBV> implements Serializable {

    @Inject
    private BundleUtil msg;

    private Cobranca cobranca;

    public void update() {
        try {
            if (cobranca != null) {

                if (cobranca instanceof Cheque) {
                    updateNoBanco(e.construirChequeComID());
                }
                if (cobranca instanceof BoletoDeCartao) {
                    updateNoBanco(e.construirBoletoDeCartaoComId());
                }
                if (cobranca instanceof Titulo) {
                    updateNoBanco(e.construirTituloComID());
                }
            }

        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void limparJanela() {
        cobranca = null;
        e = new CobrancaBV();
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Cobranca) {
            cobranca = (Cobranca) obj;
            e = new CobrancaBV(cobranca);
        }
    }

    public Cobranca getCobranca() {
        return cobranca;
    }

    public void setCobranca(Cobranca cobranca) {
        this.cobranca = cobranca;
    }

}
