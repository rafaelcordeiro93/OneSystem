/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.war.builder.NotaEmitidaBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class DesdobramentoDeVendaView extends BasicMBImpl<NotaEmitida, NotaEmitidaBV> implements Serializable {

    private List<Cobranca> cobrancaDeletadas;
    private List<Cobranca> cobrancasNoBanco;
    private NotaEmitida notaEmitida;

    Cobranca c;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    @Override
    public void update() {

        Long id = cobrancasNoBanco.stream().max(Comparator.comparing(Cobranca::getId)).map(Cobranca::getId).get()+1;
        System.out.println("======================");
        System.out.println("==========Nota==========");
        System.out.println("======================");

        cobrancasNoBanco.forEach(System.out::println);

        System.out.println("======================");
        System.out.println("=======Nota Emitida========");
        System.out.println("======================");

        notaEmitida.getCobrancas().forEach(c -> System.out.println(id.compareTo(c.getId())));

        System.out.println("======================");
        System.out.println("=========Delete=========");
        System.out.println("======================");

        cobrancaDeletadas.forEach(System.out::println);

    }

    public void selecionar(SelectEvent event) {
        try {
            Object obj = event.getObject();
            String idComponent = event.getComponent().getId();
            if (obj instanceof NotaEmitida && "consultaDesdNotaEmitida-search".equals(idComponent)) {
                this.notaEmitida = new NotaEmitida((NotaEmitida) obj);
                cobrancasNoBanco = notaEmitida.getCobrancas().stream().collect(Collectors.toList());
            } else if (obj instanceof Cobranca) {
                Cobranca cb = (Cobranca) obj;
                if (cb.getId() != null && notaEmitida.getCobrancas().contains(cb)) {
                    notaEmitida.atualiza(cb);
                } else {
                    notaEmitida.adiciona(cb);
                }
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public Long getId() {
        Long id = cobrancasNoBanco.stream().max(Comparator.comparing(Cobranca::getId)).map(Cobranca::getId).get();
        id++;
        for (Cobranca p : notaEmitida.getCobrancas()) {
            if (p.getId() >= id) {
                id = p.getId() + 1;
            }
        }
        return id;
    }

    public void remover() {
        if (cobrancasNoBanco.contains(c)) {
            cobrancaDeletadas.add(c);
        }
        notaEmitida.remove(c);
    }

    public void selecionaParcela(SelectEvent event) {
        try {
            c = (Cobranca) event.getObject();
            removeDaSessao();
            SessionUtil.put(c, "cobranca", FacesContext.getCurrentInstance());
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void removeDaSessao() throws FDadoInvalidoException {
        SessionUtil.remove("cobranca", FacesContext.getCurrentInstance());
        SessionUtil.put(getId(), "idCobranca", FacesContext.getCurrentInstance());
    }

    public void limparJanela() {
        c = null;
        notaEmitida = null;
        cobrancaDeletadas = new ArrayList<>();
    }

    public List<Cobranca> getCobrancaDeletadas() {
        return cobrancaDeletadas;
    }

    public void setCobrancaDeletadas(List<Cobranca> cobrancaDeletadas) {
        this.cobrancaDeletadas = cobrancaDeletadas;
    }

    public NotaEmitida getNotaEmitida() {
        return notaEmitida;
    }

    public void setNotaEmitida(NotaEmitida notaEmitida) {
        this.notaEmitida = notaEmitida;
    }

    public List<Cobranca> getCobrancasNoBanco() {
        return cobrancasNoBanco;
    }

    public void setCobrancasNoBanco(List<Cobranca> cobrancasNoBanco) {
        this.cobrancasNoBanco = cobrancasNoBanco;
    }

    public String getRedirect() {
        return "dashboard?faces-redirect=true";
    }

}
