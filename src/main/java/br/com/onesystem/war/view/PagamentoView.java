package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.ContaDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.ConfiguracaoCambio;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Despesa;
import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.PerfilDeValor;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.war.builder.BaixaBV;
import br.com.onesystem.war.service.ConfiguracaoCambioService;
import br.com.onesystem.war.service.ContaService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class PagamentoView implements Serializable {

    private Date data = new Date();
    private BaixaBV baixa;
    private BaixaBV baixaDespesaEventual;
    private BaixaBV baixaDespesaProvisionada;
    private List<Baixa> baixaLista;
    private Baixa baixaSelecionada;
    private BigDecimal total;
    private Pessoa pessoa;
    private ConfiguracaoCambio confCambio;
    private List<Conta> contaLista;

    @ManagedProperty("#{configuracaoCambioService}")
    private ConfiguracaoCambioService serviceConfCambio;

    @ManagedProperty("#{contaService}")
    private ContaService serviceConta;

    @PostConstruct
    public void init() {
        limpar();
        inicializaConfiguracoes();
    }

    private void inicializaConfiguracoes() {
        try {
            confCambio = serviceConfCambio.buscar();
        } catch (EDadoInvalidoException ex) {
            ex.print();
        }
    }

    public void selecionaPessoa(SelectEvent event) {
        Pessoa pessoaSelecionada = (Pessoa) event.getObject();
        this.setPessoa(pessoaSelecionada);
    }

    public void selecionaPessoaEventual(SelectEvent event) {
        Pessoa pessoaSelecionada = (Pessoa) event.getObject();
        this.baixaDespesaEventual.setPessoa(pessoaSelecionada);
    }

    public void selecionaDespesa(SelectEvent event) {
        Despesa despesaSelecionada = (Despesa) event.getObject();
        this.baixaDespesaEventual.setDespesa(despesaSelecionada);
    }

    public void selecionaTitulo(SelectEvent event) {
        Titulo titulo = (Titulo) event.getObject();
        baixa.selecionaTitulo(titulo);
        buscarListaDeContas(titulo.getMoeda());
//        baixa.setConta(contaLista.get(0));
    }

    private void buscarListaDeContas(Moeda moeda) {
        contaLista = new ContaDAO().buscarContaW().ePorMoeda(moeda).listaDeResultados();
    }

    private void buscarListaDeContas() {
        contaLista = new ContaDAO().buscarContaW().listaDeResultados();
    }

    public void selecionaDespesaProvisionada(SelectEvent event) {
        DespesaProvisionada dp = (DespesaProvisionada) event.getObject();
        baixaDespesaProvisionada.selecionaDespesaProvisionada(dp);
        buscarListaDeContas(dp.getMoeda());
//        baixaDespesaProvisionada.setConta(contaLista.get(0));
    }

    public void pagar() {
        try {
            for (Baixa novaBaixa : baixaLista) {
                new AdicionaDAO<Baixa>().adiciona(new BaixaBV(novaBaixa).construir());
                atualizaSaldo(novaBaixa);
                ehDivisaoDeLucro(novaBaixa);
            }
            limpar();
            InfoMessage.print("Pagamentos realizados com sucesso!");
        } catch (ConstraintViolationException ex) {
            ErrorMessage.print("Erro: " + ex.getMessage());
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private void ehDivisaoDeLucro(Baixa novaBaixa) throws DadoInvalidoException, ConstraintViolationException {
        if (novaBaixa.getPerfilDeValor() instanceof DespesaProvisionada && confCambio != null
                && ((DespesaProvisionada) novaBaixa.getPerfilDeValor()).isDivisaoLucroCambioCaixa()) {
            BaixaBV baixaBV = new BaixaBV(novaBaixa);
//            baixaBV.setConta(confCambio.getContaCaixa()); Verificar Possivel erro apos atualização
            baixaBV.setNaturezaFinanceira(OperacaoFinanceira.ENTRADA);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(novaBaixa.getEmissao());
            calendar.add(Calendar.MILLISECOND, 1);
            baixaBV.setEmissao(calendar.getTime());
            new AdicionaDAO<Baixa>().adiciona(baixaBV.construir());
        }
    }

    public void delete() {
        if (baixaSelecionada != null) {
            total = total.subtract(baixaSelecionada.getValor());
            baixaLista.remove(baixaSelecionada);
            limpaBaixa();
            InfoMessage.print("Registro Removido!");
        } else {
            ErrorMessage.print("Selecione um registro para remover!");
        }
    }

    public void confirmar() {
        try {
            validaBaixa();
            if (baixa.getId() == null && baixaDespesaProvisionada.getId() == null && baixaDespesaEventual.getId() == null) {
                validaExistente();
                Baixa novaBaixa = contruirBaixa(baixa.getPerfilDeValor() instanceof Titulo ? baixa : baixaDespesaEventual.getDespesa() != null ? baixaDespesaEventual : baixaDespesaProvisionada);
                baixaLista.add(novaBaixa);
                limpaBaixa();
            } else {
                Baixa atualizaBaixa = (baixa.getId() != null ? baixa.construirComID() : baixaDespesaEventual.getId() != null ? baixaDespesaEventual.construirComID() : baixaDespesaProvisionada.construirComID());
                baixaLista.set(baixaLista.indexOf(baixaSelecionada), atualizaBaixa);
                limpaBaixa();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private void validaExistente() throws EDadoInvalidoException {
        for (Baixa novaBaixa : baixaLista) {
            if (novaBaixa.getPerfilDeValor() != null && novaBaixa.getPerfilDeValor().getId().equals(baixa.getPerfilDeValor().getId())) {
                throw new EDadoInvalidoException("Baixa já consta na lista!");
            }

        }
    }

    private void validaBaixa() throws EDadoInvalidoException {
        validaRegistroFoiInformado();
        if ((this.baixa.getHistorico() == null || this.baixa.getHistorico().length() == 0)
                && (this.baixaDespesaProvisionada.getHistorico() == null || this.baixaDespesaProvisionada.getHistorico().length() == 0)
                && (this.baixaDespesaEventual.getHistorico() == null || this.baixaDespesaEventual.getHistorico().length() == 0)) {
            String str;
            if (baixa.getPerfilDeValor() instanceof Titulo) {
                str = " Título";
                if (this.baixa.getPessoa() != null) {
                    this.baixa.setHistorico("Pagamento de " + str + " para " + this.baixa.getPessoa().getNome());
                }
            } else if (baixaDespesaEventual.getDespesa() != null) {
                str = "Pagamento de " + baixaDespesaEventual.getDespesa().getNome();
                this.baixaDespesaEventual.setHistorico(str);
            } else {
                str = "Despesa Provisionada";
                this.baixaDespesaProvisionada.setHistorico("Pagamento de " + str + this.baixaDespesaProvisionada.getPessoa() == null ? " para " + this.baixaDespesaProvisionada.getPessoa().getNome() : "");
            }
        }
        if (baixa.getPerfilDeValor() instanceof Titulo) {
            if (this.baixa.getValor().compareTo(((Titulo) this.baixa.getPerfilDeValor()).getSaldo()) > 0) {
                throw new EDadoInvalidoException("O valor deve ser menor que o saldo.");
            }
        }
    }

    private void validaRegistroFoiInformado() throws EDadoInvalidoException {
        if (this.baixa.getPerfilDeValor() instanceof Titulo && this.baixaDespesaEventual.getDespesa() == null && this.baixa.getPerfilDeValor() instanceof DespesaProvisionada) {
            throw new EDadoInvalidoException("Selecione um titulo ou uma despesa para pagar!");
        }
        if (this.baixa.getValor().compareTo(BigDecimal.ZERO) == 0 && this.baixaDespesaProvisionada.getValor().compareTo(BigDecimal.ZERO) == 0
                && this.baixaDespesaEventual.getValor().compareTo(BigDecimal.ZERO) == 0) {
            throw new EDadoInvalidoException("O valor deve ser maior que zero!");
        }
    }

    private Baixa contruirBaixa(BaixaBV baixa) throws EDadoInvalidoException, DadoInvalidoException {
        baixa.setId(retornarCodigo());
        baixa.setEmissao(data);
        baixa.setNaturezaFinanceira(OperacaoFinanceira.SAIDA);
        Baixa novaBaixa = baixa.construirComID();
        total = total.add(baixa.getValor());
        return novaBaixa;
    }

    private Baixa atualizaSaldo(Baixa baixa) throws DadoInvalidoException, EDadoInvalidoException {
        if (baixa.getPerfilDeValor() instanceof Titulo) {
            ((Titulo) baixa.getPerfilDeValor()).atualizaSaldo(baixa.getValor());
            new AtualizaDAO<PerfilDeValor>(PerfilDeValor.class).atualiza(baixa.getPerfilDeValor());
        }
        return baixa;
    }

    private void limpaBaixa() {
        baixa = new BaixaBV();
        baixaSelecionada = null;
        contaLista = new ArrayList<Conta>();
        baixaDespesaProvisionada = new BaixaBV();
        baixaDespesaEventual = new BaixaBV();
    }

    public void abrirBaixaComDados() {
        if (this.baixa.getPerfilDeValor() instanceof Titulo) {
            baixa = new BaixaBV(baixaSelecionada);
            buscarListaDeContas(baixaSelecionada.getCotacao().getConta().getMoeda());
            RequestContext.getCurrentInstance().execute("PF('pagarTitulo').show()");
        } else if (this.baixa.getPerfilDeValor() instanceof DespesaProvisionada) {
            baixaDespesaProvisionada = new BaixaBV(baixaSelecionada);
            buscarListaDeContas(baixaSelecionada.getCotacao().getConta().getMoeda());
            RequestContext.getCurrentInstance().execute("PF('pagarDespesaProvisionada').show()");
        } else {
            buscarListaDeContas();
            baixaDespesaEventual = new BaixaBV(baixaSelecionada);
            RequestContext.getCurrentInstance().execute("PF('despesaEventual').show()");
        }
    }

    public void abrirTitulo() {
        RequestContext.getCurrentInstance().execute("PF('pagarTitulo').show()");
        limpaBaixa();
    }

    public void abrirDespesaProvisionada() {
        RequestContext.getCurrentInstance().execute("PF('pagarDespesaProvisionada').show()");
        limpaBaixa();
    }

    public void abrirDespesaEventual() {
        RequestContext.getCurrentInstance().execute("PF('despesaEventual').show()");
        limpaBaixa();
        buscarListaDeContas();
//        baixaDespesaEventual.setConta(contaLista.get(0)); Verificar possivel erro de atualização
    }

    public void abrirChequeEmitido() {
        RequestContext.getCurrentInstance().execute("PF('chequeEmitido').show()");
        limpaBaixa();
    }

    public void abrirChequeTerceiro() {
        RequestContext.getCurrentInstance().execute("PF('chequeTerceiro').show()");
        limpaBaixa();
    }

    public void abrirDebitoCC() {
        RequestContext.getCurrentInstance().execute("PF('debitoCC').show()");
        limpaBaixa();
    }

    private void limpar() {
        data = new Date();
        baixa = new BaixaBV();
        baixaDespesaProvisionada = new BaixaBV();
        baixaDespesaEventual = new BaixaBV();
        baixaLista = new ArrayList<Baixa>();
        baixaSelecionada = null;
        total = BigDecimal.ZERO;
    }

    private Long retornarCodigo() {
        Long id = (long) 1;
        if (!baixaLista.isEmpty()) {
            for (Baixa baixaExistente : baixaLista) {
                if (baixaExistente.getId() >= id) {
                    id = baixaExistente.getId() + 1;
                }
            }
        }
        return id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public BaixaBV getBaixa() {
        return baixa;
    }

    public void setBaixa(BaixaBV baixa) {
        this.baixa = baixa;
    }

    public List<Baixa> getBaixaLista() {
        return baixaLista;
    }

    public void setBaixaLista(List<Baixa> baixaLista) {
        this.baixaLista = baixaLista;
    }

    public Baixa getBaixaSelecionada() {
        return baixaSelecionada;
    }

    public void setBaixaSelecionada(Baixa baixaSelecionada) {
        this.baixaSelecionada = baixaSelecionada;
    }

    public String getTotalFormatado() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return nf.format(total);
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BaixaBV getBaixaDespesaProvisionada() {
        return baixaDespesaProvisionada;
    }

    public void setBaixaDespesaProvisionada(BaixaBV baixaDespesaProvisionada) {
        this.baixaDespesaProvisionada = baixaDespesaProvisionada;
    }

    public ConfiguracaoCambio getConfCambio() {
        return confCambio;
    }

    public void setConfCambio(ConfiguracaoCambio confCambio) {
        this.confCambio = confCambio;
    }

    public ConfiguracaoCambioService getServiceConfCambio() {
        return serviceConfCambio;
    }

    public void setServiceConfCambio(ConfiguracaoCambioService serviceConfCambio) {
        this.serviceConfCambio = serviceConfCambio;
    }

    public List<Conta> getContaLista() {
        return contaLista;
    }

    public void setContaLista(List<Conta> contaLista) {
        this.contaLista = contaLista;
    }

    public ContaService getServiceConta() {
        return serviceConta;
    }

    public void setServiceConta(ContaService serviceConta) {
        this.serviceConta = serviceConta;
    }

    public BaixaBV getBaixaDespesaEventual() {
        return baixaDespesaEventual;
    }

    public void setBaixaDespesaEventual(BaixaBV baixaDespesaEventual) {
        this.baixaDespesaEventual = baixaDespesaEventual;
    }

}
