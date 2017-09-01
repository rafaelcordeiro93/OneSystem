/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.dao.BaixaDAO;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.reportTemplate.TemplateFormaPagamento;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.services.impl.MetodoInacessivelRelatorio;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.valueobjects.EstadoDeLancamento;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "MOVIMENTO_CLASSE_NOME")
public abstract class Movimento implements Serializable {

    @Id
    @SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_MOVIMENTO",
            sequenceName = "SEQ_MOVIMENTO")
    @GeneratedValue(generator = "SEQ_MOVIMENTO", strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(mappedBy = "movimento", cascade = CascadeType.ALL)
    private List<TipoDeCobranca> tipoDeCobranca;

    @OneToMany(mappedBy = "movimento", cascade = CascadeType.ALL)
    private List<FormaDeCobranca> formasDeCobranca;

    private BigDecimal totalEmDinheiro = BigDecimal.ZERO;

    @ManyToOne
    private Cotacao cotacaoPadrao;

    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;

    @Enumerated(EnumType.STRING)
    private EstadoDeLancamento estado;

    @NotNull(message = "{caixa_not_null}")
    @ManyToOne(optional = false)
    private Caixa caixa;

    @NotNull(message = "{filial_not_null}")
    @ManyToOne(optional = false)
    private Filial filial;

    @OneToMany(mappedBy = "movimento", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<ValorPorCotacao> valorPorCotacao;

    public Movimento() {
    }

    public Movimento(Long id, List<TipoDeCobranca> tipoDeCobranca, List<FormaDeCobranca> formasDeCobranca, Cotacao cotacaoPadrao, Date emissao, EstadoDeLancamento estado, Caixa caixa, Filial filial, List<ValorPorCotacao> valorPorCotacao) throws DadoInvalidoException {
        this.id = id;
        this.tipoDeCobranca = tipoDeCobranca;
        this.formasDeCobranca = formasDeCobranca;
        this.cotacaoPadrao = cotacaoPadrao;
        this.emissao = emissao;
        this.estado = estado;
        this.caixa = caixa;
        this.filial = filial;
        this.valorPorCotacao = valorPorCotacao;
        ehValido();
    }

    public void ehRegistroValido() throws DadoInvalidoException {
        if ((tipoDeCobranca == null || tipoDeCobranca.isEmpty()) && (formasDeCobranca == null || formasDeCobranca.isEmpty())) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("Deve_possuir_registros_informados"));
        }
    }

    private final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("caixa", "filial");
        new ValidadorDeCampos<>().valida(this, campos);
    }

    public void adiciona(ValorPorCotacao valorPorCotacao) throws DadoInvalidoException {
        valorPorCotacao.geraBaixaPor(this);
        this.valorPorCotacao.add(valorPorCotacao);
    }

    public void adiciona(TipoDeCobranca tipo) {
        if (tipoDeCobranca == null) {
            tipoDeCobranca = new ArrayList<>();
        }
        tipo.setMovimento(this);
        tipoDeCobranca.add(tipo);
    }

    public void atualiza(TipoDeCobranca tipo) {
        tipoDeCobranca.set(tipoDeCobranca.indexOf(tipo), tipo);
    }

    public void remove(TipoDeCobranca tipo) {
        tipoDeCobranca.remove(tipo);
    }

    public void adiciona(FormaDeCobranca forma) {
        if (formasDeCobranca == null) {
            formasDeCobranca = new ArrayList<>();
        }
        forma.setMovimento(this);
        formasDeCobranca.add(forma);
    }

    public void atualiza(FormaDeCobranca forma) {
        formasDeCobranca.set(formasDeCobranca.indexOf(forma), forma);
    }

    public void atualizaBaixas(FormaDeCobranca forma) {
        List<Baixa> bx = new BaixaDAO().ePorFormaDeCobranca(forma).listaDeResultados();
        bx.forEach((b) -> {
            b.atualizaValor(forma.getValor());
        });
    }

    public void atualizaBaixas(TipoDeCobranca tipo) {
        List<Baixa> bx = new BaixaDAO().ePorTipoDeCobranca(tipo).listaDeResultados();
        bx.forEach((b) -> {
            b.atualizaValor(tipo.getValor());
        });
    }

    public void remove(FormaDeCobranca forma) {
        formasDeCobranca.remove(forma);
    }

    public void geraBaixas() {
        if (tipoDeCobranca != null) {
            tipoDeCobranca.forEach(t -> t.geraBaixas());
        }
        if (formasDeCobranca != null) {
            formasDeCobranca.forEach(f -> f.geraBaixas());
        }
    }

    public void efetiva() {
        estado = EstadoDeLancamento.EFETIVADO;
    }

    public void efetivaBaixas() throws DadoInvalidoException {
        for (TipoDeCobranca t : tipoDeCobranca) {
            t.descancelar();
        }
        for (FormaDeCobranca f : formasDeCobranca) {
            f.descancelar();
        }
    }

    public void cancela() throws DadoInvalidoException {
        estado = EstadoDeLancamento.CANCELADO;
        for (TipoDeCobranca t : tipoDeCobranca) {
            t.cancela();
        }
        for (FormaDeCobranca f : formasDeCobranca) {
            f.cancela();
        }
    }

    @MetodoInacessivelRelatorio
    public List<TemplateFormaPagamento> getTemplateFormaPagamento() {
        List<TemplateFormaPagamento> forma = new ArrayList<>();
        int i = 0;
        if (totalEmDinheiro.compareTo(BigDecimal.ZERO) != 0) {
            forma.add(new TemplateFormaPagamento(i, cotacaoPadrao.getConta().getNome(), null, getTotalEmDinheiroFormatado(), getTotalEmDinheiroFormatado()));
            i++;
        }
        if (formasDeCobranca != null && !formasDeCobranca.isEmpty()) {
            for (FormaDeCobranca f : formasDeCobranca) {
                forma.add(new TemplateFormaPagamento(i, f.getCobranca().getModalidade().getNome(), f.getCobranca().getVencimentoFormatadoSemHoras(), f.getTotalFormatado(), f.getTotalNaMoedaPadraoFormatado()));
                i++;
            }
        }
        return forma;
    }

    public String getEmissaoFormatadaSemHoras() {
        SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy");
        return getEmissao() != null ? emissaoFormatada.format(getEmissao().getTime()) : "";
    }

    public String getTotalNaMoedaPadraoPorExtenso() {
        return StringUtils.primeiraLetraMaiusculaAposEspaco(MoedaFormatter.valorPorExtenso(cotacaoPadrao.getConta().getMoeda(), getTotalNaMoedaPadrao()));
    }

    public String getTotalEmDinheiroFormatado() {
        return MoedaFormatter.format(cotacaoPadrao.getConta().getMoeda(), totalEmDinheiro);
    }

    public String getTotalNaMoedaPadraoFormatado() {
        return MoedaFormatter.format(cotacaoPadrao.getConta().getMoeda(), getTotalNaMoedaPadrao());
    }

    public BigDecimal getTotalNaMoedaPadrao() {
        return tipoDeCobranca.stream().map(TipoDeCobranca::getTotalNaMoedaPadrao).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Long getId() {
        return id;
    }

    public List<TipoDeCobranca> getTipoDeCobranca() {
        return tipoDeCobranca;
    }

    public List<FormaDeCobranca> getFormasDeCobranca() {
        return formasDeCobranca;
    }

    public BigDecimal getTotalEmDinheiro() {
        return totalEmDinheiro;
    }

    public Cotacao getCotacaoPadrao() {
        return cotacaoPadrao;
    }

    public Date getEmissao() {
        return emissao;
    }

    public EstadoDeLancamento getEstado() {
        return estado;
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public Filial getFilial() {
        return filial;
    }

    public List<ValorPorCotacao> getValorPorCotacao() {
        return valorPorCotacao;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Movimento other = (Movimento) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
