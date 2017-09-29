package br.com.onesystem.domain;

import br.com.onesystem.domain.builder.BaixaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.EstadoDeBaixa;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.EstadoDeCheque;
import br.com.onesystem.valueobjects.SituacaoDeCobranca;
import br.com.onesystem.valueobjects.TipoLancamento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("CHEQUE")
public class Cheque extends CobrancaVariavel implements Serializable {

    @NotNull(message = "{banco_not_null}")
    @ManyToOne
    private Banco banco;
    @NotNull(message = "{agencia_not_null}")
    private String agencia;
    @NotNull(message = "{conta_not_null}")
    private String conta;
    @NotNull(message = "{numero_cheque_not_null}")
    private String numeroCheque;
    @NotNull(message = "{estado_not_null}")
    @Enumerated(EnumType.STRING)
    private EstadoDeCheque estado;
    @NotNull(message = "{tipo_lancamento_not_null}")
    @Enumerated(EnumType.STRING)
    private TipoLancamento tipoLancamento;
    @Column(nullable = true)
    private BigDecimal multas;
    @Column(nullable = true)
    private BigDecimal juros;
    @Column(nullable = true)
    private BigDecimal descontos;
    @Column(nullable = true)
    private String emitente;
    @ManyToOne
    private DepositoBancario depositoBancario;
    @Temporal(TemporalType.TIMESTAMP)
    private Date compensacao;

    public Cheque() {
    }

    public Cheque(Long id, Nota nota, BigDecimal valor, Date emissao, Date vencimento, Banco banco, String agencia,
            String conta, String numeroCheque, EstadoDeCheque tipoSituacao, BigDecimal multas, BigDecimal juros, BigDecimal descontos, String emitente, OperacaoFinanceira operacaoFinanceira,
            String historico, Cotacao cotacao, TipoLancamento tipoLancamento, Pessoa pessoa, Boolean entrada, 
            SituacaoDeCobranca situacaoDeCobranca, Date compensacao, Filial filial, Integer parcela) throws DadoInvalidoException {
        super(id, emissao, pessoa, cotacao, historico, operacaoFinanceira, valor, vencimento, nota, entrada, situacaoDeCobranca, filial, parcela);
        this.banco = banco;
        this.agencia = agencia;
        this.conta = conta;
        this.numeroCheque = numeroCheque;
        this.estado = tipoSituacao;
        this.multas = multas;
        this.juros = juros;
        this.descontos = descontos;
        this.emitente = emitente;
        this.tipoLancamento = tipoLancamento;
        this.compensacao = compensacao;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> camposCheque = Arrays.asList("banco", "agencia", "conta", "numeroCheque", "estado",
                "multas", "juros", "descontos", "emitente");
        new ValidadorDeCampos<Cheque>().valida(this, camposCheque);
    }

    /* Deve ser utilizado para gerar a baixa do depósito */
    public void geraBaixaDeCheque() throws DadoInvalidoException {
//        Caixa caixa = (Caixa) SessionUtil.getObject("caixa", FacesContext.getCurrentInstance());
//        if (this.compensacao != null && this.tipoLancamento == TipoLancamento.EMITIDA) {
//            adiciona(new BaixaBuilder().comFilial(getFilial()).comValor(valor).comOperacaoFinanceira(OperacaoFinanceira.SAIDA).comCotacao(cotacao).comEstadoDeBaixa(EstadoDeBaixa.EFETIVADO).comDataCompensacao(compensacao).comCaixa(caixa).comPessoa(pessoa).construir());//Baixas Compensadas
//        } else if (this.tipoLancamento == TipoLancamento.EMITIDA) {
//            adiciona(new BaixaBuilder().comFilial(getFilial()).comValor(valor).comOperacaoFinanceira(OperacaoFinanceira.SAIDA).comCotacao(cotacao).comCaixa(caixa).comPessoa(pessoa).construir());//Baixas do Lançamento
//        } else if (this.tipoLancamento == TipoLancamento.RECEBIDA) {
//            adiciona(new BaixaBuilder().comFilial(getFilial()).comValor(valor).comOperacaoFinanceira(OperacaoFinanceira.ENTRADA).comCotacao(cotacao).comCaixa(caixa).comPessoa(pessoa).construir());//Baixas do Lançamento
//        }
    }

//    /* Adiciona Baixa*/
//    @Override
//    public void adiciona(Baixa baixa) {
//        try {
//            BaixaBuilder b = new BaixaBuilder(baixa);
//            if (baixas == null) {
//                this.baixas = new ArrayList<>();
//            }
//            if(baixa.getHistorico() == null){
//            geraHistorico(b);
//            }
//            b.comCobranca(this);
//            b.comEmissao(emissao);
//            this.baixas.add(b.construir());
//        } catch (DadoInvalidoException ex) {
//            ex.print();
//        }
//    }
//
//    private void geraHistorico(BaixaBuilder b) {
//        BundleUtil msg = new BundleUtil();
//        if (this.tipoLancamento == TipoLancamento.EMITIDA) {
//            b.comHistorico(msg.getLabel("Cheque") + " " + msg.getLabel("Emitido") + " " + msg.getLabel("para") + " " + pessoa.getFirstNameLastName());
//        } else if (this.tipoLancamento == TipoLancamento.RECEBIDA) {
//            b.comHistorico(msg.getLabel("Cheque") + " " + msg.getLabel("Recebido") + " " + msg.getLabel("de") + " " + pessoa.getFirstNameLastName());
//        }
//    }

    @Override
    public ModalidadeDeCobranca getModalidade() {
        return ModalidadeDeCobranca.CHEQUE;
    }

    public void cancela() {
        this.estado = EstadoDeCheque.CANCELADO;
    }

    public void devolve() {
        this.estado = EstadoDeCheque.DEVOLVIDO;
    }

    public void desconta() {
        this.estado = EstadoDeCheque.DESCONTADO;
    }

    public void compensar(Date data) {
        this.estado = EstadoDeCheque.COMPENSADO;
        this.compensacao = data;
    }

    public void depositaNo(DepositoBancario deposito) {
        this.depositoBancario = deposito;
    }

    public Banco getBanco() {
        return banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getConta() {
        return conta;
    }

    public String getNumeroCheque() {
        return numeroCheque;
    }

    public EstadoDeCheque getEstadoDeCheque() {
        return estado;
    }

    public BigDecimal getMultas() {
        return multas;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public BigDecimal getDescontos() {
        return descontos;
    }

    public String getEmitente() {
        return emitente;
    }

    public TipoLancamento getTipoLancamento() {
        return tipoLancamento;
    }

    public DepositoBancario getDepositoBancario() {
        return depositoBancario;
    }

    public Date getCompensacao() {
        return compensacao;
    }

    public String getDetalhes() {
        return banco == null ? "" : banco.getNome() + " / " + agencia + " / " + conta + " Nº " + numeroCheque;
    }

    @Override
    public String toString() {
        return "Cheque{" + "id=" + getId() + ", nota=" + (getNota() == null ? null : getNota().getId()) + ", valor=" + valor
                + ", emissao=" + getEmissao() + ", vencimento=" + getVencimento() + ", banco=" + (banco == null ? null : banco.getId()) + ", agencia=" + agencia
                + ", conta=" + conta + ", numeroCheque=" + numeroCheque + ", tipoSituacao=" + estado + ", multas=" + multas + ", juros=" + juros
                + ", tipoLancamento=" + tipoLancamento + ", descontos=" + descontos + ", emitente=" + emitente + ", historico=" + getHistorico()
                + '}';
    }

}
