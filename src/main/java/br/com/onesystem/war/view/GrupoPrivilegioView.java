/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.Janela;
import br.com.onesystem.domain.Privilegio;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.war.builder.GrupoDePrivilegioBV;
import br.com.onesystem.war.service.JanelaService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class GrupoPrivilegioView extends BasicMBImpl<GrupoDePrivilegio, GrupoDePrivilegioBV> implements Serializable {

    @Inject
    private JanelaService serviceJanela;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            GrupoDePrivilegio novoRegistro = e.construir();
            addNoBanco(novoRegistro);
            geraPrivilegio(novoRegistro);
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void geraPrivilegio(GrupoDePrivilegio grupo) {
        List<Janela> janelas = serviceJanela.buscarJanelas();
        for (Janela j : janelas) {
            try {
                new AdicionaDAO<Privilegio>().adiciona(new Privilegio(null, j, false, false, false, false, grupo));
            } catch (DadoInvalidoException die) {
                die.print();
                break;
            }
        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof GrupoDePrivilegio) {
            e = new GrupoDePrivilegioBV((GrupoDePrivilegio) obj);
        }
    }

    public void limparJanela() {
        e = new GrupoDePrivilegioBV();
    }

    public GrupoDePrivilegioBV getGrupoPrivilegioBV() {
        return e;
    }

    public void setGrupoPrivilegioBV(GrupoDePrivilegioBV e) {
        this.e = e;
    }

    public JanelaService getServiceJanela() {
        return serviceJanela;
    }

    public void setServiceJanela(JanelaService serviceJanela) {
        this.serviceJanela = serviceJanela;
    }

}
