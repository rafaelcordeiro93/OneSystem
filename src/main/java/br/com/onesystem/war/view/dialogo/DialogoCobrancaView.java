package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Fatura;
import br.com.onesystem.domain.FaturaEmitida;
import br.com.onesystem.domain.FaturaLegada;
import br.com.onesystem.domain.FaturaRecebida;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCartao;
import br.com.onesystem.valueobjects.EstadoDeCheque;
import br.com.onesystem.valueobjects.SituacaoDeCobranca;
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
    private Fatura fatura;
    private ConhecimentoDeFrete conhecimentoDeFrete;

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
        fatura = (Fatura) SessionUtil.getObject("fatura", FacesContext.getCurrentInstance());
        if(model == null){
            e.setFilial((Filial) SessionUtil.getObject("filial", FacesContext.getCurrentInstance()));
            e.setParcela((Integer) SessionUtil.getObject("parcela", FacesContext.getCurrentInstance()));
        }
        if (model != null && fatura != null) {
            cobranca = (Cobranca) model.getObject();
            e = new CobrancaBV(cobranca);
            cotacaoLista = new CotacaoDAO().naEmissao(fatura.getEmissao()).listaDeResultados();
            return;
        }
        if (fatura != null) {
            cotacaoLista = new CotacaoDAO().naEmissao(fatura.getEmissao()).listaDeResultados();
            e.setOperacaoFinanceira(OperacaoFinanceira.ENTRADA);
            e.setCotacao(new CotacaoDAO().porMoeda(fatura.getMoedaPadrao()).porCotacaoEmpresa().naMaiorEmissao(fatura.getEmissao()).resultado());
            e.setTipoLancamento(TipoLancamento.EMITIDA);
            e.setMoeda(fatura.getMoedaPadrao());
            e.setFatura(fatura);
            e.setPessoa(fatura.getPessoa());
            modalidade = true;
            return;
        }
        conhecimentoDeFrete = (ConhecimentoDeFrete) SessionUtil.getObject("conhecimentoDeFrete", FacesContext.getCurrentInstance());
        if (model != null && conhecimentoDeFrete != null) {
            cobranca = (Cobranca) model.getObject();
            e = new CobrancaBV(cobranca);
            cotacaoLista = new CotacaoDAO().naEmissao(conhecimentoDeFrete.getEmissao()).listaDeResultados();
            return;
        }
        if (conhecimentoDeFrete != null) {
            cotacaoLista = new CotacaoDAO().naEmissao(conhecimentoDeFrete.getEmissao()).listaDeResultados();
            e.setOperacaoFinanceira(OperacaoFinanceira.SAIDA);
            e.setCotacao(new CotacaoDAO().porMoeda(conhecimentoDeFrete.getMoedaPadrao()).porCotacaoEmpresa().naMaiorEmissao(conhecimentoDeFrete.getEmissao()).resultado());
            e.setTipoLancamento(TipoLancamento.RECEBIDA);
            e.setMoeda(conhecimentoDeFrete.getMoedaPadrao());
            e.setConhecimentoDeFrete(conhecimentoDeFrete);
            e.setPessoa(conhecimentoDeFrete.getPessoa());
            modalidade = true;
            return;
        }
        if (model != null) { //NOTA
            cobranca = (Cobranca) model.getObject();
            e = new CobrancaBV(cobranca);
            cotacaoLista = new CotacaoDAO().naEmissao(cobranca.getNota().getEmissao()).listaDeResultados();
            return;
        }
        nota = (Nota) SessionUtil.getObject("nota", FacesContext.getCurrentInstance());
        if (nota != null) {

            cotacaoLista = new CotacaoDAO().naEmissao(nota.getEmissao()).listaDeResultados();
            e.setOperacaoFinanceira(nota.getOperacao().getOperacaoFinanceira());
            e.setCotacao(new CotacaoDAO().porMoeda(nota.getMoedaPadrao()).porCotacaoEmpresa().naMaiorEmissao(nota.getEmissao()).resultado());
            e.setTipoLancamento(nota.getOperacao().getTipoNota());
            e.setMoeda(nota.getMoedaPadrao());
            e.setNota(nota);
            e.setPessoa(nota.getPessoa());
            e.setSituacaoDeCartao(SituacaoDeCartao.ABERTO);
            e.setSituacaoDeCheque(EstadoDeCheque.ABERTO);
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

        RequestContext.getCurrentInstance().openDialog("/dialogo/dialogoCobranca", opcoes, null);
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
        e.setSituacaoDeCobranca(SituacaoDeCobranca.ABERTO);
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
        SessionUtil.remove("parcela", FacesContext.getCurrentInstance());
        SessionUtil.remove("conhecimentoDeFrete", FacesContext.getCurrentInstance());
        SessionUtil.remove("faturaLegada", FacesContext.getCurrentInstance());
        SessionUtil.remove("faturaRecebida", FacesContext.getCurrentInstance());
        SessionUtil.remove("faturaEmitida", FacesContext.getCurrentInstance());
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
