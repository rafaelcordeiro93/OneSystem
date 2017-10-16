package br.com.onesystem.war.view;

import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.CobrancaFixa;
import br.com.onesystem.domain.CobrancaVariavel;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.FormaDeCobranca;
import br.com.onesystem.domain.LayoutDeImpressao;
import br.com.onesystem.domain.Recebimento;
import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ImpressoraDeLayoutGrafico;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.ModelList;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.EstadoDeLancamento;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import br.com.onesystem.valueobjects.TipoLayout;
import br.com.onesystem.war.builder.FormaDeCobrancaBV;
import br.com.onesystem.war.builder.RecebimentoBV;
import br.com.onesystem.war.builder.TipoDeCobrancaBV;
import br.com.onesystem.war.service.CotacaoService;
import br.com.onesystem.war.service.LayoutDeImpressaoService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ConsultaRecebimentoView extends BasicMBImpl<Recebimento, RecebimentoBV> implements Serializable {

    private Model tipoSelecionado;
    private Model formaSelecionado;
    private ModelList<TipoDeCobranca> tiposDeCobranca;
    private ModelList<FormaDeCobranca> formasDeCobranca;
    private ModelList<TipoDeCobranca> tiposDeCobrancaDeletados;
    private ModelList<FormaDeCobranca> formasDeCobrancaDeletados;
    private TipoDeCobrancaBV tipoDeCobrancaBV;
    private FormaDeCobrancaBV formaDeCobrancaBV;

    @Inject
    private CotacaoService service;

    @Inject
    private LayoutDeImpressaoService layoutService;
    
    @PostConstruct
    public void init() {
        limparJanela();
    }

    @Override
    public void update() {
        try {
            e.setTotalEmDinheiro(getTotalEmDinheiro());
            Recebimento recebimento = e.construirComID();
            tiposDeCobranca.getList().forEach(tp -> recebimento.atualiza(tp));
            formasDeCobranca.getList().forEach(f -> recebimento.atualiza(f));

            tiposDeCobranca.getList().forEach(tp -> recebimento.atualizaBaixas(tp));
            formasDeCobranca.getList().forEach(f -> recebimento.atualizaBaixas(f));

            recebimento.ehRegistroValido();
            tiposDeCobrancaDeletados.getList().forEach(tp -> recebimento.remove(tp));
            formasDeCobrancaDeletados.getList().forEach(f -> recebimento.remove(f));

            updateNoBanco(recebimento);
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void cancela() throws DadoInvalidoException {
        try {
            Recebimento recebimento = e.construirComID();
            if (recebimento.getEstado() == EstadoDeLancamento.CANCELADO) {
                recebimento.efetiva();
                recebimento.efetivaBaixas();
                updateNoBanco(recebimento);
            } else {
                recebimento.cancela();
                updateNoBanco(recebimento);
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void delete() {
        try {
            Recebimento recebimento = e.construirComID();
            tiposDeCobranca.getList().forEach(tp -> recebimento.remove(tp));
            formasDeCobranca.getList().forEach(f -> recebimento.remove(f));
            deleteNoBanco(recebimento, recebimento.getId());
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void limparJanela() {
        try {
            e = new RecebimentoBV(new Date(), (Caixa) SessionUtil.getObject("caixa", FacesContext.getCurrentInstance()),
                    (Filial) SessionUtil.getObject("filial", FacesContext.getCurrentInstance()));
            e.setCotacaoPadrao(service.getCotacaoPadrao(e.getEmissao()));
            tiposDeCobranca = new ModelList<>();
            formasDeCobranca = new ModelList<>();
            tiposDeCobrancaDeletados = new ModelList<>();
            formasDeCobrancaDeletados = new ModelList<>();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void atualizaEmissao() throws DadoInvalidoException {
        e.setCotacaoPadrao(service.getCotacaoPadrao(e.getEmissao()));
    }
    
    public void imprimir() {
        try {
            if (t != null) {
                LayoutDeImpressao layout = layoutService.getLayoutPorTipoDeLayout(TipoLayout.RECEBIMENTO);
                new ImpressoraDeLayoutGrafico(t.getTipoDeCobranca(), layout).addParametro("recebimento", t).visualizarPDF();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Selecione_um_registro"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }


    @Override
    public void selecionar(SelectEvent event) {
        try {
            Object obj = event.getObject();
            if (obj instanceof Recebimento) {
                e = new RecebimentoBV((Recebimento) obj);
                t = (Recebimento) obj;
                tiposDeCobranca = new ModelList<>(e.getTiposDeCobranca());
                formasDeCobranca = new ModelList<>(e.getFormasDeCobranca());
            } else if (obj instanceof TipoDeCobranca) {
                TipoDeCobranca tipo = (TipoDeCobranca) obj;
                boolean possuiCobranca = false;
                if (tipo.getCobranca() != null) {
                    possuiCobranca = tiposDeCobranca.getList().stream().filter(tp -> tp.getCobranca() != null && tp.getCobranca() instanceof CobrancaVariavel).filter(tp -> tp.getCobranca().getId() != null).map(TipoDeCobranca::getCobranca).anyMatch(co -> co.getId().equals(tipo.getCobranca().getId()));
                } else {
                    possuiCobranca = tiposDeCobranca.getList().stream().filter(tp -> tp.getCobranca() != null && tp.getCobranca() instanceof CobrancaFixa).filter(tp -> tp.getCobranca().getId() != null).map(TipoDeCobranca::getCobranca).anyMatch(co -> co.getId().equals(tipo.getCobranca().getId()));
                }
                if (!possuiCobranca) {
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
            SessionUtil.remove("modelTipo", FacesContext.getCurrentInstance());
            tiposDeCobrancaDeletados.add((TipoDeCobranca) tipoSelecionado.getObject());
            tiposDeCobranca.remove(tipoSelecionado);
        }
    }

    public void removeForma() throws FDadoInvalidoException {
        if (formaSelecionado != null) {
            SessionUtil.remove("modelForma", FacesContext.getCurrentInstance());
            formasDeCobrancaDeletados.add((FormaDeCobranca) formaSelecionado.getObject());
            formasDeCobranca.remove(formaSelecionado);
        }
    }

    public BigDecimal getTotalTipoNaCotacaoPadrao() {
        BigDecimal total = BigDecimal.ZERO;
        try {
            for (TipoDeCobranca tp : tiposDeCobranca.getList()) {
                if (tp.getCotacao() != null && tp.getCotacao() != e.getCotacaoPadrao()) {
                    total = total.add(tp.getValor().divide(tp.getCotacao().getValor(), 2, BigDecimal.ROUND_UP));
                } else {
                    total = total.add(tp.getValor());
                }
            }
        } catch (NullPointerException npe) {
            return null;
        }
        return total.compareTo(BigDecimal.ZERO) == 0 ? null : total;
    }

    public String getTotalTipoNaCotacaoPadraoFormatado() {
        try {
            return MoedaFormatter.format(e.getCotacaoPadrao().getConta().getMoeda(), getTotalTipoNaCotacaoPadrao()) == null ? "" : MoedaFormatter.format(e.getCotacaoPadrao().getConta().getMoeda(), getTotalTipoNaCotacaoPadrao());
        } catch (NullPointerException npe) {
            return "";
        }
    }

    public BigDecimal getTotalFormaNaCotacaoPadrao() {
        BigDecimal total = BigDecimal.ZERO;
        try {
            for (FormaDeCobranca fm : formasDeCobranca.getList()) {
                if (fm.getCotacao() != null && fm.getCotacao() != e.getCotacaoPadrao()) {
                    total = total.add(fm.getValor().divide(fm.getCotacao().getValor(), 2, BigDecimal.ROUND_UP));
                } else {
                    total = total.add(fm.getValor());
                }
            }
        } catch (NullPointerException npe) {
            return null;
        }
        return total.compareTo(BigDecimal.ZERO) == 0 ? null : total;
    }

    public String getTotalFormaNaCotacaoPadraoFormatado() {
        try {
            return MoedaFormatter.format(e.getCotacaoPadrao().getConta().getMoeda(), getTotalFormaNaCotacaoPadrao());
        } catch (NullPointerException npe) {
            return "";
        }
    }

    public BigDecimal getValorEmConta() {
        BigDecimal total = BigDecimal.ZERO;
        try {
            for (TipoDeCobranca tp : tiposDeCobranca.getList()) {
                if (tp.getConta() != null) {
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
        return (totalTipo.subtract(getValorEmConta())).subtract(totalForma);
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
