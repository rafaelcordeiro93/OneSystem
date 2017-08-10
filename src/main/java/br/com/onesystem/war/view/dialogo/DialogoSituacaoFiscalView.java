package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.domain.Cfop;
import br.com.onesystem.domain.Estado;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.SituacaoFiscal;
import br.com.onesystem.domain.TabelaDeTributacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.TipoPessoa;
import br.com.onesystem.war.builder.SituacaoFiscalBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 * @author Rafael Fernando Rauber
 * @date 09/08/2017
 *
 * Classe utilizada manutenção de situação fiscal em diálogo.
 */
@Named
@ViewScoped
public class DialogoSituacaoFiscalView extends BasicMBImpl<SituacaoFiscal, SituacaoFiscalBV> implements Serializable {

    @PostConstruct
    public void init() {
        limparJanela();
        buscaDaSessao();
        RequestContext.getCurrentInstance().update("tempDialog");
    }

    @Override
    public void limparJanela() {
        t = null;
        e = new SituacaoFiscalBV();
    }

    /**
     * Este método deve buscar da sessão dados que são obrigatórios para a
     * criação da situação fiscal. Deve trazer a operação o grupo fiscal e a
     * próxima sequência de situação para a operação.
     */
    public void buscaDaSessao() {
        try {

            //Situação Fiscal
            SituacaoFiscal situacao = (SituacaoFiscal) SessionUtil.getObject("situacaoFiscal", FacesContext.getCurrentInstance());
            if (situacao != null) {
                t = situacao;
                e = new SituacaoFiscalBV(situacao);
            } else {
                //Operacao
                Operacao operacao = (Operacao) SessionUtil.getObject("operacao", FacesContext.getCurrentInstance());
                if (operacao != null) {
                    e.setOperacao(operacao);
                } else {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("operacao_not_null"));
                }

                //Grupo Fiscal
                GrupoFiscal grupoFiscal = (GrupoFiscal) SessionUtil.getObject("grupoFiscal", FacesContext.getCurrentInstance());
                if (grupoFiscal != null) {
                    e.setGrupoFiscal(grupoFiscal);
                } else {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("grupo_fiscal_not_null"));
                }

                //Sequência
                Integer sequencia = (Integer) SessionUtil.getObject("sequencia", FacesContext.getCurrentInstance());
                if (sequencia != null) {
                    e.setSequencia(sequencia);
                } else {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("sequencia_not_null"));
                }
            }

        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void abrirDialogo() {
        exibeNaTela();
    }

    private void exibeNaTela() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("resizable", false);
        opcoes.put("width", 400);
        opcoes.put("draggable", true);
        opcoes.put("height", 475);
        opcoes.put("closable", false);
        opcoes.put("contentWidth", "100%");
        opcoes.put("contentHeight", "100%");
        opcoes.put("headerElement", "customheader");

        RequestContext.getCurrentInstance().openDialog("/dialogo/dialogoSituacaoFiscal", opcoes, null);
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj != null) {
            if (obj instanceof Estado) {
                e.setEstado((Estado) obj);
            } else if (obj instanceof Cfop) {
                e.setCfop((Cfop) obj);
            } else if (obj instanceof TabelaDeTributacao) {
                e.setTabelaDeTributacao((TabelaDeTributacao) obj);
            }
        }
    }

    public List<TipoPessoa> getTiposPessoa() {
        return Arrays.asList(TipoPessoa.values());
    }

    public void add() {
        try {

            removeDaSessao();

            SituacaoFiscal situacao = e.construir();
            addNoBanco(situacao);
            RequestContext.getCurrentInstance().closeDialog(situacao);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void atualizar() {
        try {

            removeDaSessao();

            SituacaoFiscal situacao = e.construirComID();
            updateNoBanco(situacao);
            RequestContext.getCurrentInstance().closeDialog(situacao);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void fechar() {
        limparJanela();
        RequestContext.getCurrentInstance().closeDialog(null);
    }

    private void removeDaSessao() throws FDadoInvalidoException {
        SessionUtil.remove("grupoFiscal", FacesContext.getCurrentInstance());
        SessionUtil.remove("operacao", FacesContext.getCurrentInstance());
        SessionUtil.remove("sequencia", FacesContext.getCurrentInstance());
        SessionUtil.remove("situacaoFiscal", FacesContext.getCurrentInstance());
    }

}
