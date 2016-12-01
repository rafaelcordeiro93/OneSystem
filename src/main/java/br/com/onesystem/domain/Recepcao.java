package br.com.onesystem.domain;

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

    @ManyToOne
    private Conta conta;

    @OneToOne(mappedBy = "recepcao", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Titulo titulo;

    @OneToMany(mappedBy = "recepcao", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Baixa> baixa;

    @NotNull(message = "moeda_not_null")
    @ManyToOne(optional = false)
    private Moeda moeda;

    public Recepcao() {
    }

    public Recepcao(Long id, Pessoa pessoa, BigDecimal valor, Conta conta, Date emissao, Titulo titulo,
            Moeda moeda) throws DadoInvalidoException {
        this.id = id;
        this.pessoa = pessoa;
        this.valor = valor;
        this.conta = conta;
        this.emissao = emissao;
        this.titulo = titulo;
        this.moeda = moeda;
        ehValido();
    }

    public void gerarTitulo() throws DadoInvalidoException {
        Titulo novoTitulo = new Titulo(null, pessoa, null, valor, valor,
                emissao, OperacaoFinanceira.SAIDA, TipoFormaPagRec.A_PRAZO, null, this, null, null, moeda);
        this.titulo = novoTitulo;
    }

    public void gerarBaixa() throws DadoInvalidoException {
        if (baixa == null) {
            baixa = new ArrayList<Baixa>();
        }
        Baixa novaBaixa = new Baixa(null, 0, false, BigDecimal.ZERO, valor, BigDecimal.ZERO, BigDecimal.ZERO,
                emissao, null, OperacaoFinanceira.ENTRADA, pessoa, null, conta, null, null, null, null, this, null, null);
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
    
    public Conta getConta() {
        return conta;
    }

    public Titulo getTitulo() {
        return titulo;
    }

    public Moeda getMoeda() {
        return moeda;
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
