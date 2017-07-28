/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.FaturaEmitida;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.ModelList;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.war.builder.FaturaEmitidaBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import br.com.onesystem.war.view.dialogo.DialogoCobrancaView;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael Cordeiro
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class FaturaEmitidaView extends BasicMBImpl<FaturaEmitida, FaturaEmitidaBV> implements Serializable {

    private Model<Titulo> modeloSelecionado;
    private ModelList<Titulo> list;
    private List<NotaEmitida> notaEmitidaList;
    private List<NotaEmitida> notaEmitidaRemovidas;
    private NotaEmitida notaEmitidaSelecionada;
    private Pessoa pessoaNota;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            FaturaEmitida f = e.construir();
            list.getList().forEach((t) -> {
                f.adiciona(t);
            });
            notaEmitidaList.forEach((n) -> {
                f.adiciona(n);
            });
            addNoBanco(f);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    @Override
    public void update() {
        try {
            List<Titulo> removidos = list.getRemovidos().stream().filter(m -> ((Titulo) m.getObject()).getId() != null).map(m -> (Titulo) m.getObject()).collect(Collectors.toList());
            FaturaEmitida f = e.construirComID();
            removidos.forEach(c -> f.remove(c));
            notaEmitidaRemovidas.forEach(c -> f.remove(c));
            list.getList().forEach((t) -> {
                f.atualiza(t);
            });
            notaEmitidaList.forEach((n) -> {
                f.atualiza(n);
            });
            new AtualizaDAO<>().atualiza(f);
            for (NotaEmitida ne : notaEmitidaRemovidas) {
                ne.setFaturaEmitida(null);
                new AtualizaDAO<>().atualiza(ne);
            }
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

    @Override
    public void delete() {
        try {
            for (NotaEmitida ne : e.getNotaEmitida()) {
                ne.setFaturaEmitida(null);
                new AtualizaDAO<>().atualiza(ne);
            }
            e.setNotaEmitida(null);
            FaturaEmitida f = e.construirComID();
            new AtualizaDAO<>().atualiza(f);
            deleteNoBanco(f, f.getId());
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void addNovaParcela() throws DadoInvalidoException {
        try {
            SessionUtil.put(e.construir(), "faturaEmitida", FacesContext.getCurrentInstance());
            new DialogoCobrancaView().abrirDialogo();
        } catch (EDadoInvalidoException die) {
            die.print();
        }
    }

    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        String cid = event.getComponent().getId();
        if (obj instanceof FaturaEmitida) {
            limparJanela();
            e = new FaturaEmitidaBV((FaturaEmitida) obj);
            if (e.getNotaEmitida().size() > 0 && e.getNotaEmitida() != null) {
                notaEmitidaList = e.getNotaEmitida();
            }
            buscaPessoaNota();
            if (e.getTitulo().size() > 0 && e.getTitulo() != null) {
                list = new ModelList<>(e.getTitulo());
            }
            try {
                SessionUtil.put(e.construirComID(), "faturaEmitida", FacesContext.getCurrentInstance());
            } catch (DadoInvalidoException ex) {
                ex.print();
            }
        } else if (obj instanceof Pessoa && cid.equals("pessoaID-search")) {
            e.setPessoa((Pessoa) obj);
        } else if (obj instanceof Pessoa && cid.equals("pessoaIDNota-search")) {
            pessoaNota = (Pessoa) obj;
            addPessoaSessao((Pessoa) obj);
        } else if (obj instanceof NotaEmitida) {
            addNotaEmitidaNaLista((NotaEmitida) obj);
        } else if (obj instanceof Titulo) {
            Titulo cb = (Titulo) obj;
            list.add(cb);
        } else if (obj instanceof Model) {
            Model m = (Model) obj;
            list.atualiza(m);
            modeloSelecionado = null;
        }
    }

    private void buscaPessoaNota() {
        for (NotaEmitida n : e.getNotaEmitida()) {
            pessoaNota = n.getPessoa();
            addPessoaSessao(n.getPessoa());
            break;
        }
    }

    private void addNotaEmitidaNaLista(NotaEmitida n) {
        if (!notaEmitidaList.contains(n)) {
            notaEmitidaList.add(n);
        }
    }

    public void removerNotaDaLista() {
        if (notaEmitidaSelecionada != null) {
            notaEmitidaList.remove(notaEmitidaSelecionada);
            if (e.getId() != null) {
                notaEmitidaRemovidas.add(notaEmitidaSelecionada);
            }
        }
    }

    public boolean habilitaBotaoPessoa() {
        if (notaEmitidaList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void addPessoaSessao(Pessoa ps) {
        try {
            SessionUtil.put(ps, "pessoaFaturaEmitida", FacesContext.getCurrentInstance());
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void removePessoaSessao() {
        try {
            SessionUtil.remove("pessoaFaturaEmitida", FacesContext.getCurrentInstance());
        } catch (DadoInvalidoException ex) {
            ex.print();
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
            SessionUtil.remove("faturaEmitida", FacesContext.getCurrentInstance());
            e = new FaturaEmitidaBV();
            modeloSelecionado = null;
            list = new ModelList<>();
            notaEmitidaList = new ArrayList<>();
            notaEmitidaRemovidas = new ArrayList<>();
            notaEmitidaSelecionada = null;
            pessoaNota = null;
            removePessoaSessao();
        } catch (FDadoInvalidoException ex) {
            ex.print();
        }
    }

    public String getTotalParcelas() throws EDadoInvalidoException {
        if (list != null) {
            BigDecimal valor = list.getList().stream().map(Cobranca::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
            return e.getMoedaPadrao() + valor.toString();
        }
        return e.getMoedaPadrao();
    }

    public String getTotalAFaturar() throws EDadoInvalidoException {
        if (notaEmitidaList != null) {
            BigDecimal valor = notaEmitidaList.stream().map(NotaEmitida::getaFaturar).reduce(BigDecimal.ZERO, BigDecimal::add);
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

    public NotaEmitida getNotaEmitidaSelecionada() {
        return notaEmitidaSelecionada;
    }

    public void setNotaEmitidaSelecionada(NotaEmitida notaEmitidaSelecionada) {
        this.notaEmitidaSelecionada = notaEmitidaSelecionada;
    }

    public List<NotaEmitida> getNotaEmitidaList() {
        return notaEmitidaList;
    }

    public void setNotaEmitidaList(List<NotaEmitida> notaEmitidaList) {
        this.notaEmitidaList = notaEmitidaList;
    }

    public Pessoa getPessoaNota() {
        return pessoaNota;
    }

    public void setPessoaNota(Pessoa pessoaNota) {
        this.pessoaNota = pessoaNota;
    }
}
