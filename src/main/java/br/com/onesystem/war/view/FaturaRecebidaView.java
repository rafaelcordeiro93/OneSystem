/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.FaturaRecebidaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.dao.ValorPorCotacaoDAO;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.FaturaRecebida;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.NotaRecebida;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.ValorPorCotacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.ModelList;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.war.builder.FaturaRecebidaBV;
import br.com.onesystem.war.builder.TituloBV;
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
public class FaturaRecebidaView extends BasicMBImpl<FaturaRecebida, FaturaRecebidaBV> implements Serializable {

    private Model<Titulo> modeloSelecionado;
    private ModelList<Titulo> list;
    private List<NotaRecebida> notaRecebidaList;
    private List<NotaRecebida> notaRecebidaRemovidas;
    private List<ValorPorCotacao> valorPorCotacaoList;
    private NotaRecebida notaRecebidaSelecionada;
    private Pessoa pessoaNota;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            FaturaRecebida f = e.construir();

            int parcela = 1;
            for (Titulo t : list.getList()) {
                TituloBV bv = new TituloBV(t);
                bv.setParcela(parcela);
                parcela++;
                f.adiciona(bv.construir());
            }
            notaRecebidaList.forEach((n) -> {
                f.adiciona(n);
            });
            valorPorCotacaoList.forEach((v) -> {
                f.adiciona(v);
            });
            new AdicionaDAO<>().adiciona(f);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
        InfoMessage.atualizado();
        RequestContext.getCurrentInstance().update("conteudo");
        limparJanela();
    }

    @Override
    public void update() {
        try {
            List<Titulo> removidos = list.getRemovidos().stream().filter(m -> ((Titulo) m.getObject()).getId() != null).map(m -> (Titulo) m.getObject()).collect(Collectors.toList());
            FaturaRecebida f = e.construirComID();
            removidos.forEach(c -> f.remove(c));
            notaRecebidaRemovidas.forEach(c -> f.remove(c));
            List<ValorPorCotacao> valoresARemover = new ValorPorCotacaoDAO().porFaturaRecebida(f).listaDeResultados();
            if (valorPorCotacaoList.size() > 0) {
                valoresARemover.forEach(v -> f.remove(v));
            }
            int parcela = 1;
            for (Titulo t : list.getList()) {
                TituloBV bv = new TituloBV(t);
                bv.setParcela(parcela);
                parcela++;
                f.atualiza(bv.construirComID());
            }
            notaRecebidaList.forEach((n) -> {
                f.atualiza(n);
            });
            valorPorCotacaoList.forEach((v) -> {
                f.adiciona(v);//Adiciona as os ValoresPorCotacão Novos
            });
            new AtualizaDAO<>().atualiza(f);
            if (valorPorCotacaoList.size() > 0) {
                for (ValorPorCotacao v : valoresARemover) {
                    new RemoveDAO<>().remove(v, v.getId());
                }
            }
            for (NotaRecebida ne : notaRecebidaRemovidas) {
                ne.setFaturaRecebida(null);
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
            for (NotaRecebida ne : e.getNotaRecebida()) {
                ne.setFaturaRecebida(null);
                new AtualizaDAO<>().atualiza(ne);
            }
            e.setNotaRecebida(null);
            FaturaRecebida f = e.construirComID();
            new AtualizaDAO<>().atualiza(f);
            deleteNoBanco(f, f.getId());
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void addNovaParcela() throws DadoInvalidoException {
        try {
            if (modeloSelecionado != null) {
                SessionUtil.remove("parcela", FacesContext.getCurrentInstance());
            }
            SessionUtil.put(e.construir(), "fatura", FacesContext.getCurrentInstance());
            new DialogoCobrancaView().abrirDialogo();
        } catch (EDadoInvalidoException die) {
            die.print();
        }
    }

    public void validaDinheiro() throws DadoInvalidoException {
        BigDecimal valorBanco = BigDecimal.ZERO; // Se existir valor em dinheiro abre a janela de cotações.     
        if (e.getId() != null) {
            valorBanco = new FaturaRecebidaDAO().porId(e.getId()).resultado().getDinheiro();
        }
        if (e.getDinheiro() != null && e.getDinheiro().compareTo(BigDecimal.ZERO) > 0 && valorBanco.subtract(e.getDinheiro()).compareTo(BigDecimal.ZERO) != 0) {
            SessionUtil.put(e.construir(), "fatura", FacesContext.getCurrentInstance());
            RequestContext.getCurrentInstance().execute("document.getElementById(\"conteudo:abreDialogoCotacao-btn\").click();");
        } else if (e.getId() == null) {
            add();
        } else {
            update();
        }
    }

    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        String cid = event.getComponent().getId();
        if (obj instanceof FaturaRecebida) {
            limparJanela();
            e = new FaturaRecebidaBV((FaturaRecebida) obj);
            if (e.getNotaRecebida().size() > 0 && e.getNotaRecebida() != null) {
                notaRecebidaList = e.getNotaRecebida();
            }
            buscaPessoaNota();
            if (e.getTitulo().size() > 0 && e.getTitulo() != null) {
                list = new ModelList<>(e.getTitulo());
            }
            try {
                SessionUtil.put(e.construirComID(), "fatura", FacesContext.getCurrentInstance());
            } catch (DadoInvalidoException ex) {
                ex.print();
            }
        } else if (obj instanceof Pessoa && cid.equals("pessoaID-search")) {
            e.setPessoa((Pessoa) obj);
        } else if (obj instanceof Pessoa && cid.equals("pessoaIDNota-search")) {
            pessoaNota = (Pessoa) obj;
            addPessoaSessao((Pessoa) obj);
        } else if (obj instanceof NotaRecebida) {
            addNotaRecebidaNaLista((NotaRecebida) obj);
        } else if (obj instanceof Titulo) {
            Titulo cb = (Titulo) obj;
            list.add(cb);
        } else if (obj instanceof List<?> && "abreDialogoCotacao-btn".equals(cid)) {
            valorPorCotacaoList = (List<ValorPorCotacao>) obj;
            if (e.getId() != null) {
                update();
            } else {
                add();
            }
        } else if (obj instanceof Model) {
            Model m = (Model) obj;
            list.set(m);
            modeloSelecionado = null;
        }
    }

    private void buscaPessoaNota() {
        for (NotaRecebida n : e.getNotaRecebida()) {
            pessoaNota = n.getPessoa();
            addPessoaSessao(n.getPessoa());
            break;
        }
    }

    private void addNotaRecebidaNaLista(NotaRecebida n) {
        if (!notaRecebidaList.contains(n)) {
            notaRecebidaList.add(n);
        }
    }

    public void removerNotaDaLista() {
        if (notaRecebidaSelecionada != null) {
            notaRecebidaList.remove(notaRecebidaSelecionada);
            if (e.getId() != null) {
                notaRecebidaRemovidas.add(notaRecebidaSelecionada);
            }
        }
    }

    public boolean habilitaBotaoPessoa() {
        if (notaRecebidaList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void addPessoaSessao(Pessoa ps) {
        try {
            SessionUtil.put(ps, "pessoaFaturaRecebida", FacesContext.getCurrentInstance());
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void removePessoaSessao() {
        try {
            SessionUtil.remove("pessoaFaturaRecebida", FacesContext.getCurrentInstance());
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
            SessionUtil.put(e.construir(), "fatura", FacesContext.getCurrentInstance());
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void limparJanela() {
        try {
            removeDaSessao();
            SessionUtil.remove("fatura", FacesContext.getCurrentInstance());
            e = new FaturaRecebidaBV();
            e.setFilial((Filial) SessionUtil.getObject("filial", FacesContext.getCurrentInstance()));
            modeloSelecionado = null;
            list = new ModelList<>();
            notaRecebidaList = new ArrayList<>();
            notaRecebidaRemovidas = new ArrayList<>();
            valorPorCotacaoList = new ArrayList<>();
            notaRecebidaSelecionada = null;
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
        if (notaRecebidaList != null) {
            BigDecimal valor = notaRecebidaList.stream().map(NotaRecebida::getaFaturar).reduce(BigDecimal.ZERO, BigDecimal::add);
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

    public NotaRecebida getNotaRecebidaSelecionada() {
        return notaRecebidaSelecionada;
    }

    public void setNotaRecebidaSelecionada(NotaRecebida notaRecebidaSelecionada) {
        this.notaRecebidaSelecionada = notaRecebidaSelecionada;
    }

    public List<NotaRecebida> getNotaRecebidaList() {
        return notaRecebidaList;
    }

    public void setNotaRecebidaList(List<NotaRecebida> notaRecebidaList) {
        this.notaRecebidaList = notaRecebidaList;
    }

    public Pessoa getPessoaNota() {
        return pessoaNota;
    }

    public void setPessoaNota(Pessoa pessoaNota) {
        this.pessoaNota = pessoaNota;
    }
}
