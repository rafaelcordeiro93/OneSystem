package br.com.onesystem.war.view;

import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.CobrancaFixa;
import br.com.onesystem.domain.CobrancaVariavel;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.FormaDeCobranca;
import br.com.onesystem.domain.LayoutDeImpressao;
import br.com.onesystem.domain.Recebimento;
import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.domain.ValorPorCotacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.services.GeradorDeBaixas;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ImpressoraDeLayout;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.ModelList;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import br.com.onesystem.valueobjects.TipoImpressao;
import br.com.onesystem.valueobjects.TipoLayout;
import br.com.onesystem.war.builder.FormaDeCobrancaBV;
import br.com.onesystem.war.builder.RecebimentoBV;
import br.com.onesystem.war.builder.TipoDeCobrancaBV;
import br.com.onesystem.war.service.CotacaoService;
import br.com.onesystem.war.service.LayoutDeImpressaoService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class RecebimentoView extends BasicMBImpl<Recebimento, RecebimentoBV> implements Serializable {

    private Model tipoSelecionado;
    private Model formaSelecionado;
    private ModelList<TipoDeCobranca> tiposDeCobranca;
    private ModelList<FormaDeCobranca> formasDeCobranca;
    private TipoDeCobrancaBV tipoDeCobrancaBV;
    private FormaDeCobrancaBV formaDeCobrancaBV;
    private LayoutDeImpressao layoutDeImpressao;
    private List<ValorPorCotacao> valorPorCotacao;

    @Inject
    private CotacaoService service;

    @Inject
    private LayoutDeImpressaoService layoutService;
    
    @Inject
    private GeradorDeBaixas geradorDeBaixas;
    
    public void validaDinheiro() throws DadoInvalidoException {
        e.setTotalEmDinheiro(getTotalEmDinheiro());
        if (e.getTotalEmDinheiro() != null && e.getTotalEmDinheiro().compareTo(BigDecimal.ZERO) != 0) {
            SessionUtil.put(e.construir(), "movimento", FacesContext.getCurrentInstance());
            RequestContext.getCurrentInstance().execute("document.getElementById(\"conteudo:abreDialogoCotacao-btn\").click();");
        } else {
            receber();
        }
    }

    public void receber() {
        try {
            Recebimento recebimento = constroiRecebimento();
            addNoBanco(recebimento);
            t = recebimento;
            layoutDeImpressao = layoutService.getLayoutPorTipoDeLayout(TipoLayout.RECEBIMENTO);
            if (!layoutDeImpressao.getTipoImpressao().equals(TipoImpressao.NADA_A_FAZER)) {
                RequestContext.getCurrentInstance().execute("document.getElementById('conteudo:imprimir').click()"); // chama a impressao da nota
            }
        } catch (DadoInvalidoException die) {
            die.print();
            limparJanela();
        }
    }

    /**
     * Constroi Recebimento adicionando tipos,formas e valor por cotação. Após
     * contrução gera as baixas e valida o objeto.
     *
     * @return retorna o recebimento, construído e validado.
     */
    private Recebimento constroiRecebimento() throws DadoInvalidoException {
        Recebimento recebimento = e.construirComID(); // Constroi Recebimento **throws DadoInvalidoException
        tiposDeCobranca.getList().forEach(tp -> recebimento.adiciona(tp)); // Adiciona Tipos
        formasDeCobranca.getList().forEach(f -> recebimento.adiciona(f)); // Adiciona Forma
        for (ValorPorCotacao v : valorPorCotacao) { //Adiciona Valor por cotação **throws DadoInvalidoException
            recebimento.adiciona(v);
        }

        geradorDeBaixas.geraBaixasDe(recebimento); //Gera as baixas de recebimento
        recebimento.ehRegistroValido();// Valida **throws DadoInvalidoException
        return recebimento;
    }

    public void imprimir() {
        try {
            new ImpressoraDeLayout(t.getTipoDeCobranca(), layoutDeImpressao).addParametro("recebimento", t).visualizarPDF();
            t = null;
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void limparJanela() {
        try {
            removeDaSessao();
            e = new RecebimentoBV(new Date(), (Caixa) SessionUtil.getObject("caixa", FacesContext.getCurrentInstance()),
                    (Filial) SessionUtil.getObject("filial", FacesContext.getCurrentInstance()));
            e.setCotacaoPadrao(service.getCotacaoPadrao(e.getEmissao()));
            tiposDeCobranca = new ModelList<>();
            formasDeCobranca = new ModelList<>();
            valorPorCotacao = new ArrayList<>();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void removeDaSessao() throws FDadoInvalidoException {
        SessionUtil.remove("model", FacesContext.getCurrentInstance());
        SessionUtil.remove("emissao", FacesContext.getCurrentInstance());
        SessionUtil.remove("modalidadeDeCobranca", FacesContext.getCurrentInstance());
        SessionUtil.remove("modalidadeDeCobrancaFixa", FacesContext.getCurrentInstance());
        SessionUtil.remove("naturezaFinanceira", FacesContext.getCurrentInstance());
    }

    public void atualizaEmissao() throws DadoInvalidoException {
        e.setCotacaoPadrao(service.getCotacaoPadrao(e.getEmissao()));
    }

    @Override
    public void selecionar(SelectEvent event) {
        try {
            Object obj = event.getObject();
            String componentId = event.getComponent().getId();
            if (obj instanceof TipoDeCobranca) {
                TipoDeCobranca tipo = (TipoDeCobranca) obj;
                boolean possuiCobranca = false;
                if (tipo.getCobranca() != null) {
                    possuiCobranca = tiposDeCobranca.getList().stream().filter(tp -> tp.getCobranca() != null && tp.getCobranca() instanceof CobrancaVariavel).filter(tp -> tp.getCobranca().getId() != null).map(TipoDeCobranca::getCobranca).anyMatch(co -> co.getId().equals(tipo.getCobranca().getId()));
                } else {
                    possuiCobranca = tiposDeCobranca.getList().stream().filter(tp -> tp.getCobranca() != null && tp.getCobranca() instanceof CobrancaFixa).filter(tp -> tp.getCobranca().getId() != null).map(TipoDeCobranca::getCobranca).anyMatch(co -> co.getId().equals(tipo.getCobranca().getId()));
                }
                if (!possuiCobranca) {
                    tipo.setMovimento(e.construirComID());
                    tiposDeCobranca.add(tipo);
                } else {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("Tipo_Cobranca_Já_Está_Informado"));
                }
            } else if (obj instanceof FormaDeCobranca) {
                FormaDeCobranca forma = (FormaDeCobranca) obj;
                boolean possuiCobranca = formasDeCobranca.getList().stream().filter(tp -> tp.getCobranca() != null).filter(tp -> tp.getCobranca().getId() != null).map(FormaDeCobranca::getCobranca).anyMatch(co -> co.getId().equals(forma.getCobranca().getId()));
                if (!possuiCobranca) {
                    formasDeCobranca.add(forma);
                } else {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("Forma_Cobranca_Já_Está_Informada"));
                }
            } else if (obj instanceof Model) {
                Model model = (Model) obj;
                if (model.getObject() instanceof TipoDeCobranca) {
                    tiposDeCobranca.set(model);
                } else {
                    formasDeCobranca.set(model);
                }
            } else if (obj instanceof List<?> && "abreDialogoCotacao-btn".equals(componentId)) {
                valorPorCotacao = (List<ValorPorCotacao>) obj;
                receber();
            }
            tipoSelecionado = null;
            formaSelecionado = null;
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void abreTitulo() throws DadoInvalidoException {
        SessionUtil.remove("modalidadeDeCobranca", FacesContext.getCurrentInstance());
        SessionUtil.put(ModalidadeDeCobranca.TITULO, "modalidadeDeCobranca", FacesContext.getCurrentInstance());
        adicionaEmissaoNaSessao();
    }

    public void abreCheque() throws DadoInvalidoException {
        SessionUtil.remove("modalidadeDeCobranca", FacesContext.getCurrentInstance());
        SessionUtil.put(ModalidadeDeCobranca.CHEQUE, "modalidadeDeCobranca", FacesContext.getCurrentInstance());
        adicionaEmissaoNaSessao();
    }

    public void abreCartao() throws DadoInvalidoException {
        SessionUtil.remove("modalidadeDeCobranca", FacesContext.getCurrentInstance());
        SessionUtil.put(ModalidadeDeCobranca.CARTAO, "modalidadeDeCobranca", FacesContext.getCurrentInstance());
        adicionaEmissaoNaSessao();
    }

    public void abreCredito() throws DadoInvalidoException {
        SessionUtil.remove("modalidadeDeCobranca", FacesContext.getCurrentInstance());
        SessionUtil.put(ModalidadeDeCobranca.CREDITO, "modalidadeDeCobranca", FacesContext.getCurrentInstance());
        adicionaEmissaoNaSessao();
    }

    public void abreReceitaProvisionada() throws DadoInvalidoException {
        SessionUtil.remove("modalidadeDeCobrancaFixa", FacesContext.getCurrentInstance());
        SessionUtil.put(ModalidadeDeCobranca.RECEITA_PROVISIONADA, "modalidadeDeCobrancaFixa", FacesContext.getCurrentInstance());
        adicionaEmissaoNaSessao();
    }

    public void abreReceitaEventual() throws DadoInvalidoException {
        SessionUtil.remove("modalidadeDeCobrancaFixa", FacesContext.getCurrentInstance());
        SessionUtil.put(ModalidadeDeCobranca.RECEITA_EVENTUAL, "modalidadeDeCobrancaFixa", FacesContext.getCurrentInstance());
        adicionaEmissaoNaSessao();
    }

    public void adicionaEmissaoNaSessao() throws DadoInvalidoException {
        SessionUtil.remove("naturezaFinanceira", FacesContext.getCurrentInstance());
        SessionUtil.put(NaturezaFinanceira.RECEITA, "naturezaFinanceira", FacesContext.getCurrentInstance());
        SessionUtil.remove("emissao", FacesContext.getCurrentInstance());
        SessionUtil.put(e.getEmissao(), "emissao", FacesContext.getCurrentInstance());
    }

    public void adicionaTipoNaSessao(SelectEvent event) throws DadoInvalidoException {
        tipoSelecionado = (Model) event.getObject();
        if (tipoSelecionado != null) {
            SessionUtil.remove("modelTipo", FacesContext.getCurrentInstance());
            SessionUtil.put(tipoSelecionado, "modelTipo", FacesContext.getCurrentInstance());
        }
    }

    public void adicionaFormaNaSessao(SelectEvent event) throws DadoInvalidoException {
        formaSelecionado = (Model) event.getObject();
        if (formaSelecionado != null) {
            SessionUtil.remove("modelForma", FacesContext.getCurrentInstance());
            SessionUtil.put(formaSelecionado, "modelForma", FacesContext.getCurrentInstance());
        }
    }

    public void removeTipo() throws FDadoInvalidoException {
        if (tipoSelecionado != null) {
            SessionUtil.remove("model", FacesContext.getCurrentInstance());
            tiposDeCobranca.remove(tipoSelecionado);
        }
    }

    public void removeForma() throws FDadoInvalidoException {
        if (formaSelecionado != null) {
            SessionUtil.remove("model", FacesContext.getCurrentInstance());
            formasDeCobranca.remove(formaSelecionado);
        }
    }

    public BigDecimal getTotalTipoNaCotacaoPadrao() {
        BigDecimal total = BigDecimal.ZERO;
        try {
            for (TipoDeCobranca tp : tiposDeCobranca.getList()) {
                if (tp.getCotacao().getConta().getBanco() == null) {
                    if (tp.getCotacao() != null && tp.getCotacao() != e.getCotacaoPadrao()) {
                        total = total.add(tp.getValor().divide(tp.getCotacao().getValor(), 2, BigDecimal.ROUND_UP));
                    } else {
                        total = total.add(tp.getValor());
                    }
                }
            }
        } catch (NullPointerException npe) {
            return null;
        }
        return total.compareTo(BigDecimal.ZERO) == 0 ? null : total;
    }

    public String getTotalTipoNaCotacaoPadraoFormatado() {
        return MoedaFormatter.format(e.getCotacaoPadrao().getConta().getMoeda(), getTotalTipoNaCotacaoPadrao());
    }

    public BigDecimal getTotalFormaNaCotacaoPadrao() {
        BigDecimal total = BigDecimal.ZERO;
        try {
            for (FormaDeCobranca fm : formasDeCobranca.getList()) {
                if (fm.getCotacao().getConta().getBanco() == null) {
                    if (fm.getCotacao() != null && fm.getCotacao() != e.getCotacaoPadrao()) {
                        total = total.add(fm.getValor().divide(fm.getCotacao().getValor(), 2, BigDecimal.ROUND_UP));
                    } else {
                        total = total.add(fm.getValor());
                    }
                }
            }
        } catch (NullPointerException npe) {
            return null;
        }
        return total.compareTo(BigDecimal.ZERO) == 0 ? null : total;
    }

    public String getTotalFormaNaCotacaoPadraoFormatado() {
        return MoedaFormatter.format(e.getCotacaoPadrao().getConta().getMoeda(), getTotalFormaNaCotacaoPadrao());
    }

    public BigDecimal getValorEmConta() {
        BigDecimal total = BigDecimal.ZERO;
        try {
            for (TipoDeCobranca tp : tiposDeCobranca.getList()) {
                if (tp.getCotacao().getConta().getBanco() != null) {
                    if (tp.getCotacao() != null && tp.getCotacao() != e.getCotacaoPadrao()) {
                        total = total.add(tp.getValor().divide(tp.getCotacao().getValor(), 2, BigDecimal.ROUND_UP));
                    } else {
                        total = total.add(tp.getValor());
                    }
                }
            }
        } catch (NullPointerException npe) {
            return null;
        }
        return total;
    }

    public BigDecimal getTotalEmDinheiro() {
        BigDecimal totalTipo = getTotalTipoNaCotacaoPadrao() == null ? BigDecimal.ZERO : getTotalTipoNaCotacaoPadrao();
        BigDecimal totalForma = getTotalFormaNaCotacaoPadrao() == null ? BigDecimal.ZERO : getTotalFormaNaCotacaoPadrao();
        return totalTipo.subtract(totalForma);
    }

    public Model getTipoSelecionado() {
        return tipoSelecionado;
    }

    public void setTipoSelecionado(Model tipoSelecionado) {
        this.tipoSelecionado = tipoSelecionado;
    }

    public Model getFormaSelecionado() {
        return formaSelecionado;
    }

    public void setFormaSelecionado(Model formaSelecionado) {
        this.formaSelecionado = formaSelecionado;
    }

    public ModelList<TipoDeCobranca> getTiposDeCobranca() {
        return tiposDeCobranca;
    }

    public void setTiposDeCobranca(ModelList<TipoDeCobranca> tiposDeCobranca) {
        this.tiposDeCobranca = tiposDeCobranca;
    }

    public ModelList<FormaDeCobranca> getFormasDeCobranca() {
        return formasDeCobranca;
    }

    public void setFormasDeCobranca(ModelList<FormaDeCobranca> formasDeCobranca) {
        this.formasDeCobranca = formasDeCobranca;
    }

    public TipoDeCobrancaBV getTipoDeCobrancaBV() {
        return tipoDeCobrancaBV;
    }

    public void setTipoDeCobrancaBV(TipoDeCobrancaBV tipoDeCobrancaBV) {
        this.tipoDeCobrancaBV = tipoDeCobrancaBV;
    }

    public FormaDeCobrancaBV getFormaDeCobrancaBV() {
        return formaDeCobrancaBV;
    }

    public void setFormaDeCobrancaBV(FormaDeCobrancaBV formaDeCobrancaBV) {
        this.formaDeCobrancaBV = formaDeCobrancaBV;
    }

}
