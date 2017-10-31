/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.reportTemplate.TemplateFormaPagamento;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.services.impl.MetodoInacessivelRelatorio;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.valueobjects.EstadoDeNota;
import br.com.onesystem.valueobjects.TipoItem;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author rauber
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "NOTA_CLASSE_NOME")
public abstract class Nota implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_NOTA", sequenceName = "SEQ_NOTA",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "SEQ_NOTA", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{pessoa_not_null}")
    @ManyToOne
    private Pessoa pessoa;
    @NotNull(message = "{operacao_not_null}")
    @ManyToOne
    private Operacao operacao;
    @ManyToOne
    private FormaDeRecebimento formaDeRecebimento;
    @ManyToOne
    private ListaDePreco listaDePreco;
    @Temporal(TemporalType.TIMESTAMP)
    protected Date emissao;
    @Enumerated(EnumType.STRING)
    private EstadoDeNota estado;
    @OneToMany(mappedBy = "nota", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<ValorPorCotacao> valorPorCotacao;
    @OneToMany(mappedBy = "nota", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<CobrancaVariavel> cobrancas;
    @NotNull(message = "{cotacao_not_null}")
    @ManyToOne(optional = false)
    private Cotacao cotacao;
    @OneToMany(mappedBy = "nota", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<ItemDeNota> itens;
    @Min(value = 0, message = "{valorDesconto_min}")
    @Column(nullable = true)
    private BigDecimal desconto;
    @Min(value = 0, message = "{valor_acrescimo_min}")
    @Column(nullable = true)
    private BigDecimal acrescimo = BigDecimal.ZERO;
    @Min(value = 0, message = "{valor_despesa_cobranca_min}")
    private BigDecimal despesaCobranca = BigDecimal.ZERO;
    @Min(value = 0, message = "{valor_frete_min}")
    private BigDecimal frete = BigDecimal.ZERO;
    @Min(value = 0, message = "{min_aFaturar}")
    private BigDecimal aFaturar = BigDecimal.ZERO;
    @Min(value = 0, message = "{min_dinheiro}")
    private BigDecimal totalEmDinheiro = BigDecimal.ZERO;
    @OneToOne
    private Nota notaDeOrigem;
    @ManyToOne(optional = false)
    private Caixa caixa;
    @ManyToOne
    private Usuario usuario;
    @NotNull(message = "{filial_not_null}")
    @ManyToOne(optional = false)
    private Filial filial;
    @NotNull(message = "{numero_nf_not_null}")
    @Column(nullable = false)
    protected Long numeroNF;

    public Nota() {
        emissao = new Date(); // Necesário para construção do estoque.
    }

    public Nota(Long id, Pessoa pessoa, Operacao operacao, List<ItemDeNota> itens,
            FormaDeRecebimento formaDeRecebimento, ListaDePreco listaDePreco,
            List<CobrancaVariavel> cobrancas, Cotacao cotacao, List<ValorPorCotacao> valorPorCotacao, BigDecimal desconto,
            BigDecimal acrescimo, BigDecimal despesaCobranca, BigDecimal frete, BigDecimal aFaturar,
            BigDecimal totalEmDinheiro, Nota notaDeOrigem, Date emissao, Caixa caixa, Usuario usuario, Filial filial, Long numeroNF) throws DadoInvalidoException {
        this.emissao = emissao == null ? new Date() : emissao; // Necesário para construção do estoque.
        this.id = id;
        this.pessoa = pessoa;
        this.operacao = operacao;
        this.formaDeRecebimento = formaDeRecebimento;
        this.listaDePreco = listaDePreco;
        this.estado = EstadoDeNota.EM_DEFINICAO;
        this.cobrancas = cobrancas;
        this.cotacao = cotacao;
        this.valorPorCotacao = valorPorCotacao;
        this.desconto = desconto;
        this.acrescimo = acrescimo;
        this.despesaCobranca = despesaCobranca;
        this.frete = frete;
        this.aFaturar = aFaturar;
        this.totalEmDinheiro = totalEmDinheiro;
        this.itens = itens;
        this.notaDeOrigem = notaDeOrigem;
        this.caixa = caixa;
        this.usuario = usuario;
        this.filial = filial;
        this.numeroNF = numeroNF;
        if (id == null) {
            geraBaixaPorValorDeCotacao();
            geraCobrancas();
        }
        ehValido();
    }

    
    protected abstract void adicionaNotaNoItem() throws DadoInvalidoException;

    public void adiciona(ValorPorCotacao valorPorCotacao) throws DadoInvalidoException {
        valorPorCotacao.geraBaixaPor(this);
        this.valorPorCotacao.add(valorPorCotacao);
    }

    public void atualiza(ValorPorCotacao valor) throws DadoInvalidoException {
        for (ValorPorCotacao v : valorPorCotacao) {
            if (v.getCotacao().getConta().getMoeda().equals(v.getCotacao().getConta().getMoeda())) {
                v.atualizaValor(valor.getValor());
                v.atualizaValorDeBaixa(valor.getValor());
            }
        }
    }

    public void adiciona(CobrancaVariavel cobranca) throws DadoInvalidoException {
        cobranca.geraPara(this);
        this.cobrancas.add(cobranca);
    }

    public void atualiza(CobrancaVariavel cobranca) throws DadoInvalidoException {
        this.cobrancas.set(cobrancas.indexOf(cobranca), cobranca);
    }

    public void remove(CobrancaVariavel cobranca) {
        this.cobrancas.remove(cobranca);
    }

    private void geraBaixaPorValorDeCotacao() throws DadoInvalidoException {
        for (ValorPorCotacao v : valorPorCotacao) {
            v.geraBaixaPor(this);
        }
    }

    private void geraCobrancas() {
        cobrancas.forEach((c) -> {
            c.geraPara(this);
        });
    }

    public void cancela() {
        estado = EstadoDeNota.CANCELADO;
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("pessoa", "cotacao", "operacao", "filial");
        new ValidadorDeCampos<>().valida(this, campos);
    }

    public BigDecimal getTotalItens() {
        return itens.stream().map(ItemDeNota::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<ItemDeNota> getItens() {
        return itens;
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

    public Caixa getCaixa() {
        return caixa;
    }

    public FormaDeRecebimento getFormaDeRecebimento() {
        return formaDeRecebimento;
    }

    public ListaDePreco getListaDePreco() {
        return listaDePreco;
    }

    public Date getEmissao() {
        return emissao;
    }

    @MetodoInacessivelRelatorio
    public List<TemplateFormaPagamento> getTemplateFormaPagamento() {
        List<TemplateFormaPagamento> lista = new ArrayList<>();
        int id = 0;
        for (ValorPorCotacao v : valorPorCotacao) {
            lista.add(new TemplateFormaPagamento(id, v.getCotacao().getConta().getMoeda().getNome(), null, null, v.getValorFormatado(), v.getValorNaMoedaPadraoFormatado()));
            id++;
        }
        List<CobrancaVariavel> entradas = cobrancas.stream().filter(c -> c.getEntrada() == true).sorted(Comparator.comparing(c -> c.getVencimento())).collect(Collectors.toList());
        List<CobrancaVariavel> parcelas = cobrancas.stream().filter(c -> c.getEntrada() == false).sorted(Comparator.comparing(c -> c.getParcela())).collect(Collectors.toList());

        for (CobrancaVariavel c : entradas) {
            lista.add(new TemplateFormaPagamento(id, c.getModalidade().getNome(), c.getDetalhes(), c.getVencimentoFormatadoSemHoras(), c.getValorFormatado(), c.getValorNaMoedaPadraoFormatado()));
            id++;
        }

        for (CobrancaVariavel c : parcelas) {
            lista.add(new TemplateFormaPagamento(id, c.getModalidade().getNome(), c.getDetalhes(), c.getVencimentoFormatadoSemHoras(), c.getValorFormatado(), c.getValorNaMoedaPadraoFormatado()));
            id++;
        }

        return lista;
    }

    public String getEmissaoFormatada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(emissao);
    }

    public Nota getNotaDeOrigem() {
        return notaDeOrigem;
    }

    public EstadoDeNota getEstado() {
        return estado;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public List<CobrancaVariavel> getCobrancas() {
        return cobrancas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Filial getFilial() {
        return filial;
    }

    public List<CobrancaVariavel> getParcelas() {
        if (cobrancas != null) {
            List<CobrancaVariavel> parcelamento = this.cobrancas.stream().filter(p -> p.getEntrada() != true).collect(Collectors.toList());
            parcelamento.sort(Comparator.comparingLong(CobrancaVariavel::getDias));
            return parcelamento;
        } else {
            return null;
        }
    }

    public List<CobrancaVariavel> getEntradas() {
        if (cobrancas != null) {
            List<CobrancaVariavel> entradas = cobrancas.stream().filter(p -> p.getEntrada() == true).collect(Collectors.toList());
            entradas.sort(Comparator.comparingLong(CobrancaVariavel::getDias));
            return entradas;
        } else {
            return null;
        }
    }

    public String getTotalItensFormatado() {
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), getTotalItens());
    }

    public BigDecimal getTotalEmMercadorias() {
        return itens.stream().filter(i -> i.getItem().getTipoItem() != TipoItem.SERVICO).map(ItemDeNota::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getTotalEmMercadoriasFormatado() {
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), getTotalEmMercadorias());
    }

    public BigDecimal getTotalEmServicos() {
        return itens.stream().filter(i -> i.getItem().getTipoItem() == TipoItem.SERVICO).map(ItemDeNota::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getTotalEmServicosFormatado() {
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), getTotalEmServicos());
    }

    public String getTotalParcelasFormatado() {
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), getTotalParcelas());
    }

    public String getAcrescimoFormatado() {
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), getAcrescimo());
    }

    public String getTotalEmDinheiroFormatado() {
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), getTotalEmDinheiro());
    }

    public String getDescontoFormatado() {
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), getDesconto());
    }

    public String getDespesaCobrancaFormatado() {
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), getDespesaCobranca());
    }

    public String getFreteFormatado() {
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), getFrete());
    }

    public BigDecimal getTotalParcelas() {
        if (cobrancas != null) {
            return cobrancas.stream().filter(c -> c.getEntrada() == null || c.getEntrada() == false).map(CobrancaVariavel::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal getTotalCartaoDeEntrada() {
        BigDecimal total = BigDecimal.ZERO;
        try {
            for (CobrancaVariavel c : cobrancas) {
                if (c instanceof BoletoDeCartao && c.getEntrada() != null && c.getEntrada() == true) {
                    total = total.add(c.getValor());
                }
            }
        } catch (NullPointerException npe) {
            return null;
        }
        return total.compareTo(BigDecimal.ZERO) == 0 ? null : total;
    }

    public String getTotalCartaoDeEntradaFormatado() {
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), getTotalCartaoDeEntrada());
    }

    public BigDecimal getTotalChequeDeEntrada() {
        BigDecimal total = BigDecimal.ZERO;
        try {
            for (CobrancaVariavel c : cobrancas) {
                if (c instanceof Cheque && c.getEntrada() != null && c.getEntrada() == true) {
                    if (c.getCotacao() != null && c.getCotacao().getConta().getMoeda() != cotacao.getConta().getMoeda()) {
                        total = total.add(c.getValor().divide(c.getCotacao().getValor(), 2, BigDecimal.ROUND_UP));
                    } else {
                        total = total.add(c.getValor());
                    }
                }
            }
        } catch (NullPointerException npe) {
            return null;
        }
        return total.compareTo(BigDecimal.ZERO) == 0 ? null : total;
    }

    public String getTotalChequeDeEntradaFormatado() {
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), getTotalChequeDeEntrada());
    }

    public void setParcelas(List<CobrancaVariavel> parcelas) {
        this.cobrancas = parcelas;
    }

    public String getTotalFormatado() {
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), getTotalNota());
    }

    public BigDecimal getTotalNota() {
        BigDecimal a = acrescimo == null ? BigDecimal.ZERO : acrescimo;
        BigDecimal f = frete == null ? BigDecimal.ZERO : frete;
        BigDecimal c = despesaCobranca == null ? BigDecimal.ZERO : despesaCobranca;
        BigDecimal d = desconto == null ? BigDecimal.ZERO : desconto;
        return getTotalItens().add(a.add(f.add(c))).subtract(d);
    }

    public List<ValorPorCotacao> getValorPorCotacao() {
        return valorPorCotacao;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public BigDecimal getAcrescimo() {
        return acrescimo;
    }

    public BigDecimal getDespesaCobranca() {
        return despesaCobranca;
    }

    public BigDecimal getFrete() {
        return frete;
    }

    public Credito getCredito() {
        List<CobrancaVariavel> credito = cobrancas.stream().filter(c -> c instanceof Credito).filter(c -> c.getEntrada() == true).collect(Collectors.toList());
        if (credito != null && !credito.isEmpty()) {
            return (Credito) credito.get(0);
        } else {
            return null;
        }
    }

    public BoletoDeCartao getBoletoDeCartaoEntrada() {
        List<CobrancaVariavel> boletoDeCartao = cobrancas.stream().filter(c -> c instanceof BoletoDeCartao).filter(c -> c.getEntrada() == true).collect(Collectors.toList());
        if (boletoDeCartao != null && !boletoDeCartao.isEmpty()) {
            return (BoletoDeCartao) boletoDeCartao.get(0);
        } else {
            return null;
        }
    }

    public BigDecimal getaFaturar() {
        return aFaturar;
    }

    public BigDecimal getTotalEmDinheiro() {
        return totalEmDinheiro;
    }

    public Long getNumeroNF() {
        return numeroNF;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Nota)) {
            return false;
        }
        Nota outro = (Nota) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
