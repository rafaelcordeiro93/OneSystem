/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.Privilegio;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.RelatorioPrivilegioPorModulo;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.PrivilegioBV;
import br.com.onesystem.war.service.GrupoPrivilegioService;
import br.com.onesystem.war.service.PrivilegioService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;

import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class PrivilegioView extends BasicMBImpl<Privilegio, PrivilegioBV> implements Serializable {

    private GrupoDePrivilegio grupoSelecionado;
    private GrupoDePrivilegio outroGrupo;
    private List<Privilegio> privilegioLista;

    @Inject
    private PrivilegioService service;

    @Inject
    private GrupoPrivilegioService grupoService;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void limparJanela() {
        privilegioLista = new ArrayList<Privilegio>();
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        String idComponent = event.getComponent().getId();
        if (obj instanceof GrupoDePrivilegio && "grupoDePrivilegioID-search".equals(idComponent)) {
            grupoSelecionado = (GrupoDePrivilegio) obj;
            buscarPrivilegioLista();
        } else if (obj instanceof GrupoDePrivilegio && "grupoDePrivilegioOutro-search".equals(idComponent)) {
            outroGrupo = (GrupoDePrivilegio) obj;
        }
    }

    public void buscarPrivilegioLista() { 
        privilegioLista = service.buscarPrivilegioDoGrupo(grupoSelecionado);
    }

    public void save() {
        try {
            for (Privilegio p : privilegioLista) {
                new AtualizaDAO<Privilegio>().atualiza(p);
            }
            InfoMessage.atualizado();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void permitir() {
        for (Privilegio p : privilegioLista) {
            p.setConsultar(true);
            p.setAdicionar(true);
            p.setRemover(true);
            p.setAlterar(true);
        }
    }

    public void negar() {
        for (Privilegio p : privilegioLista) {
            p.setConsultar(false);
            p.setAdicionar(false);
            p.setRemover(false);
            p.setAlterar(false);
        }
    }

    public void copiarDeOutroGrupo() {
        List<Privilegio> lista = service.buscarPrivilegioDoGrupo(outroGrupo);
        for (Privilegio p : privilegioLista) {
            for (Privilegio l : lista) {
                if (p.getJanela().getId().equals(l.getJanela().getId())) {
                    p.setAdicionar(l.isAdicionar());
                    p.setAlterar(l.isAlterar());
                    p.setRemover(l.isRemover());
                    p.setConsultar(l.isConsultar());
                    break;
                }
            }
        }
        Collections.sort(privilegioLista, new RelatorioPrivilegioPorModulo());
    }

    public PrivilegioService getService() {
        return service;
    }

    public void setService(PrivilegioService service) {
        this.service = service;
    }

    public GrupoPrivilegioService getGrupoService() {
        return grupoService;
    }

    public void setGrupoService(GrupoPrivilegioService grupoService) {
        this.grupoService = grupoService;
    }

    public List<Privilegio> getPrivilegioLista() {
        Collections.sort(privilegioLista, new RelatorioPrivilegioPorModulo());
        return privilegioLista;
    }

    public void setPrivilegioLista(List<Privilegio> privilegioLista) {
        this.privilegioLista = privilegioLista;
    }

    public GrupoDePrivilegio getOutroGrupo() {
        return outroGrupo;
    }

    public void setOutroGrupo(GrupoDePrivilegio outroGrupo) {
        this.outroGrupo = outroGrupo;
    }

    public GrupoDePrivilegio getGrupoSelecionado() {
        return grupoSelecionado;
    }

    public void setGrupoSelecionado(GrupoDePrivilegio grupoSelecionado) {
        this.grupoSelecionado = grupoSelecionado;
    }

}
