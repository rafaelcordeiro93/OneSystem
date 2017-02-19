package br.com.onesystem.domain;

import br.com.onesystem.dao.ConfiguracaoDAO;
import br.com.onesystem.dao.TituloDAO;
import br.com.onesystem.domain.builder.BaixaBuilder;
import br.com.onesystem.domain.builder.DespesaProvisionadaBuilder;
import br.com.onesystem.domain.builder.TituloBuilder;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoFormaPagRec;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_CAMBIO",
        sequenceName = "SEQ_CAMBIO")
public class Cambio implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_CAMBIO", strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    private ContratoDeCambio contrato;

    @ManyToOne
    private Cotacao cotacao;

    @NotNull(message = "{taxaEfetivada_not_null}")
    @Column(nullable = false)
    private BigDecimal taxaEfetivada;

    @NotNull(message = "{valorBruto_not_null}")
    @Column(nullable = false)
    private BigDecimal valorBruto;

    @Column(nullable = false)
    private BigDecimal porcentagemDeComissao;

    @Column(nullable = false)
    private BigDecimal porcentagemDeLucroEmTaxa;

    @NotNull(message = "{valorLiquido_not_null}")
    @Column(nullable = false)
    private BigDecimal valorLiquido;

    @Column
    private String processo;

    @NotNull(message = "{emissao_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao = new Date();

    @OneToMany(mappedBy = "cambio", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Baixa> pagamentos = new ArrayList<Baixa>();

    @OneToMany(mappedBy = "cambio", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Titulo> titulos = new ArrayList<Titulo>();

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private DespesaProvisionada comissao;

    @OneToMany(mappedBy = "cambio", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<DespesaProvisionada> divisaoLucro = new ArrayList<DespesaProvisionada>();

    @ManyToOne(optional = true)
    private Pessoa pessoaComissionada;

    private BigDecimal comissaoCalculada;

    public Cambio() {
    }

    public Cambio(Long id, ContratoDeCambio contrato, Cotacao cotacao, BigDecimal taxaEfetivada, BigDecimal valorBruto,
            BigDecimal valorLiquido, BigDecimal porcentagemDeComissao, String processo,
            Date emissao, Pessoa pessoaComissionada, BigDecimal comissaoCalculada,
            BigDecimal porcentagemDeLucroEmTaxa) throws DadoInvalidoException {
        this.id = id;
        this.contrato = contrato;
        this.cotacao = cotacao;
        this.taxaEfetivada = taxaEfetivada;
        this.valorBruto = valorBruto;
        this.valorLiquido = valorLiquido;
        this.porcentagemDeComissao = porcentagemDeComissao;
        this.comissaoCalculada = comissaoCalculada;
        this.pessoaComissionada = pessoaComissionada;
        this.processo = processo;
        this.emissao = emissao;
        this.porcentagemDeLucroEmTaxa = porcentagemDeLucroEmTaxa;
        ehValido();
    }

    public void baixarContasAPagar() throws DadoInvalidoException {
        List<Titulo> contasAPagar = new TituloDAO().buscarTitulos().wAPagar().eAbertas().ePorPessoa(contrato.getPessoa()).listaDeResultados();
        BigDecimal resto = contrato.getValorCalculado();

        for (Titulo tituloAPagar : contasAPagar) {
            if (resto.compareTo(BigDecimal.ZERO) == 0) {
                break;
            }
            if (tituloAPagar.getRecepcao() != null) {
                if (tituloAPagar.getSaldo().compareTo(resto) >= 0) {
                    tituloAPagar.atualizaSaldo(resto);
                    verificaNecessidadeBaixa();
                    titulos.add(tituloAPagar);
                    return;
                } else {
                    resto = resto.subtract(tituloAPagar.getSaldo());
                    tituloAPagar.atualizaSaldo(tituloAPagar.getSaldo());
                    verificaNecessidadeBaixa();
                    titulos.add(tituloAPagar);
                }
            }
        }
        if (resto.compareTo(BigDecimal.ZERO) == 1 && valorLiquido.compareTo(BigDecimal.ZERO) == 1) {
            gerarNovoTitulo(resto);
        } else if (valorLiquido.compareTo(BigDecimal.ZERO) == -1) {
            pagarBaixa();
        }

    }

    private void verificaNecessidadeBaixa() throws DadoInvalidoException {
        if (valorLiquido.compareTo(BigDecimal.ZERO) >= 0) {
            for (Baixa b : pagamentos) {
                if (b.getValor().equals(valorBruto)) {
                    return;
                }
            }
            pagarBaixa();
        }
    }

    private void pagarBaixa() throws DadoInvalidoException {
        Baixa baixa = new BaixaBuilder().comValor(valorBruto).comEmissao(emissao).comNaturezaFinanceira(OperacaoFinanceira.SAIDA)
                .comPessoa(contrato.getPessoa()).comCotacao(cotacao).construir();
        pagamentos.add(baixa);
    }

    public void dividirLucros(Despesa despesaDivisaoLucro, List<Pessoa> pessoaDivisaoLucro, Pessoa pessoaCaixa) throws DadoInvalidoException {
        if (valorLiquido.compareTo(BigDecimal.ZERO) == 1) {
            BigDecimal valorDividido = valorLiquido.divide(new BigDecimal(pessoaDivisaoLucro.size()), new MathContext(20, RoundingMode.HALF_UP));
            for (Pessoa pessoa : pessoaDivisaoLucro) {
                DespesaProvisionada despesa;
                DespesaProvisionadaBuilder builder = new DespesaProvisionadaBuilder().comPessoa(pessoa).comDespesa(despesaDivisaoLucro).comValor(valorBruto).comCambio(this)
                        .comCotacao(cotacao).comHistorico("Divisão de Lucros");
                if (pessoaCaixa.getId().equals(pessoa.getId())) {
                    despesa = builder.comDivisaoLucroCambioCaixa(true).construir();
                } else {
                    despesa = builder.comDivisaoLucroCambioCaixa(false).construir();
                }
                divisaoLucro.add(despesa);
            }
        }
    }

    private void gerarNovoTitulo(BigDecimal valor) throws DadoInvalidoException {
        Titulo novoTitulo = new TituloBuilder().comPessoa(contrato.getPessoa()).comValor(valor).
                comSaldo(valor).comEmissao(emissao).comOperacaoFinanceira(OperacaoFinanceira.SAIDA).
                comTipoFormaPagRec(TipoFormaPagRec.A_PRAZO).comCotacao(cotacao).construir();
        this.titulos.add(novoTitulo);
    }

    public void gerarComissao() throws DadoInvalidoException {
        if (comissaoCalculada != null && comissaoCalculada.compareTo(BigDecimal.ZERO) == 1) {
            Configuracao c = new ConfiguracaoDAO().buscar();
            comissao = new DespesaProvisionadaBuilder().comPessoa(pessoaComissionada).comValor(comissaoCalculada)
                    .comCotacao(cotacao).comCambio(this).comDespesa(c.getDespesaDeComissao()).construir();
        }
    }

    public List<Titulo> getTitulos() {
        return titulos;
    }

    public Long getId() {
        return id;
    }

    public ContratoDeCambio getContrato() {
        return contrato;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public BigDecimal getTaxaEfetivada() {
        return taxaEfetivada;
    }

    public BigDecimal getValorBruto() {
        return valorBruto;
    }

    public BigDecimal getValorLiquido() {
        return valorLiquido;
    }

    public Pessoa getPessoaComissionada() {
        return pessoaComissionada;
    }

    public BigDecimal getComissaoCalculada() {
        return comissaoCalculada;
    }

    public BigDecimal getPorcentagemDeComissao() {
        return porcentagemDeComissao;
    }

    public String getProcesso() {
        return processo;
    }

    public List<Baixa> getPagamentos() {
        return pagamentos;
    }

    public Date getEmissao() {
        return emissao;
    }

    public BigDecimal getPorcentagemDeLucroEmTaxa() {
        return porcentagemDeLucroEmTaxa;
    }

    public String getEmissaoFormatada() {
        SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy");
        return emissaoFormatada.format(getEmissao().getTime());
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("taxaEfetivada", "valorBruto", "valorLiquido", "emissao");
        new ValidadorDeCampos<Cambio>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Cambio)) {
            return false;
        }
        Cambio outro = (Cambio) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }
}
