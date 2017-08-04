package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.war.service.ConfiguracaoService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, sequenceName = "SEQ_CONHECIMENTODEFRETE",
        name = "SEQ_CONHECIMENTODEFRETE")
public class ConhecimentoDeFrete implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_CONHECIMENTODEFRETE", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Pessoa pessoa;

    @NotNull(message = "{operacao_not_null}")
    @ManyToOne
    private Operacao operacao;

    @Min(value = 0, message = "{valor_min}")
    @Max(value = 999999999, message = "{valor_max}")
    @Column(nullable = true)
    private BigDecimal valorFrete;

    @Min(value = 0, message = "{valor_min}")
    @Max(value = 999999999, message = "{valor_max}")
    @Column(nullable = true)
    private BigDecimal outrasdespesas;

    @Min(value = 0, message = "{valor_min}")
    @Max(value = 999999999, message = "{valor_max}")
    @Column(nullable = true)
    private BigDecimal dinheiro;

    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao = Calendar.getInstance().getTime();

    @OneToMany(mappedBy = "conhecimentoDeFrete", cascade = {CascadeType.ALL})
    private List<Titulo> titulo;

    @OneToMany(mappedBy = "conhecimentoDeFrete", cascade = {CascadeType.ALL})
    private List<NotaRecebida> notaRecebida;

    @OneToMany(mappedBy = "conhecimentoDeFrete", cascade = {CascadeType.ALL})
    private List<ValorPorCotacao> valorPorCotacao;

    public ConhecimentoDeFrete() {
    }

    public ConhecimentoDeFrete(Long id, Pessoa pessoa, Operacao operacao, BigDecimal valorFrete, BigDecimal despesas,
            BigDecimal dinheiro, Date data, Date emissao, List<Titulo> titulo, List<NotaRecebida> notaRecebida, List<ValorPorCotacao> valorPorCotacao) throws DadoInvalidoException {
        this.id = id;
        this.pessoa = pessoa;
        this.operacao = operacao;
        this.valorFrete = valorFrete;
        this.outrasdespesas = despesas;
        this.dinheiro = dinheiro;
        this.data = data;
        this.emissao = emissao;
        this.titulo = titulo;
        this.notaRecebida = notaRecebida;
        this.valorPorCotacao = valorPorCotacao;
        ehValido();
    }

    public void adiciona(Titulo t) {
        if (titulo == null) {
            titulo = new ArrayList<>();
        }
        t.setConhecimentoDeFrete(this);
        titulo.add(t);
    }

    public void atualiza(Titulo t) {
        if (titulo.contains(t)) {
            titulo.set(titulo.indexOf(t), t);
        } else {
            t.setConhecimentoDeFrete(this);
            titulo.add(t);
        }
    }

    public void remove(Titulo t) {
        titulo.remove(t);
    }

    public void adiciona(NotaRecebida n) {
        if (notaRecebida == null) {
            notaRecebida = new ArrayList<>();
        }
        n.setConhecimentoDeFrete(this);
        notaRecebida.add(n);
    }

    public void atualiza(NotaRecebida n) {
        if (notaRecebida.contains(n)) {
            notaRecebida.set(notaRecebida.indexOf(n), n);
        } else {
            n.setConhecimentoDeFrete(this);
            notaRecebida.add(n);
        }
    }

    public void remove(NotaRecebida n) {
        notaRecebida.remove(n);
    }

    public void adiciona(ValorPorCotacao b) {
        try {
            if (valorPorCotacao == null) {
                valorPorCotacao = new ArrayList<>();
            }
            b.geraBaixaPor(this);
            valorPorCotacao.add(b);
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void atualiza(ValorPorCotacao b) {
        try {
            if (valorPorCotacao.contains(b)) {
                valorPorCotacao.set(valorPorCotacao.indexOf(b), b);
            } else {
                b.geraBaixaPor(this);
                valorPorCotacao.add(b);
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void remove(ValorPorCotacao b) {
        valorPorCotacao.remove(b);
    }

    public Long getId() {
        return id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Operacao getOperacao() {
        return operacao;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public BigDecimal getOutrasdespesas() {
        return outrasdespesas;
    }

    public BigDecimal getDinheiro() {
        return dinheiro;
    }

    public Date getData() {
        return data;
    }

    public Date getEmissao() {
        return emissao;
    }

    public List<Titulo> getTitulo() {
        return titulo;
    }

    public List<NotaRecebida> getNotaRecebida() {
        return notaRecebida;
    }

    public List<ValorPorCotacao> getValorPorCotacao() {
        return valorPorCotacao;
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("valorFrete", "outrasdespesas", "dinheiro", "pessoa");
        new ValidadorDeCampos<ConhecimentoDeFrete>().valida(this, campos);
    }

    public Moeda getMoedaPadrao() throws EDadoInvalidoException {
        Configuracao cfg = new ConfiguracaoService().buscar();
        return cfg.getMoedaPadrao();
    }

    public String getValorFormatado() {
        NumberFormat numeroFormatado = NumberFormat.getCurrencyInstance();
        return numeroFormatado.format(getValorFrete());
    }

    public String getEmissaoFormatadaSemHoras() {
        SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy");
        return getEmissao() != null ? emissaoFormatada.format(getEmissao().getTime()) : "";
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof ConhecimentoDeFrete)) {
            return false;
        }
        ConhecimentoDeFrete outro = (ConhecimentoDeFrete) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
