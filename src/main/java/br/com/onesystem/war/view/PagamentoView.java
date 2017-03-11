package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.ConfiguracaoCambio;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Despesa;
import br.com.onesystem.domain.DespesaEventual;
import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.FormaPagamentoRecebimento;
import br.com.onesystem.domain.PerfilDeValor;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.services.Movimento;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.war.builder.BaixaBV;
import br.com.onesystem.war.builder.ChequeBV;
import br.com.onesystem.war.builder.DespesaEventualBV;
import br.com.onesystem.war.builder.DespesaProvisionadaBV;
import br.com.onesystem.war.builder.TituloBV;
import br.com.onesystem.war.service.ConfiguracaoCambioService;
import br.com.onesystem.war.service.ContaService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
    private TituloBV titulo;
    private DespesaEventualBV despesaEventual;
    private DespesaProvisionadaBV despesaProvisionada;
    private PerfilDeValor perfilDeValorSelecionado;
    private List<PerfilDeValor> listaPerfilDeValor;
    private List<Cotacao> listaCotacao;
    
    private ChequeBV chequeEmitido;
    private ChequeBV chequeTerceiro;
    private TituloBV debitoCC;
    private FormaPagamentoRecebimento formaPagamentoRecebimentoSelecionado;
    private List<FormaPagamentoRecebimento> listaFormaPagamentoRecebimento;
    
    

    private BaixaBV baixa;
    private BaixaBV baixaDespesaEventual;
    private BaixaBV baixaDespesaProvisionada;
    private List<Baixa> baixaLista;
    private Baixa baixaSelecionada;
    private BigDecimal total;
    private Pessoa pessoa;
    private ConfiguracaoCambio confCambio;

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
        this.despesaEventual.setPessoa(pessoaSelecionada);
    }

    public void selecionaDespesaEventual(SelectEvent event) throws DadoInvalidoException {
        Despesa despesaSelecionada = (Despesa) event.getObject();
        this.despesaEventual.setDespesa(despesaSelecionada);

    }
    
    
    public void selecionaChequeEmitido(SelectEvent event) throws DadoInvalidoException {
        Cheque c = (Cheque) event.getObject();
        this.chequeEmitido = new ChequeBV(c);
    }
    
    public void selecionaChequeTerceiro(SelectEvent event) throws DadoInvalidoException {
        Cheque c = (Cheque) event.getObject();
        this.chequeTerceiro = new ChequeBV(c);
    }
    
    public void selecionaTitulo(SelectEvent event) {
        Titulo t = (Titulo) event.getObject();
        this.titulo = new TituloBV(t);

//        baixa.setConta(contaLista.get(0));
    }

    public List<Cotacao> buscarListaDeCotacao() {
        listaCotacao = new CotacaoDAO().buscarCotacoes().naEmissao(new Date()).listaDeResultados();
        return listaCotacao;
    }

    public void selecionaDespesaProvisionada(SelectEvent event) {
        DespesaProvisionada dp = (DespesaProvisionada) event.getObject();
        despesaProvisionada = new DespesaProvisionadaBV(dp);
//        baixaDespesaProvisionada.setConta(contaLista.get(0));
    }

    public void pagar() {
       // Movimento movimento ; movimento.getperfildevalor
       // if cartao {adicionadao cartao}
       
        
        
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
            limpaDialogos();
            InfoMessage.print("Registro Removido!");
        } else {
            ErrorMessage.print("Selecione um registro para remover!");
        }
    }

    public void addTituloNaLista() {
        try {
            // validaBaixa(perfildevalor); ajustar valida depois para adicionar o hitorico automaticamente
            listaPerfilDeValor.add(titulo.construirComID());
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void addDespesaProvisonadaNaLista() {
        try {
            // validaBaixa(perfildevalor); ajustar valida depois para adicionar o hitorico automaticamente
            listaPerfilDeValor.add(titulo.construirComID());
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void addDespesaEventualNaLista() {
        try {
            //retornar codigo
            // validaBaixa(perfildevalor); ajustar valida depois para adicionar o hitorico automaticamente
            listaPerfilDeValor.add(despesaEventual.construirComID());
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private void validaExistente() throws EDadoInvalidoException {
        for (PerfilDeValor novo : listaPerfilDeValor) {
            if (novo != null && novo.getId().equals(titulo.getId())) {
                throw new EDadoInvalidoException("Titulo já consta na lista!");
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

    private void limpaDialogos() {
        perfilDeValorSelecionado = null;
        // listaPerfilDeValor = new ArrayList<>();
        titulo = new TituloBV();
        despesaProvisionada = new DespesaProvisionadaBV();
        despesaEventual = new DespesaEventualBV();
    }

//    public void abrirBaixaComDados() {
//        if (this.baixa.getPerfilDeValor() instanceof Titulo) {
//            baixa = new BaixaBV(baixaSelecionada);
//            buscarListaDeContas(baixaSelecionada.getCotacao().getConta().getMoeda());
//            RequestContext.getCurrentInstance().execute("PF('pagarTitulo').show()");
//        } else if (this.baixa.getPerfilDeValor() instanceof DespesaProvisionada) {
//            baixaDespesaProvisionada = new BaixaBV(baixaSelecionada);
//            buscarListaDeContas(baixaSelecionada.getCotacao().getConta().getMoeda());
//            RequestContext.getCurrentInstance().execute("PF('pagarDespesaProvisionada').show()");
//        } else {
//            buscarListaDeContas();
//            baixaDespesaEventual = new BaixaBV(baixaSelecionada);
//            RequestContext.getCurrentInstance().execute("PF('despesaEventual').show()");
//        }
//    }
    public void abrirPerfilDeValorComDados() {
        if (this.perfilDeValorSelecionado instanceof Titulo) {
//            baixa = new BaixaBV(baixaSelecionada);
//            buscarListaDeContas(baixaSelecionada.getCotacao().getConta().getMoeda());
//            RequestContext.getCurrentInstance().execute("PF('pagarTitulo').show()");
        } else if (this.perfilDeValorSelecionado instanceof DespesaProvisionada) {
//            baixaDespesaProvisionada = new BaixaBV(baixaSelecionada);
//            buscarListaDeContas(baixaSelecionada.getCotacao().getConta().getMoeda());
//            RequestContext.getCurrentInstance().execute("PF('pagarDespesaProvisionada').show()");
        } else {
//            buscarListaDeContas();
//            baixaDespesaEventual = new BaixaBV(baixaSelecionada);
//            RequestContext.getCurrentInstance().execute("PF('despesaEventual').show()");
        }
    }

    public void abrirTitulo() {
        RequestContext.getCurrentInstance().execute("PF('pagarTitulo').show()");
        limpaDialogos();
    }

    public void abrirDespesaProvisionada() {
        RequestContext.getCurrentInstance().execute("PF('pagarDespesaProvisionada').show()");
        limpaDialogos();
    }

    public void abrirDespesaEventual() {
        RequestContext.getCurrentInstance().execute("PF('despesaEventual').show()");
        limpaDialogos();

//        baixaDespesaEventual.setConta(contaLista.get(0)); Verificar possivel erro de atualização
    }

    public void abrirChequeEmitido() {
        RequestContext.getCurrentInstance().execute("PF('chequeEmitido').show()");
        limpaDialogos();
    }

    public void abrirChequeTerceiro() {
        RequestContext.getCurrentInstance().execute("PF('chequeTerceiro').show()");
        limpaDialogos();
    }

    public void abrirDebitoCC() {
        RequestContext.getCurrentInstance().execute("PF('debitoCC').show()");
        limpaDialogos();
    }

    private void limpar() {
        data = new Date();
        baixa = new BaixaBV();
        baixaDespesaProvisionada = new BaixaBV();
        baixaDespesaEventual = new BaixaBV();
        baixaLista = new ArrayList<Baixa>();
        baixaSelecionada = null;
        total = BigDecimal.ZERO;

        titulo = new TituloBV();
        despesaEventual = new DespesaEventualBV();
        despesaProvisionada = new DespesaProvisionadaBV();
        listaPerfilDeValor = new ArrayList<>();
        perfilDeValorSelecionado = null;

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

    public List<Cotacao> getListaCotacao() {
        return listaCotacao;
    }

    public void setListaCotacao(List<Cotacao> listaCotacao) {
        this.listaCotacao = listaCotacao;
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

    public TituloBV getTitulo() {
        return titulo;
    }

    public void setTitulo(TituloBV titulo) {
        this.titulo = titulo;
    }

    public DespesaEventualBV getDespesaEventual() {
        return despesaEventual;
    }

    public void setDespesaEventual(DespesaEventualBV despesaEventual) {
        this.despesaEventual = despesaEventual;
    }

    public DespesaProvisionadaBV getDespesaProvisionada() {
        return despesaProvisionada;
    }

    public void setDespesaProvisionada(DespesaProvisionadaBV despesaProvisionada) {
        this.despesaProvisionada = despesaProvisionada;
    }

    public List<PerfilDeValor> getListaPerfilDeValor() {
        return listaPerfilDeValor;
    }

    public void setListaPerfilDeValor(List<PerfilDeValor> listaPerfilDeValor) {
        this.listaPerfilDeValor = listaPerfilDeValor;
    }

    public PerfilDeValor getPerfilDeValorSelecionado() {
        return perfilDeValorSelecionado;
    }

    public void setPerfilDeValorSelecionado(PerfilDeValor perfilDeValorSelecionado) {
        this.perfilDeValorSelecionado = perfilDeValorSelecionado;
    }

    public FormaPagamentoRecebimento getFormaPagamentoRecebimentoSelecionado() {
        return formaPagamentoRecebimentoSelecionado;
    }

    public void setFormaPagamentoRecebimentoSelecionado(FormaPagamentoRecebimento formaPagamentoRecebimentoSelecionado) {
        this.formaPagamentoRecebimentoSelecionado = formaPagamentoRecebimentoSelecionado;
    }

    public List<FormaPagamentoRecebimento> getListaFormaPagamentoRecebimento() {
        return listaFormaPagamentoRecebimento;
    }

    public void setListaFormaPagamentoRecebimento(List<FormaPagamentoRecebimento> listaFormaPagamentoRecebimento) {
        this.listaFormaPagamentoRecebimento = listaFormaPagamentoRecebimento;
    }

    public ChequeBV getChequeEmitido() {
        return chequeEmitido;
    }

    public void setChequeEmitido(ChequeBV chequeEmitido) {
        this.chequeEmitido = chequeEmitido;
    }

    public ChequeBV getChequeTerceiro() {
        return chequeTerceiro;
    }

    public void setChequeTerceiro(ChequeBV chequeTerceiro) {
        this.chequeTerceiro = chequeTerceiro;
    }

   
    
    
    

}
