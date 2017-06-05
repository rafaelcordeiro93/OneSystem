/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.NotaRecebida;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.ModelList;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.war.builder.NotaRecebidaBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
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
 * @author Rafael Fernando Rauber
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class RetificacaoDeCompraView extends BasicMBImpl<NotaRecebida, NotaRecebidaBV> implements Serializable {

    private Model<Cobranca> modelo;
    private ModelList<Cobranca> list;
    private NotaRecebidaBV notaBV;
    private NotaRecebida nota;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    // -------------- Operações para criação da entidade ----------------------   
    public void validaAFaturar() {
        try {
            nota = notaBV.construirComID();
            // Se valor a faturar maior que zero deve exibir diálogo de confirmação
            if (notaBV.getAFaturar() != null && notaBV.getAFaturar().compareTo(BigDecimal.ZERO) > 0) {
                RequestContext c = RequestContext.getCurrentInstance();
                c.execute("PF('existeValorAFaturar').show()");
            } else if (notaBV.getAFaturar() != null && notaBV.getAFaturar().compareTo(BigDecimal.ZERO) < 0) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Valor_A_Faturar_Menor_Que_Zero"));
            } else {
                validaDinheiro();
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void validaDinheiro() throws DadoInvalidoException {
        // Se existir valor em dinheiro abre a janela de cotações.
        if (notaBV.getTotalEmDinheiro() != null && notaBV.getTotalEmDinheiro().compareTo(BigDecimal.ZERO) > 0) {
            SessionUtil.remove("nota", FacesContext.getCurrentInstance());
            SessionUtil.put(nota, "nota", FacesContext.getCurrentInstance());
            RequestContext.getCurrentInstance().execute("document.getElementById(\"conteudo:abreDialogoCotacao-btn\").click();");
        }
    }

    /**
     * Calcula o valor de acréscimo e desconto após informar um dos campos de
     * porcentagem de acréscimo e desconto.
     */
    public void calculaValorAcrescimoEDesconto() {
        BigDecimal total = notaBV.getTotalItens();
        if (total.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal pAcrescimo = notaBV.getPorcentagemAcrescimo() == null ? BigDecimal.ZERO : notaBV.getPorcentagemAcrescimo();
            BigDecimal pDesconto = notaBV.getPorcentagemDesconto() == null ? BigDecimal.ZERO : notaBV.getPorcentagemDesconto();
            BigDecimal acrescimo;
            BigDecimal desconto;
            BigDecimal cem = new BigDecimal(100);

            if (pAcrescimo.compareTo(BigDecimal.ZERO) > 0) {
                acrescimo = (pAcrescimo.multiply(total)).divide(cem, 2, BigDecimal.ROUND_UP);
                notaBV.setAcrescimo(acrescimo);
            } else {
                notaBV.setAcrescimo(BigDecimal.ZERO);
            }
            if (pDesconto.compareTo(BigDecimal.ZERO) > 0) {
                desconto = (pDesconto.multiply((total))).divide(cem, 2, BigDecimal.ROUND_UP);
                notaBV.setDesconto(desconto);
            } else {
                notaBV.setDesconto(BigDecimal.ZERO);
            }
        }
    }

    /**
     * Calcula a porcentagem de acréscimo e desconto após informar um dos campos
     * de valor de acréscimo e desconto.
     */
    public void calculaPorcentagemAcrescimoEDesconto() {
        BigDecimal total = notaBV.getTotalItens();
        if (total.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal acrescimo = notaBV.getAcrescimo() == null ? BigDecimal.ZERO : notaBV.getAcrescimo();
            BigDecimal desconto = notaBV.getDesconto() == null ? BigDecimal.ZERO : notaBV.getDesconto();
            BigDecimal pAcrescimo;
            BigDecimal pDesconto;
            BigDecimal cem = new BigDecimal(100);

            if (acrescimo.compareTo(BigDecimal.ZERO) > 0) {
                pAcrescimo = (acrescimo.multiply(cem)).divide(total, 2, BigDecimal.ROUND_UP);
                notaBV.setPorcentagemAcrescimo(pAcrescimo);
            } else {
                notaBV.setPorcentagemAcrescimo(BigDecimal.ZERO);
            }
            if (desconto.compareTo(BigDecimal.ZERO) > 0) {
                pDesconto = (desconto.multiply(cem)).divide(((total)), 2, BigDecimal.ROUND_UP);
                notaBV.setPorcentagemDesconto(pDesconto);
            } else {
                notaBV.setPorcentagemDesconto(BigDecimal.ZERO);
            }
        }
    }

    public void recalculaValorAFaturar() {

        BigDecimal cobrancas = list.getList() == null ? BigDecimal.ZERO : list.getList().stream().map(Cobranca::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal dinheiro = notaBV.getTotalEmDinheiro() == null ? BigDecimal.ZERO : notaBV.getTotalEmDinheiro();
        BigDecimal soma = cobrancas.add(dinheiro);

        notaBV.setAFaturar(notaBV.getTotalNota().subtract(soma));
    }

    @Override
    public void update() {
        try {
            for (Cobranca c : list.getList()) {
                if (c.getId() != null) {
                    nota.atualiza(c);
                } else {
                    nota.adiciona(c);
                }
            }

            List<Cobranca> removidos = list.getRemovidos().stream().filter(m -> ((Cobranca) m.getObject()).getId() != null).map(m -> (Cobranca) m.getObject()).collect(Collectors.toList());
            removidos.forEach(c -> nota.remove(c));

            new AtualizaDAO<>().atualiza(nota);
            for (Cobranca c : removidos) {
                new RemoveDAO<>().remove(c, c.getId());
            }
            InfoMessage.atualizado();
            RequestContext.getCurrentInstance().update("conteudo");
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void selecionar(SelectEvent event) {
        try {
            Object obj = event.getObject();
            String idComponent = event.getComponent().getId();
            if (obj instanceof NotaRecebida && "consultaDesdNotaRecebida-search".equals(idComponent)) {
                this.nota = (NotaRecebida) obj;
                this.notaBV = new NotaRecebidaBV(nota);
                list = new ModelList<>(nota.getCobrancas());
                SessionUtil.put(nota, "nota", FacesContext.getCurrentInstance());
            }
            if (obj instanceof NotaRecebida && "abreDialogoCotacao-btn".equals(idComponent)) {
                this.nota = (NotaRecebida) obj;
                this.notaBV = new NotaRecebidaBV(nota);
                update();
            } else if (obj instanceof Cobranca) {
                Cobranca cb = (Cobranca) obj;
                list.add(cb);
                recalculaValorAFaturar();
            } else if (obj instanceof Model) {
                Model m = (Model) obj;
                list.atualiza(m);
                modelo = null;
                recalculaValorAFaturar();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void remover() {
        list.remove(modelo);
        modelo = null;
        recalculaValorAFaturar();
    }

    public void selecionaParcela(SelectEvent event) {
        try {
            modelo = (Model<Cobranca>) event.getObject();
            removeDaSessao();
            SessionUtil.put(modelo, "model", FacesContext.getCurrentInstance());
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void removeDaSessao() throws FDadoInvalidoException {
        SessionUtil.remove("model", FacesContext.getCurrentInstance());
    }

    public void limparJanela() {
        try {
            removeDaSessao();
            SessionUtil.remove("nota", FacesContext.getCurrentInstance());
            nota = null;
            modelo = null;
            list = null;
            notaBV = new NotaRecebidaBV();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public String getTotalParcelas() {
        if (list != null) {
            BigDecimal valor = list.getList().stream().map(Cobranca::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
            return MoedaFormatter.format(nota.getMoedaPadrao(), valor);
        }
        return "";
    }

    public NotaRecebida getNota() {
        return nota;
    }

    public void setNota(NotaRecebida nota) {
        this.nota = nota;
    }

    public ModelList<Cobranca> getList() {
        return list;
    }

    public void setList(ModelList<Cobranca> list) {
        this.list = list;
    }

    public Model getModelo() {
        return modelo;
    }

    public void setModelo(Model modelo) {
        this.modelo = modelo;
    }

    public NotaRecebidaBV getNotaBV() {
        return notaBV;
    }

    public void setNotaBV(NotaRecebidaBV notaBV) {
        this.notaBV = notaBV;
    }

}
