package br.com.onesystem.domain;

import br.com.onesystem.domain.builder.BaixaBuilder;
import br.com.onesystem.domain.builder.TituloBuilder;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoFormaPagRec;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_RECEPCAO",
        sequenceName = "SEQ_RECEPCAO")
public class Recepcao implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_RECEPCAO", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Pessoa pessoa;

    @NotNull(message = "{valor_not_null}")
    @Max(message = "{valor_max}", value = 999999999)
    private BigDecimal valor;

    @NotNull(message = "{emissao_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao = new Date();

    @OneToOne(mappedBy = "recepcao", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Titulo titulo;

    @OneToMany(mappedBy = "recepcao", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Baixa> baixa;

    @NotNull(message = "cotacao_not_null")
    @ManyToOne(optional = false)
    private Cotacao cotacao;

    public Recepcao() {
    }

    public Recepcao(Long id, Pessoa pessoa, BigDecimal valor, Date emissao, Titulo titulo,
            Cotacao cotacao) throws DadoInvalidoException {
        this.id = id;
        this.pessoa = pessoa;
        this.valor = valor;
        this.emissao = emissao;
        this.titulo = titulo;
        this.cotacao = cotacao;
        ehValido();
    }

    public void gerarTitulo() throws DadoInvalidoException {
        Titulo novoTitulo = new TituloBuilder().comPessoa(pessoa).comValor(valor).comSaldo(valor).comEmissao(emissao).comOperacaoFinanceira(OperacaoFinanceira.SAIDA)
                .comTipoFormaPagRec(TipoFormaPagRec.A_PRAZO).comCotacao(cotacao).construir();
        this.titulo = novoTitulo;
    }

    public void gerarBaixa() throws DadoInvalidoException {
        if (baixa == null) {
            baixa = new ArrayList<Baixa>();
        }
        Baixa novaBaixa = new BaixaBuilder().comValor(valor).comEmissao(emissao).comNaturezaFinanceira(OperacaoFinanceira.ENTRADA)
                .comPessoa(pessoa).comCotacao(cotacao).construir();
        this.baixa.add(novaBaixa);
    }

    public Long getId() {
        return id;
    }

    public Date getEmissao() {
        return emissao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public List<Baixa> getBaixa() {
        return baixa;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getValorFormatado() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(valor);
    }

    public Titulo getTitulo() {
        return titulo;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("valor", "emissao", "moeda");
        new ValidadorDeCampos<Recepcao>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Recepcao)) {
            return false;
        }
        Recepcao outro = (Recepcao) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
