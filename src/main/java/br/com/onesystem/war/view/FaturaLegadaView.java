/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.CobrancaVariavel;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.FaturaLegada;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.ModelList;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.war.builder.FaturaLegadaBV;
import br.com.onesystem.war.builder.TituloBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import br.com.onesystem.war.view.dialogo.DialogoCobrancaView;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
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

    public void add() {
        try {
            FaturaLegada f = e.construir();
            int parcela = 1;
            for (Titulo t : list.getList()) {
                TituloBV bv = new TituloBV(t);
                bv.setParcela(parcela);
                parcela++;
                f.adiciona(bv.construir());
            }
            addNoBanco(f);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void addNovaParcela() throws DadoInvalidoException {
        try {
            SessionUtil.put(e.construir(), "fatura", FacesContext.getCurrentInstance());
            new DialogoCobrancaView().abrirDialogo();
        } catch (EDadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void update() {
        try {
            List<Titulo> removidos = list.getRemovidos().stream().filter(m -> ((Titulo) m.getObject()).getId() != null).map(m -> (Titulo) m.getObject()).collect(Collectors.toList());
            FaturaLegada f = e.construirComID();
            removidos.forEach(c -> f.remove(c));
            int parcela = 1;
            for (Titulo t : list.getList()) {
                TituloBV bv = new TituloBV(t);
                bv.setParcela(parcela);
                parcela++;
                f.atualiza(bv.construirComID());
            }
            new AtualizaDAO<>().atualiza(f);
            for (Titulo c : removidos) {
                new RemoveDAO<>().remove(c, c.getId());
            }
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
                if (e.getTitulo().size() > 0 && e.getTitulo() != null) {
                    list = new ModelList<>(e.getTitulo());
                }
                SessionUtil.put(e.construirComID(), "fatura", FacesContext.getCurrentInstance());
            } catch (DadoInvalidoException ex) {
                ex.print();
            }
        } else if (obj instanceof Pessoa) {
            e.setPessoa((Pessoa) obj);
        } else if (obj instanceof Titulo) {
            Titulo cb = (Titulo) obj;
            list.add(cb);
        } else if (obj instanceof Model) {
            Model m = (Model) obj;
            list.set(m);
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
            SessionUtil.put(e.construir(), "fatura", FacesContext.getCurrentInstance());
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void limparJanela() {
        try {
            removeDaSessao();
            SessionUtil.remove("fatura", FacesContext.getCurrentInstance());
            e = new FaturaLegadaBV();
            e.setFilial((Filial) SessionUtil.getObject("filial", FacesContext.getCurrentInstance()));
            modeloSelecionado = null;
            list = new ModelList<>();

        } catch (FDadoInvalidoException ex) {
            ex.print();
        }
    }

    public String getTotalParcelas() throws EDadoInvalidoException {
        if (list != null) {
            BigDecimal valor = list.getList().stream().map(CobrancaVariavel::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
            //return MoedaFomatter.format(nota.getMoedaPadrao(), valor);
            return e.getMoedaPadrao() + valor.toString();
        }
        return e.getMoedaPadrao();
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
