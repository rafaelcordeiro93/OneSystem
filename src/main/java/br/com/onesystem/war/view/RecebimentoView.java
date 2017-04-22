package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.ContaDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.ConfiguracaoCambio;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.FormaPagamentoRecebimento;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Transacao;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class RecebimentoView implements Serializable {

    private FormaPagamentoRecebimento formaPagamentoRecebimento; //classe abstrata para recebimento de valores
    
    
    private Date data = new Date();
    private BaixaBV baixa;
    private BaixaBV baixaReceitaEventual;
    private BaixaBV baixaReceitaProvisionada;
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
        this.baixaReceitaEventual.setPessoa(pessoaSelecionada);
    }

    public void selecionaReceita(SelectEvent event) {
        TipoReceita ReceitaSelecionada = (TipoReceita) event.getObject();
        this.baixaReceitaEventual.setReceita(ReceitaSelecionada);
    }

    public void selecionaTitulo(SelectEvent event) {
        Titulo titulo = (Titulo) event.getObject();
        baixa.selecionaTitulo(titulo);
        buscarListaDeContas(titulo.getMoeda());
//        baixa.setConta(contaLista.get(0)); Verificar possivel erro de atualização
    }

    private void buscarListaDeContas(Moeda moeda) {
        contaLista = new ContaDAO().buscarContaW().ePorMoeda(moeda).listaDeResultados();
    }

    private void buscarListaDeContas() {
        contaLista = new ContaDAO().buscarContaW().listaDeResultados();
    }

    public void selecionaReceitaProvisionada(SelectEvent event) {
        ReceitaProvisionada dp = (ReceitaProvisionada) event.getObject();
        baixaReceitaProvisionada.selecionaReceitaProvisionada(dp);
        buscarListaDeContas(dp.getMoeda());
//        baixaReceitaProvisionada.setConta(contaLista.get(0)); Verificar possivel erro de atualização
    }

    public void receber() {
        try {
            for (Baixa novaBaixa : baixaLista) {
                new AdicionaDAO<Baixa>().adiciona(new BaixaBV(novaBaixa).construir());
                atualizaSaldo(novaBaixa);
            }
            limpar();
            InfoMessage.print("Recebimentos realizados com sucesso!");
        } catch (ConstraintViolationException ex) {
            ErrorMessage.print("Erro: " + ex.getMessage());
        } catch (DadoInvalidoException ex) {
            ex.print();
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
            if (baixa.getId() == null && baixaReceitaProvisionada.getId() == null && baixaReceitaEventual.getId() == null) {
                validaExistente();
                Baixa novaBaixa = contruirBaixa(baixa.getPerfilDeValor() instanceof Titulo ? baixa : baixaReceitaEventual.getReceita() != null ? baixaReceitaEventual : baixaReceitaProvisionada);
                baixaLista.add(novaBaixa);
                limpaBaixa();
            } else {
                Baixa atualizaBaixa = (baixa.getId() != null ? baixa.construirComID() : baixaReceitaEventual.getId() != null ? baixaReceitaEventual.construirComID() : baixaReceitaProvisionada.construirComID());
                baixaLista.set(baixaLista.indexOf(baixaSelecionada), atualizaBaixa);
                limpaBaixa();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private void validaExistente() throws EDadoInvalidoException {
        for (Baixa novaBaixa : baixaLista) {
            if (novaBaixa.getTransacao() != null && novaBaixa.getTransacao().getId().equals(baixa.getPerfilDeValor().getId())) {
                throw new EDadoInvalidoException("Baixa já consta na lista!");
            }

        }
    }

    private void validaBaixa() throws EDadoInvalidoException {
        validaRegistroFoiInformado();
        if ((this.baixa.getHistorico() == null || this.baixa.getHistorico().length() == 0)
                && (this.baixaReceitaProvisionada.getHistorico() == null || this.baixaReceitaProvisionada.getHistorico().length() == 0)
                && (this.baixaReceitaEventual.getHistorico() == null || this.baixaReceitaEventual.getHistorico().length() == 0)) {
            String str;
            if (baixa.getPerfilDeValor() instanceof Titulo) {
                str = " Título";
                if (this.baixa.getPessoa() != null) {
                    this.baixa.setHistorico("Recebimento de " + str + " para " + this.baixa.getPessoa().getNome());
                }
            } else if (baixaReceitaEventual.getReceita() != null) {
                str = "Recebimento de " + baixaReceitaEventual.getReceita().getNome();
                this.baixaReceitaEventual.setHistorico(str);
            } else {
                str = "Receita Provisionada";
                this.baixaReceitaProvisionada.setHistorico("Recebimento de " + str + this.baixaReceitaProvisionada.getPessoa() == null ? " para " + this.baixaReceitaProvisionada.getPessoa().getNome() : "");
            }
        }
        if (baixa.getPerfilDeValor() instanceof Titulo && this.baixa.getValor().compareTo(((Titulo) this.baixa.getPerfilDeValor()).getSaldo()) > 0) {
            throw new EDadoInvalidoException("O valor deve ser menor que o saldo.");
        }
    }

    private void validaRegistroFoiInformado() throws EDadoInvalidoException {
        if (this.baixa.getPerfilDeValor() instanceof Titulo && this.baixaReceitaEventual.getReceita() == null && this.baixa.getPerfilDeValor() instanceof ReceitaProvisionada) {
            throw new EDadoInvalidoException("Selecione um titulo ou uma Receita para receber!");
        }
        if (this.baixa.getValor().compareTo(BigDecimal.ZERO) == 0 && this.baixaReceitaProvisionada.getValor().compareTo(BigDecimal.ZERO) == 0
                && this.baixaReceitaEventual.getValor().compareTo(BigDecimal.ZERO) == 0) {
            throw new EDadoInvalidoException("O valor deve ser maior que zero!");
        }
    }

    private Baixa contruirBaixa(BaixaBV baixa) throws EDadoInvalidoException, DadoInvalidoException {
        baixa.setId(retornarCodigo());
        baixa.setEmissao(data);
        baixa.setNaturezaFinanceira(OperacaoFinanceira.ENTRADA);
        Baixa novaBaixa = baixa.construirComID();
        total = total.add(baixa.getValor());
        return novaBaixa;
    }

    private Baixa atualizaSaldo(Baixa baixa) throws DadoInvalidoException, EDadoInvalidoException {
        if (baixa.getTransacao() instanceof Titulo) {
            ((Titulo) this.baixa.getPerfilDeValor()).atualizaSaldo(baixa.getValor());
            new AtualizaDAO<Transacao>().atualiza(baixa.getTransacao());
        }
        return baixa;
    }

    private void limpaBaixa() {
        baixa = new BaixaBV();
        baixaSelecionada = null;
        contaLista = new ArrayList<Conta>();
        baixaReceitaProvisionada = new BaixaBV();
        baixaReceitaEventual = new BaixaBV();
    }

    public void abrirBaixaComDados() {
        if (baixa.getPerfilDeValor() instanceof Titulo) {
            baixa = new BaixaBV(baixaSelecionada);
            buscarListaDeContas(baixaSelecionada.getCotacao().getConta().getMoeda());
            RequestContext.getCurrentInstance().execute("PF('receberTitulo').show()");
        } else if (baixa.getPerfilDeValor() instanceof ReceitaProvisionada) {
            baixaReceitaProvisionada = new BaixaBV(baixaSelecionada);
            buscarListaDeContas(baixaSelecionada.getCotacao().getConta().getMoeda());
            RequestContext.getCurrentInstance().execute("PF('receberReceitaProvisionada').show()");
        } else {
            buscarListaDeContas();
            baixaReceitaEventual = new BaixaBV(baixaSelecionada);
            RequestContext.getCurrentInstance().execute("PF('ReceitaEventual').show()");
        }
    }

    public void abrirTitulo() {
        RequestContext.getCurrentInstance().execute("PF('receberTitulo').show()");
        limpaBaixa();
    }

    public void abrirReceitaProvisionada() {
        RequestContext.getCurrentInstance().execute("PF('receberReceitaProvisionada').show()");
        limpaBaixa();
    }

    public void abrirReceitaEventual() {
        RequestContext.getCurrentInstance().execute("PF('receitaEventual').show()");
        limpaBaixa();
        buscarListaDeContas();
//        baixaReceitaEventual.setConta(contaLista.get(0)); Verificar possível erro de atualização
    }
    
    public void abrirCheque() {
        RequestContext.getCurrentInstance().execute("PF('cheque').show()");
        limpaBaixa();
    }
    public void abrirCartao() {
        RequestContext.getCurrentInstance().execute("PF('cartao').show()");
        limpaBaixa();
    }
    public void abrirDebito() {
        RequestContext.getCurrentInstance().execute("PF('debitoCC').show()");
        limpaBaixa();
    }
    

    private void limpar() {
        data = new Date();
        baixa = new BaixaBV();
        baixaReceitaProvisionada = new BaixaBV();
        baixaReceitaEventual = new BaixaBV();
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

    public BaixaBV getBaixaReceitaProvisionada() {
        return baixaReceitaProvisionada;
    }

    public void setBaixaReceitaProvisionada(BaixaBV baixaReceitaProvisionada) {
        this.baixaReceitaProvisionada = baixaReceitaProvisionada;
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

    public BaixaBV getBaixaReceitaEventual() {
        return baixaReceitaEventual;
    }

    public void setBaixaReceitaEventual(BaixaBV baixaReceitaEventual) {
        this.baixaReceitaEventual = baixaReceitaEventual;
    }

}
