package br.com.onesystem.war.view;

import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.FormaDeCobranca;
import br.com.onesystem.domain.Pagamento;
import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.ModelList;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.valueobjects.ModalidadeDeCobrancaFixa;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import br.com.onesystem.war.builder.FormaDeCobrancaBV;
import br.com.onesystem.war.builder.PagamentoBV;
import br.com.onesystem.war.builder.TipoDeCobrancaBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class PagamentoView extends BasicMBImpl<Pagamento, PagamentoBV> implements Serializable {

    private Model tipoSelecionado;
    private Model formaSelecionado;
    private ModelList<TipoDeCobranca> tiposDeCobranca;
    private ModelList<FormaDeCobranca> formasDeCobranca;
    private TipoDeCobrancaBV tipoDeCobrancaBV;
    private FormaDeCobrancaBV formaDeCobrancaBV;

    @Inject
    private ConfiguracaoService serviceConf;

    public void pagar() {
        try {
            e.setTotalEmDinheiro(getTotalEmDinheiro());
            Pagamento pagamento = e.construirComID();
            tiposDeCobranca.getList().forEach(tp -> pagamento.adiciona(tp));
            formasDeCobranca.getList().forEach(f -> pagamento.adiciona(f));
            pagamento.geraBaixas();
            pagamento.ehValido();

            addNoBanco(pagamento);
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void limparJanela() {
        try {
            e = new PagamentoBV(new Date(), (Caixa) SessionUtil.getObject("caixa", FacesContext.getCurrentInstance()));
            e.setCotacaoPadrao(new CotacaoDAO().buscarCotacoes().porMoeda(serviceConf.buscar().getMoedaPadrao()).naMaiorEmissao(e.getEmissao()).resultado());
            tiposDeCobranca = new ModelList<>();
            formasDeCobranca = new ModelList<>();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void atualizaEmissao() throws DadoInvalidoException {
        e.setCotacaoPadrao(new CotacaoDAO().buscarCotacoes().porMoeda(serviceConf.buscar().getMoedaPadrao()).naMaiorEmissao(e.getEmissao()).resultado());
    }

    @Override
    public void selecionar(SelectEvent event) {
        try {
            Object obj = event.getObject();
            if (obj instanceof TipoDeCobranca) {
                TipoDeCobranca tipo = (TipoDeCobranca) obj;
                boolean possuiCobranca = false;
                if (tipo.getCobranca() != null) {
                    possuiCobranca = tiposDeCobranca.getList().stream().filter(tp -> tp.getCobranca() != null).filter(tp -> tp.getCobranca().getId() != null).map(TipoDeCobranca::getCobranca).anyMatch(co -> co.getId().equals(tipo.getCobranca().getId()));
                } else {
                    possuiCobranca = tiposDeCobranca.getList().stream().filter(tp -> tp.getCobrancaFixa() != null).filter(tp -> tp.getCobrancaFixa().getId() != null).map(TipoDeCobranca::getCobrancaFixa).anyMatch(co -> co.getId().equals(tipo.getCobrancaFixa().getId()));
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
                    tiposDeCobranca.atualiza(model);
                } else {
                    formasDeCobranca.atualiza(model);
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

    public void abreDespesaProvisionada() throws DadoInvalidoException {
        SessionUtil.remove("modalidadeDeCobrancaFixa", FacesContext.getCurrentInstance());
        SessionUtil.put(ModalidadeDeCobrancaFixa.DESPESA_PROVISIONADA, "modalidadeDeCobrancaFixa", FacesContext.getCurrentInstance());
        adicionaEmissaoNaSessao();
    }

    public void abreDespesaEventual() throws DadoInvalidoException {
        SessionUtil.remove("modalidadeDeCobrancaFixa", FacesContext.getCurrentInstance());
        SessionUtil.put(ModalidadeDeCobrancaFixa.DESPESA_EVENTUAL, "modalidadeDeCobrancaFixa", FacesContext.getCurrentInstance());
        adicionaEmissaoNaSessao();
    }

    public void adicionaEmissaoNaSessao() throws DadoInvalidoException {
        SessionUtil.remove("naturezaFinanceira", FacesContext.getCurrentInstance());
        SessionUtil.put(NaturezaFinanceira.DESPESA, "naturezaFinanceira", FacesContext.getCurrentInstance());
        SessionUtil.remove("emissao", FacesContext.getCurrentInstance());
        SessionUtil.put(e.getEmissao(), "emissao", FacesContext.getCurrentInstance());
    }

    public void adicionaTipoNaSessao(SelectEvent event) throws DadoInvalidoException {
        tipoSelecionado = (Model) event.getObject();
        if (tipoSelecionado != null) {
            SessionUtil.remove("model", FacesContext.getCurrentInstance());
            SessionUtil.put(tipoSelecionado, "model", FacesContext.getCurrentInstance());
        }
    }

    public void adicionaFormaNaSessao(SelectEvent event) throws DadoInvalidoException {
        formaSelecionado = (Model) event.getObject();
        if (formaSelecionado != null) {
            SessionUtil.remove("model", FacesContext.getCurrentInstance());
            SessionUtil.put(formaSelecionado, "model", FacesContext.getCurrentInstance());
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
        return MoedaFormatter.format(e.getCotacaoPadrao().getConta().getMoeda(), getTotalTipoNaCotacaoPadrao());
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
        return MoedaFormatter.format(e.getCotacaoPadrao().getConta().getMoeda(), getTotalFormaNaCotacaoPadrao());
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
