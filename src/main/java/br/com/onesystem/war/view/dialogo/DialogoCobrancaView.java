package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.FaturaEmitida;
import br.com.onesystem.domain.FaturaLegada;
import br.com.onesystem.domain.FaturaRecebida;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCartao;
import br.com.onesystem.valueobjects.SituacaoDeCheque;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.war.builder.CobrancaBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class DialogoCobrancaView extends BasicMBImpl<Cobranca, CobrancaBV> implements Serializable {

    private boolean modalidade = true;
    private Cobranca cobranca;
    private List<Cotacao> cotacaoLista;
    private Nota nota;
    private FaturaLegada faturaLegada;
    private FaturaEmitida faturaEmitida;
    private FaturaRecebida faturaRecebida;

    private Model<Cobranca> model;

    @Inject

    @PostConstruct
    public void init() {
        try {
            limparJanela();
            buscaDaSessao();
        } catch (EDadoInvalidoException die) {
            die.print();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private void buscaDaSessao() throws DadoInvalidoException {
        model = (Model<Cobranca>) SessionUtil.getObject("model", FacesContext.getCurrentInstance());
        faturaLegada = (FaturaLegada) SessionUtil.getObject("faturaLegada", FacesContext.getCurrentInstance());
        faturaEmitida = (FaturaEmitida) SessionUtil.getObject("faturaEmitida", FacesContext.getCurrentInstance());
        faturaRecebida = (FaturaRecebida) SessionUtil.getObject("faturaRecebida", FacesContext.getCurrentInstance());

        if (model != null && faturaLegada != null) {
            cobranca = (Cobranca) model.getObject();
            e = new CobrancaBV(cobranca);
            cotacaoLista = new CotacaoDAO().naEmissao(faturaLegada.getEmissao()).listaDeResultados();
            return;
        }
        if (model != null && faturaEmitida != null) {
            cobranca = (Cobranca) model.getObject();
            e = new CobrancaBV(cobranca);
            cotacaoLista = new CotacaoDAO().naEmissao(faturaEmitida.getEmissao()).listaDeResultados();
            return;
        }
        if (model != null && faturaRecebida != null) {
            cobranca = (Cobranca) model.getObject();
            e = new CobrancaBV(cobranca);
            cotacaoLista = new CotacaoDAO().naEmissao(faturaRecebida.getEmissao()).listaDeResultados();
            return;
        }
        if (faturaLegada != null) {
            cotacaoLista = new CotacaoDAO().naEmissao(faturaLegada.getEmissao()).listaDeResultados();
            e.setOperacaoFinanceira(OperacaoFinanceira.ENTRADA);
            e.setCotacao(new CotacaoDAO().porMoeda(faturaLegada.getMoedaPadrao()).porCotacaoEmpresa().naMaiorEmissao(faturaLegada.getEmissao()).resultado());
            e.setTipoLancamento(TipoLancamento.EMITIDA);
            e.setMoeda(faturaLegada.getMoedaPadrao());
            e.setFaturaLegada(faturaLegada);
            e.setPessoa(faturaLegada.getPessoa());
            modalidade = true;
            return;
        }
        if (faturaEmitida != null) {
            cotacaoLista = new CotacaoDAO().naEmissao(faturaEmitida.getEmissao()).listaDeResultados();
            e.setOperacaoFinanceira(OperacaoFinanceira.ENTRADA);
            e.setCotacao(new CotacaoDAO().porMoeda(faturaEmitida.getMoedaPadrao()).porCotacaoEmpresa().naMaiorEmissao(faturaEmitida.getEmissao()).resultado());
            e.setTipoLancamento(TipoLancamento.EMITIDA);
            e.setMoeda(faturaEmitida.getMoedaPadrao());
            e.setFaturaEmitida(faturaEmitida);
            e.setPessoa(faturaEmitida.getPessoa());
            modalidade = true;
            return;
        }
        if (faturaRecebida != null) {
            cotacaoLista = new CotacaoDAO().naEmissao(faturaRecebida.getEmissao()).listaDeResultados();
            e.setOperacaoFinanceira(OperacaoFinanceira.SAIDA);
            e.setCotacao(new CotacaoDAO().porMoeda(faturaRecebida.getMoedaPadrao()).porCotacaoEmpresa().naMaiorEmissao(faturaRecebida.getEmissao()).resultado());
            e.setTipoLancamento(TipoLancamento.RECEBIDA);
            e.setMoeda(faturaRecebida.getMoedaPadrao());
            e.setFaturaRecebida(faturaRecebida);
            e.setPessoa(faturaRecebida.getPessoa());
            modalidade = true;
            return;
        }
        if (model != null) { //NOTA
            cobranca = (Cobranca) model.getObject();
            e = new CobrancaBV(cobranca);
            cotacaoLista = new CotacaoDAO().naEmissao(cobranca.getNota().getEmissao()).listaDeResultados();
            return;
        } else {
            nota = (Nota) SessionUtil.getObject("nota", FacesContext.getCurrentInstance());
            cotacaoLista = new CotacaoDAO().naEmissao(nota.getEmissao()).listaDeResultados();
            e.setOperacaoFinanceira(nota.getOperacao().getOperacaoFinanceira());
            e.setCotacao(new CotacaoDAO().porMoeda(nota.getMoedaPadrao()).porCotacaoEmpresa().naMaiorEmissao(nota.getEmissao()).resultado());
            e.setTipoLancamento(nota.getOperacao().getTipoNota());
            e.setMoeda(nota.getMoedaPadrao());
            e.setNota(nota);
            e.setPessoa(nota.getPessoa());
            e.setSituacaoDeCartao(SituacaoDeCartao.ABERTO);
            e.setSituacaoDeCheque(SituacaoDeCheque.ABERTO);
            modalidade = false;
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
        opcoes.put("height", 525);
        opcoes.put("closable", true);
        opcoes.put("contentWidth", "100%");
        opcoes.put("contentHeight", "100%");
        opcoes.put("headerElement", "customheader");

        RequestContext.getCurrentInstance().openDialog("dialogo/dialogoCobranca", opcoes, null);
    }

    public List<ModalidadeDeCobranca> getModalidadesDeCobranca() {
        return Arrays.asList(ModalidadeDeCobranca.values());
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Cartao) {
            e.setCartao((Cartao) obj);
        } else if (obj instanceof Banco) {
            e.setBanco((Banco) obj);
        }
    }

    public void salvar() {
        try {
            removeDaSessao();
            Cobranca c = constroi();
            if (model != null) {
                model.setObject(c);
                RequestContext.getCurrentInstance().closeDialog(model);
            } else {
                RequestContext.getCurrentInstance().closeDialog(c);
            }
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private Cobranca constroi() throws DadoInvalidoException {
        Cobranca c = null;
        switch (e.getModalidadeDeCobranca()) {
            case CARTAO:
                c = e.construirBoletoDeCartaoComId();
                break;
            case CHEQUE:
                c = e.construirChequeComID();
                break;
            case CREDITO:
                e.setEntrada(true);
                c = e.construirCreditoComID();
                break;
            case TITULO:
                c = e.construirTituloComID();
                break;
        }
        return c;
    }

    private void removeDaSessao() throws FDadoInvalidoException {
        SessionUtil.remove("model", FacesContext.getCurrentInstance());
    }

    @Override
    public void limparJanela() {
        e = new CobrancaBV();
        e.setModalidadeDeCobranca(ModalidadeDeCobranca.TITULO);
        cobranca = null;
    }

    public boolean isModalidade() {
        return modalidade;
    }

    public void setModalidade(boolean modalidade) {
        this.modalidade = modalidade;
    }

    public List<Cotacao> getCotacaoLista() {
        return cotacaoLista;
    }

    public void setCotacaoLista(List<Cotacao> cotacaoLista) {
        this.cotacaoLista = cotacaoLista;
    }

}
