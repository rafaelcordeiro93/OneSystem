/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.GerenciadorDeOrcamentos;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.MoedaFomatter;
import br.com.onesystem.valueobjects.EstadoDeOrcamento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author rauber
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_ORCAMENTO",
        name = "SEQ_ORCAMENTO")
public class Orcamento implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_ORCAMENTO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{pessoa_not_null}")
    @ManyToOne
    private Pessoa pessoa;
    @NotNull(message = "{forma_recebimento_not_null}")
    @ManyToOne
    private FormaDeRecebimento formaDeRecebimento;
    @ManyToOne
    private ListaDePreco listaDePreco;
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;
    @NotNull(message = "{cotacao_not_null}")
    @ManyToOne
    private Cotacao cotacao;
    @OneToMany(mappedBy = "orcamento", cascade = {CascadeType.ALL})
    private List<ItemOrcado> itensOrcados;
    @Temporal(TemporalType.TIMESTAMP)
    private Date validade;
    @Length(max = 1000, min = 0, message = "{observacao_lenght}")
    @Column(length = 1000)
    private String observacao;
    @Enumerated(EnumType.STRING)
    private EstadoDeOrcamento estado;
    @Min(value = 0, message = "{valorDesconto_min}")
    @Column(nullable = true)
    private BigDecimal desconto;
    @Min(value = 0, message = "{valor_acrescimo_min}")
    @Column(nullable = true)
    private BigDecimal acrescimo;
    @Min(value = 0, message = "{valor_despesa_cobranca_min}")
    private BigDecimal despesaCobranca;
    @Min(value = 0, message = "{valor_frete_min}")
    private BigDecimal frete;
    @OneToMany(mappedBy = "orcamento", cascade = CascadeType.ALL)
    private List<HistoricoDeOrcamento> historicosDeOrcamento;

    public Orcamento() {
        this.historicosDeOrcamento = new ArrayList<>();
        this.estado = EstadoDeOrcamento.EM_DEFINICAO;
    }

    public Orcamento(Long id, Pessoa pessoa, FormaDeRecebimento formaDeRecebimento,
            ListaDePreco listaDePreco, Cotacao cotacao, List<ItemOrcado> itemOrcado, Date validade, String observacao,
            BigDecimal desconto, BigDecimal acrescimo, BigDecimal despesaCobranca, BigDecimal frete) throws DadoInvalidoException {
        this.historicosDeOrcamento = new ArrayList<>();
        this.id = id;
        this.pessoa = pessoa;
        this.formaDeRecebimento = formaDeRecebimento;
        this.listaDePreco = listaDePreco;
        this.cotacao = cotacao;
        this.validade = validade;
        this.observacao = observacao;
        this.desconto = desconto;
        this.acrescimo = acrescimo;
        this.despesaCobranca = despesaCobranca;
        this.frete = frete;
        this.emissao = new Date();
        this.estado = EstadoDeOrcamento.EM_DEFINICAO;
        this.itensOrcados = itemOrcado;
        geraItensOrcados(itemOrcado);
        ehValido();
    }

    private void geraItensOrcados(List<ItemOrcado> itemOrcado) {
        itemOrcado.forEach(i -> {
            i.setOrcamento(this);
        });
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("pessoa", "formaDeRecebimento", "observacao", "cotacao", "desconto", "acrescimo", "despesaCobranca", "frete");
        new ValidadorDeCampos<>().valida(this, campos);
    }

    public void redefinir(String historico) throws DadoInvalidoException {
        new GerenciadorDeOrcamentos().redefinir(this, historico);
    }

    public void enviaParaAprovacao(String historico) throws DadoInvalidoException {
        new GerenciadorDeOrcamentos().enviarParaAprovacao(this, historico);
    }

    public void aprova(String historico) throws DadoInvalidoException {
        new GerenciadorDeOrcamentos().aprova(this, historico);
    }

    public void reprova(String historico) throws DadoInvalidoException {
        new GerenciadorDeOrcamentos().reprova(this, historico);
    }

    public void cancela(String historico) throws DadoInvalidoException {
        new GerenciadorDeOrcamentos().cancela(this, historico);
    }

    public void efetiva(String historico) throws DadoInvalidoException {
        new GerenciadorDeOrcamentos().efetiva(this, historico);
    }

    public List<HistoricoDeOrcamento> getHistoricosDeOrcamento() {
        if (historicosDeOrcamento != null) {
            return Collections.unmodifiableList(historicosDeOrcamento);
        } else {
            return null;
        }
    }

    public void adiciona(HistoricoDeOrcamento historicoDeOrcamento) {
        historicosDeOrcamento.add(historicoDeOrcamento);
        estado = historicoDeOrcamento.getEstado();
    }

    public String getAcrescimoFormatado() {
        if (cotacao != null) {
            return MoedaFomatter.format(cotacao.getConta().getMoeda(), getAcrescimo());
        } else {
            return MoedaFomatter.format(getAcrescimo());
        }
    }

    public String getDescontoFormatado() {
        if (cotacao != null) {
            return MoedaFomatter.format(cotacao.getConta().getMoeda(), getDesconto());
        } else {
            return MoedaFomatter.format(getDesconto());
        }
    }

    public String getDespesaCobrancaFormatado() {
        if (cotacao != null) {
            return MoedaFomatter.format(cotacao.getConta().getMoeda(), getDespesaCobranca());
        } else {
            return MoedaFomatter.format(getDespesaCobranca());
        }
    }

    public String getFreteFormatado() {
        if (cotacao != null) {
            return MoedaFomatter.format(cotacao.getConta().getMoeda(), getFrete());
        } else {
            return MoedaFomatter.format(getFrete());
        }
    }

    public Long getId() {
        return id;
    }

    public Pessoa getPessoa() {
        return pessoa;
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

    public Cotacao getCotacao() {
        return cotacao;
    }

    public List<ItemOrcado> getItensOrcados() {
        itensOrcados.sort(Comparator.comparing(ItemOrcado::getId));
        return itensOrcados;
    }

    public BigDecimal getTotalItens() {
        return itensOrcados.stream().map(ItemOrcado::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getTotalItensFormatado() {
        return MoedaFomatter.format(cotacao.getConta().getMoeda(), getTotalItens());
    }

    public String getTotalOrcamentoFormatado() {
        return MoedaFomatter.format(cotacao.getConta().getMoeda(), getTotalOrcamento());
    }

    public BigDecimal getTotalOrcamento() {
        BigDecimal a = getAcrescimo() == null ? BigDecimal.ZERO : getAcrescimo();
        BigDecimal f = getFrete() == null ? BigDecimal.ZERO : getFrete();
        BigDecimal c = getDespesaCobranca() == null ? BigDecimal.ZERO : getDespesaCobranca();
        BigDecimal d = getDesconto() == null ? BigDecimal.ZERO : getDesconto();
        return getTotalItens().add(a.add(f.add(c))).subtract(d);
    }

    public Date getValidade() {
        return validade;
    }

    public String getObservacao() {
        return observacao;
    }

    public EstadoDeOrcamento getEstado() {
        return estado;
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

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Orcamento)) {
            return false;
        }
        Orcamento outro = (Orcamento) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Orcamento{" + "id=" + id + ", pessoa=" + pessoa + ", formaDeRecebimento=" + formaDeRecebimento + ", listaDePreco=" + listaDePreco + ", emissao=" + emissao + ", cotacao=" + cotacao + ", validade=" + validade + ", observacao=" + observacao + ", estado=" + estado + ", desconto=" + desconto + ", acrescimo=" + acrescimo + ", despesaCobranca=" + despesaCobranca + ", frete=" + frete + '}';
    }

}
