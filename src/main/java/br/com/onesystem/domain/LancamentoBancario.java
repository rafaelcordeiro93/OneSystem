/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.domain.builder.BaixaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.BundleUtil;
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
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Rafael Cordeiro
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_DEPOSITOBANCARIO",
        sequenceName = "SEQ_DEPOSITOBANCARIO")
public class LancamentoBancario implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_DEPOSITOBANCARIO", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;

    @ManyToOne
    private TipoDespesa despesa;

    @ManyToOne
    private TipoReceita receita;

    @NotNull(message = "{conta_not_null}")
    @ManyToOne(optional = false)
    private Conta conta;

    @NotNull(message = "{valor_not_null}")
    @Min(value = 0, message = "{valor_min}")
    private BigDecimal valor;

    @OneToMany(mappedBy = "lancamentoBancario", cascade = {CascadeType.ALL})
    private List<Baixa> baixas;

    @Length(message = "{observacao_length}", max = 500)
    private String observacao;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "{tipo_lancamento_not_null}")
    private TipoLancamentoBancario tipoLancamentoBancario;

    @Column(nullable = true)
    private boolean estornado;

    @Column(nullable = true)
    private Long idRelacaoEstorno;

    public LancamentoBancario() {
    }

    public LancamentoBancario(Long id, Date emissao, Conta conta, BigDecimal valor, TipoReceita receita, TipoDespesa despesa, List<Baixa> baixas,
            String observacao, TipoLancamentoBancario tipoLancamentoBancario, boolean estornado, Long idRelacaoEstorno) throws DadoInvalidoException {
        this.id = id;
        this.emissao = emissao;
        this.conta = conta;
        this.receita = receita;
        this.despesa = despesa;
        this.valor = valor;
        this.baixas = baixas;
        this.observacao = observacao;
        this.tipoLancamentoBancario = tipoLancamentoBancario;
        this.estornado = estornado;
        this.idRelacaoEstorno = idRelacaoEstorno;
        ehValido();
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("conta", "valor", "observacao");
        new ValidadorDeCampos<LancamentoBancario>().valida(this, campos);
    }

    /* Deve ser utilizado para gerar a baixa do depósito */
    public void geraBaixaDeLancamento(Cotacao conta) throws DadoInvalidoException {
        if (receita != null) {
            adiciona(new BaixaBuilder().comValor(valor).comOperacaoFinanceira(OperacaoFinanceira.ENTRADA).comReceita(receita).comCotacao(conta).construir());
        } else {
            adiciona(new BaixaBuilder().comValor(valor).comOperacaoFinanceira(OperacaoFinanceira.SAIDA).comDespesa(despesa).comCotacao(conta).construir());
        }
    }

    /* Deve ser utilizado para gerar a baixa da transferência */
    public void geraEstornoDoLancamentoCom(Cotacao conta) throws DadoInvalidoException {
        if (receita != null) {
            adiciona(new BaixaBuilder().comValor(valor).comOperacaoFinanceira(OperacaoFinanceira.SAIDA).comReceita(receita).comCotacao(conta).construir());
        } else {
            adiciona(new BaixaBuilder().comValor(valor).comOperacaoFinanceira(OperacaoFinanceira.ENTRADA).comDespesa(despesa).comCotacao(conta).construir());
        }
    }

    public void setIdRelacaoEstorno(LancamentoBancario d) {
        this.idRelacaoEstorno = d.getId();
    }

    /* Adiciona Baixa*/
    private void adiciona(Baixa baixa) {
        try {
            BaixaBuilder b = new BaixaBuilder(baixa);
            if (baixas == null) {
                this.baixas = new ArrayList<>();
            }
            b.comLancamentoBancario(this);
            geraHistorico(b);
            b.comEmissao(emissao);
            this.baixas.add(b.construir());
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private void geraHistorico(BaixaBuilder b) {
        BundleUtil msg = new BundleUtil();
        if (this.tipoLancamentoBancario.equals(TipoLancamentoBancario.LANCAMENTO) && receita != null) {
            b.comHistorico(msg.getLabel("Lancamento") + " " + msg.getLabel("de") + " " + msg.getLabel("Receita") + " " + msg.getLabel("de") + " " + receita.getNome());
        } else if (this.tipoLancamentoBancario.equals(TipoLancamentoBancario.LANCAMENTO) && despesa != null) {
            b.comHistorico(msg.getLabel("Lancamento") + " " + msg.getLabel("de") + " " + msg.getLabel("Despesa") + " " + msg.getLabel("de") + " " + despesa.getNome());
        } else if (this.tipoLancamentoBancario.equals(TipoLancamentoBancario.ESTORNO) && despesa != null) {
            b.comHistorico(msg.getLabel("Estorno") + " " + msg.getLabel("de") + " " + msg.getLabel("Lancamento") + " " + msg.getLabel("de")
                    + " " + msg.getLabel("Despesa") + " " + msg.getLabel("de") + " " + despesa.getNome());
        } else if (this.tipoLancamentoBancario.equals(TipoLancamentoBancario.ESTORNO) && receita != null) {
            b.comHistorico(msg.getLabel("Estorno") + " " + msg.getLabel("de") + " " + msg.getLabel("Lancamento") + " " + msg.getLabel("de")
                    + " " + msg.getLabel("Receita") + " " + msg.getLabel("de") + " " + receita.getNome());
        }
    }

    public Long getId() {
        return id;
    }

    public Date getEmissao() {
        return emissao;
    }

    public TipoDespesa getDespesa() {
        return despesa;
    }

    public TipoReceita getReceita() {
        return receita;
    }

    public Conta getConta() {
        return conta;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public List<Baixa> getBaixas() {
        return baixas;
    }

    public String getObservacao() {
        return observacao;
    }

    public TipoLancamentoBancario getTipoLancamentoBancario() {
        return tipoLancamentoBancario;
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
        if (!(objeto instanceof LancamentoBancario)) {
            return false;
        }
        LancamentoBancario outro = (LancamentoBancario) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
