package br.com.onesystem.domain;

import br.com.onesystem.domain.builder.BaixaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.EstadoDeBaixa;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.TipoLancamentoBancario;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_TRANSFERENCIA",
        sequenceName = "SEQ_TRANSFERENCIA")
public class Transferencia implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_TRANSFERENCIA", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;

    @NotNull(message = "{origem_not_null}")
    @ManyToOne(optional = false)
    private Conta origem;

    @NotNull(message = "{destino_not_null}")
    @ManyToOne(optional = false)
    private Conta destino;

    @NotNull(message = "{valor_not_null}")
    @Min(value = 0, message = "{valor_min}")
    private BigDecimal valor;

    @NotNull(message = "{valorConvertido_not_null}")
    @Min(value = 0, message = "{valorConvertido_min}")
    private BigDecimal valorConvertido;

    @OneToMany(mappedBy = "transferencia", cascade = {CascadeType.ALL})
    private List<Baixa> baixas;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "{tipo_lancamento_not_null}")
    private TipoLancamentoBancario tipoLancamentoBancario;

    @Column(nullable = true)
    private boolean estornado;

    @Column(nullable = true)
    private Long idRelacaoEstorno;

    public Transferencia() {
    }

    public Transferencia(Long id, Conta origem, Conta destino, BigDecimal valor, BigDecimal valorConvertido, List<Baixa> baixas, Date emissao, TipoLancamentoBancario tipoLancamentoBancario, boolean estornado, Long idRelacaoEstorno) throws DadoInvalidoException {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
        this.valorConvertido = valorConvertido;
        this.baixas = baixas;
        this.emissao = emissao;
        this.tipoLancamentoBancario = tipoLancamentoBancario;
        this.estornado = estornado;
        this.idRelacaoEstorno = idRelacaoEstorno;
        ehValido();
    }

    /* Deve ser utilizado para gerar a baixa da transferência */
    public void geraBaixaDaTransferenciaCom(Cotacao origem, Cotacao destino) throws DadoInvalidoException {
        adiciona(new BaixaBuilder().comValor(valor).comOperacaoFinanceira(OperacaoFinanceira.SAIDA).comCotacao(origem).comEstadoDeBaixa(EstadoDeBaixa.EFETIVADO).construir());
        adiciona(new BaixaBuilder().comValor(valorConvertido).comOperacaoFinanceira(OperacaoFinanceira.ENTRADA).comCotacao(destino).comEstadoDeBaixa(EstadoDeBaixa.EFETIVADO).construir());
    }

    /* Deve ser utilizado para gerar a baixa da transferência */
    public void geraEstornoDaTransferenciaCom(Cotacao origem, Cotacao destino) throws DadoInvalidoException {
        adiciona(new BaixaBuilder().comValor(valor).comOperacaoFinanceira(OperacaoFinanceira.ENTRADA).comCotacao(origem).comEstadoDeBaixa(EstadoDeBaixa.EFETIVADO).construir());
        adiciona(new BaixaBuilder().comValor(valorConvertido).comOperacaoFinanceira(OperacaoFinanceira.SAIDA).comCotacao(destino).comEstadoDeBaixa(EstadoDeBaixa.EFETIVADO).construir());
    }

    public void setIdRelacaoEstorno(Transferencia transferencia) {
        this.idRelacaoEstorno = transferencia.getId();
    }

    /* Adiciona Baixa e as tarifas.*/
    public void adiciona(Baixa baixa) {
        try {
            BaixaBuilder b = new BaixaBuilder(baixa);
            if (baixas == null) {
                this.baixas = new ArrayList<>();
            }
            verificaEstorno(baixa, b);
            b.comTransferencia(this);
            geraHistorico(baixa, b);
            b.comEmissao(emissao);
            this.baixas.add(b.construir());
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private void verificaEstorno(Baixa baixa, BaixaBuilder b) {
        if (baixa.getDespesa() != null && this.tipoLancamentoBancario.equals(TipoLancamentoBancario.ESTORNO)) {
            b.comOperacaoFinanceira(OperacaoFinanceira.ENTRADA);
        }
    }

    private void geraHistorico(Baixa baixa, BaixaBuilder b) {
        BundleUtil msg = new BundleUtil();
        if (baixa.getDespesa() == null && this.tipoLancamentoBancario == TipoLancamentoBancario.LANCAMENTO) {
            b.comHistorico(msg.getLabel("Transferencia") + " " + msg.getLabel("de") + " (" + origem.getId() + " - " + origem.getNome() + ") "
                    + msg.getLabel("para") + " (" + destino.getId() + " - " + destino.getNome() + ")");
        } else if (baixa.getDespesa() == null && this.tipoLancamentoBancario == TipoLancamentoBancario.ESTORNO) {
            b.comHistorico(msg.getLabel("Estorno") + " " + msg.getLabel("de") + " (" + destino.getId() + " - " + destino.getNome() + ") "
                    + msg.getLabel("para") + " (" + origem.getId() + " - " + origem.getNome() + ")");
        } else if (this.tipoLancamentoBancario == TipoLancamentoBancario.LANCAMENTO) {
            b.comHistorico(msg.getLabel("Tarifa") + " " + msg.getLabel("de") + " " + baixa.getDespesa().getNome() + " " + msg.getLabel("de") + " " + msg.getLabel("Transferencia"));
        } else if (this.tipoLancamentoBancario == TipoLancamentoBancario.ESTORNO) {
            b.comHistorico(msg.getLabel("Estorno") + " " + msg.getLabel("de") + " " + msg.getLabel("Tarifa") + " " + msg.getLabel("de") + " " + baixa.getDespesa().getNome() + " " + msg.getLabel("de") + " " + msg.getLabel("Transferencia"));
        }
    }

    public Long getId() {
        return id;
    }

    public Conta getOrigem() {
        return origem;
    }

    public Conta getDestino() {
        return destino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public List<Baixa> getBaixas() {
        return baixas;
    }

    public void setBaixas(List<Baixa> baixas) {
        this.baixas = baixas;
    }

    public BigDecimal getValorConvertido() {
        return valorConvertido;
    }

    public Date getEmissao() {
        return emissao;
    }

    public TipoLancamentoBancario getTipoLancamentoBancario() {
        return tipoLancamentoBancario;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("origem", "destino", "valor", "valorConvertido");
        new ValidadorDeCampos<Transferencia>().valida(this, campos);
    }

    public boolean isEstornado() {
        return estornado;
    }

    public Long getIdRelacaoEstorno() {
        return idRelacaoEstorno;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Transferencia)) {
            return false;
        }
        Transferencia outro = (Transferencia) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
