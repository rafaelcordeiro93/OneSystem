/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.reportTemplate.ValorPorCotacaoBV;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ModelList;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.MoedaFomatter;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.war.builder.NotaEmitidaBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class DesdobramentoDeVendaView extends BasicMBImpl<NotaEmitida, NotaEmitidaBV> implements Serializable {

    private Model<Cobranca> modelo;
    private ModelList<Cobranca> list;
    private NotaEmitidaBV notaBV;
    private NotaEmitida nota;
    

    @PostConstruct
    public void init() {
        limparJanela();
    }

//        // -------------- Operações para criação da entidade ----------------------   
//    public void validaAFaturar() throws DadoInvalidoException {
//        nota = notaBV.construir();
//        // Se valor a faturar maior que zero deve exibir diálogo de confirmação
//        if (notaBV.getAFaturar() != null && notaBV.getAFaturar().compareTo(BigDecimal.ZERO) > 0) {
//            RequestContext c = RequestContext.getCurrentInstance();
//            c.execute("PF('existeValorAFaturar').show()");
//        } else if (notaBV.getAFaturar() != null && notaBV.getAFaturar().compareTo(BigDecimal.ZERO) < 0) {
//            throw new EDadoInvalidoException(new BundleUtil().getMessage("Valor_A_Faturar_Menor_Que_Zero"));
//        } else {
//            validaDinheiro();
//        }
//    }
//
//    public void validaDinheiro() {
//        // Se existir valor em dinheiro abre a janela de cotações.
//        if (notaBV.getTotalEmDinheiro() != null && notaBV.getTotalEmDinheiro().compareTo(BigDecimal.ZERO) > 0) {
//            recalculaCotacoes(); // abre a janela de cotações
//        } else {
//            geraBoletoDeCartaoAVista();
//        }
//    }
//    
//        /**
//     * Busca a moeda padrão e abre a janela de cotações com o valor restante na
//     * cotação de cada moeda.
//     */
//    public void recalculaCotacoes() {
//        RequestContext rc = RequestContext.getCurrentInstance();
//        rc.update("cotacaoVal");
//        for (ValorPorCotacaoBV c : cotacoes) {
//            c.setTotal(notaBV.getTotalEmDinheiro());
//            c.setTotalConvertidoRecebido(getTotalConvertidoRecebido());
//        }
//        rc.update("conteudo:cotacaoValoresData");
//        rc.execute("PF('cotacaoVal').show()");
//    }
//
//    /**
//     * Gera as baixas para o recebimento do valor a vista.
//     *
//     * @throws br.com.onesystem.exception.DadoInvalidoException
//     */
//    public void geraBaixaDeCotacoes() throws DadoInvalidoException {
//        for (ValorPorCotacaoBV c : cotacoes) {
//            if (c.getValorAReceber().compareTo(BigDecimal.ZERO) > 0) {
//                nota.adiciona(c.construir());
//            }
//        }
//        geraBoletoDeCartaoAVista();
//    }
//    
//        public BigDecimal getTotalConvertidoRecebido() {
//        BigDecimal total = BigDecimal.ZERO;
//        for (ValorPorCotacaoBV c : cotacoes) {
//            total = total.add(c.getValorConvertidoRecebido());
//        }
//        return total;
//    }
//
//    public String getTotalConvertidoRecebidoFormatado() {
//        return NumberFormat.getCurrencyInstance(configuracao.getMoedaPadrao().getBandeira().getLocal()).format(getTotalConvertidoRecebido());
//    }
//    
    
    @Override
    public void update() {
        list.getList().forEach(System.out::println);

        List<Cobranca> removidos = list.getRemovidos().stream().filter(m -> ((Cobranca) m.getObject()).getId() != null).map(m -> (Cobranca) m.getObject()).collect(Collectors.toList());
        removidos.forEach(System.out::println);
    }

    
    public void selecionar(SelectEvent event) {
        try {
            Object obj = event.getObject();
            String idComponent = event.getComponent().getId();
            if (obj instanceof NotaEmitida && "consultaDesdNotaEmitida-search".equals(idComponent)) {
                this.nota = (NotaEmitida) obj;
                this.notaBV = new NotaEmitidaBV(nota);
                removeDaSessao();
                list = new ModelList<>(nota.getCobrancas());
            } else if (obj instanceof Cobranca) {
                Cobranca cb = (Cobranca) obj;
                list.add(cb);
            } else if (obj instanceof Model) {
                Model m = (Model) obj;
                list.atualiza(m);
                modelo = null;
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void remover() {
        list.remove(modelo);
        modelo = null;
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
        SessionUtil.remove("nota", FacesContext.getCurrentInstance());
        SessionUtil.put(nota, "nota", FacesContext.getCurrentInstance());
    }

    public void limparJanela() {
        nota = null;
    }

    public String getTotalParcelas() {
        if (list != null) {
            BigDecimal valor = list.getList().stream().map(Cobranca::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
            return MoedaFomatter.format(nota.getMoedaPadrao(), valor);
        }
        return "";
    }

    public NotaEmitida getNota() {
        return nota;
    }

    public void setNota(NotaEmitida nota) {
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

}
