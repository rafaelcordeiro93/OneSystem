/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.MoedaFomatter;
import br.com.onesystem.valueobjects.EstadoDeOrcamento;
import br.com.onesystem.war.builder.OrcamentoBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author rauber
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ConsultaOrcamentoView extends BasicMBImpl<Orcamento, OrcamentoBV> implements Serializable {

    @Inject
    private BundleUtil msg;
    private String historico;
    private EstadoDeOrcamento estadoDesejado;

    private Orcamento orcamento;

    @PostConstruct
    public void construir() {
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Orcamento) {
            orcamento = (Orcamento) obj;
        }
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public String getZero() {
        if (orcamento != null) {
            return MoedaFomatter.format(orcamento.getCotacao().getConta().getMoeda(), BigDecimal.ZERO);
        } else {
            return "";
        }
    }

    public void aprova() {
        estadoDesejado = EstadoDeOrcamento.APROVADO;
        RequestContext.getCurrentInstance().execute("PF('historicoDeOrcamento').show()");
    }

    public void redefinir() {
        estadoDesejado = EstadoDeOrcamento.EM_DEFINICAO;
        RequestContext.getCurrentInstance().execute("PF('historicoDeOrcamento').show()");
    }

    public void reprova() {
        estadoDesejado = EstadoDeOrcamento.REPROVADO;
        RequestContext.getCurrentInstance().execute("PF('historicoDeOrcamento').show()");
    }

    public void cancela() {
        estadoDesejado = EstadoDeOrcamento.CANCELADO;
        RequestContext.getCurrentInstance().execute("PF('historicoDeOrcamento').show()");
    }

    public void efetiva() {
        estadoDesejado = EstadoDeOrcamento.EFETIVADO;
        RequestContext.getCurrentInstance().execute("PF('historicoDeOrcamento').show()");
    }

    public void enviaParaAprovacao() {
        estadoDesejado = EstadoDeOrcamento.EM_APROVACAO;
        RequestContext.getCurrentInstance().execute("PF('historicoDeOrcamento').show()");
    }

    public void gravar() {
        try {
            switch (estadoDesejado) {
                case APROVADO:
                    orcamento.aprova(historico);
                    break;
                case REPROVADO:
                    orcamento.reprova(historico);
                    break;
                case EFETIVADO:
                    orcamento.efetiva(historico);
                    break;
                case CANCELADO:
                    orcamento.cancela(historico);
                    break;
                case EM_APROVACAO:
                    orcamento.enviaParaAprovacao(historico);
                    break;
                case EM_DEFINICAO:
                    orcamento.redefinir(historico);
            }
            new AtualizaDAO<>().atualiza(orcamento);
            InfoMessage.atualizado();
            limparHistorico();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void limparJanela() {
        orcamento = null;
        limparHistorico();
    }

    private void limparHistorico() {
        estadoDesejado = null;
        historico = "";
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public EstadoDeOrcamento getEstadoDesejado() {
        return estadoDesejado;
    }

    public void setEstadoDesejado(EstadoDeOrcamento estadoDesejado) {
        this.estadoDesejado = estadoDesejado;
    }
}
