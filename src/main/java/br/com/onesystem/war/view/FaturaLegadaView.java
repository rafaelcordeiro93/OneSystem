/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.FaturaLegada;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.ModelList;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.war.builder.FaturaLegadaBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import br.com.onesystem.war.view.dialogo.DialogoCobrancaView;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class FaturaLegadaView extends BasicMBImpl<FaturaLegada, FaturaLegadaBV> implements Serializable {

    private Model<Titulo> modeloSelecionado;
    private ModelList<Titulo> list;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    @Override
    public void update() {
        try {
           
            //add    e.setTitulo(list.getList());
            
            
            //            List<Cobranca> removidos = list.getRemovidos().stream().filter(m -> ((Cobranca) m.getObject()).getId() != null).map(m -> (Cobranca) m.getObject()).collect(Collectors.toList());
//            removidos.forEach(c -> nota.remove(c));
//
//            new AtualizaDAO<>().atualiza(nota);
//            for (Cobranca c : removidos) {
//                new RemoveDAO<>().remove(c, c.getId());
//            }

            SessionUtil.put(e.construir(), "faturaLegada", FacesContext.getCurrentInstance());
        } catch (DadoInvalidoException die) {
            die.print();
        }

        InfoMessage.atualizado();
        RequestContext.getCurrentInstance().update("conteudo");
        limparJanela();
    }

    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof FaturaLegada) {
            try {
                e = new FaturaLegadaBV((FaturaLegada) obj);
                if (e.getTitulo().size() > 0) {
                    list = new ModelList<>(e.getTitulo());
                }

                SessionUtil.put(e.construirComID(), "faturaLegada", FacesContext.getCurrentInstance());
            } catch (DadoInvalidoException ex) {
                ex.print();
            }
        } else if (obj instanceof Pessoa) {
            e.setPessoa((Pessoa) obj);
        } else if (obj instanceof Titulo) {
            System.out.println("" + obj + ((Titulo) obj).getPessoa());
            Titulo cb = (Titulo) obj;
            list.add(cb);
        } else if (obj instanceof Model) {
            Model m = (Model) obj;
            list.atualiza(m);
            modeloSelecionado = null;
        }
    }

    public void remover() {
        list.remove(modeloSelecionado);
        modeloSelecionado = null;
    }

    public void selecionaParcela(SelectEvent event) {
        try {
            modeloSelecionado = (Model<Titulo>) event.getObject();
            removeDaSessao();
            SessionUtil.put(modeloSelecionado, "model", FacesContext.getCurrentInstance());
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void limparJanela() {
        try {
            removeDaSessao();
            SessionUtil.remove("faturaLegada", FacesContext.getCurrentInstance());
            e = new FaturaLegadaBV();
            modeloSelecionado = null;
            list = new ModelList<>();
        } catch (FDadoInvalidoException ex) {
            ex.print();
        }
    }

    public String getTotalParcelas() {
        if (list != null) {
            BigDecimal valor = list.getList().stream().map(Cobranca::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
            //return MoedaFomatter.format(nota.getMoedaPadrao(), valor);
            return valor.toString();
        }
        return "";
    }

    public void addNovaParcela() throws DadoInvalidoException {
        SessionUtil.put(e.construir(), "faturaLegada", FacesContext.getCurrentInstance());
        new DialogoCobrancaView().abrirDialogo();
    }

    public void removeDaSessao() throws FDadoInvalidoException {
        SessionUtil.remove("model", FacesContext.getCurrentInstance());
    }

    public ModelList<Titulo> getList() {
        return list;
    }

    public void setList(ModelList<Titulo> list) {
        this.list = list;
    }

    public Model getModelo() {
        return modeloSelecionado;
    }

    public void setModelo(Model modelo) {
        this.modeloSelecionado = modelo;
    }

}
