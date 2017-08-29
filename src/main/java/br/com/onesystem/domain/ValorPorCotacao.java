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
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.faces.context.FacesContext;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(sequenceName = "SEQ_VALOREMESPECIE", name = "SEQ_VALOREMESPECIE",
        initialValue = 1, allocationSize = 1)
public class ValorPorCotacao implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_VALOREMESPECIE", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "{cotacao_not_null}")
    @ManyToOne
    private Cotacao cotacao;

    @NotNull(message = "{valor_not_null}")
    @Min(value = 0, message = "{min_valor}")
    private BigDecimal valor;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "valorPorCotacao")
    private Baixa baixa;

    @ManyToOne
    private Nota nota;

    @ManyToOne
    private FaturaEmitida faturaEmitida;

    @ManyToOne
    private FaturaRecebida faturaRecebida;

    @ManyToOne
    private ConhecimentoDeFrete conhecimentoDeFrete;

    public ValorPorCotacao() {
    }

    public ValorPorCotacao(Long id, Cotacao cotacao, BigDecimal valor, FaturaEmitida faturaEmitida, FaturaRecebida faturaRecebida, ConhecimentoDeFrete conhecimentoDeFrete) throws DadoInvalidoException {
        this.id = id;
        this.cotacao = cotacao;
        this.valor = valor;
        this.faturaEmitida = faturaEmitida;
        this.faturaRecebida = faturaRecebida;
        this.conhecimentoDeFrete = conhecimentoDeFrete;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("valor", "cotacao");
        new ValidadorDeCampos<>().valida(this, campos);
    }

    public void geraBaixaPor(Nota nota) throws DadoInvalidoException {
        BundleUtil msg = new BundleUtil();
        String historico = nota instanceof NotaEmitida ? msg.getLabel("Nota_Emitida") : msg.getLabel("Nota_Recebida")
                + " " + msg.getMessage("de") + " " + nota.getPessoa().getNome();

        this.nota = nota;
        baixa = new BaixaBuilder().comFilial(nota.getFilial()).comCotacao(cotacao).comEmissao(nota.getEmissao()).comHistorico(historico)
                .comOperacaoFinanceira(nota.getOperacao().getOperacaoFinanceira()).comPessoa(nota.getPessoa())
                .comReceita(nota.getOperacao().getVendaAVista()).comValor(valor).comCaixa(nota.getCaixa())
                .comValorPorCotacao(this).construir();
    }

    public void geraBaixaPor(FaturaEmitida faturaEmitida) throws DadoInvalidoException {
        setFaturaEmitida(faturaEmitida);
        BundleUtil msg = new BundleUtil();
        String historico = msg.getMessage("Fatura_Emitida") + " " + msg.getMessage("de") + " " + faturaEmitida.getPessoa().getNome();
        Caixa caixa = (Caixa) SessionUtil.getObject("caixa", FacesContext.getCurrentInstance());
        baixa = new BaixaBuilder().comFilial(faturaEmitida.getFilial()).comCotacao(cotacao).comEmissao(faturaEmitida.getEmissao()).comHistorico(historico)
                .comOperacaoFinanceira(OperacaoFinanceira.ENTRADA).comPessoa(faturaEmitida.getPessoa())
                .comValor(valor).comCaixa(caixa).comValorPorCotacao(this).construir();
    }

    public void geraBaixaPor(FaturaRecebida faturaRecebida) throws DadoInvalidoException {
        setFaturaRecebida(faturaRecebida);
        BundleUtil msg = new BundleUtil();
        String historico = msg.getMessage("Fatura_Recebida") + " " + msg.getMessage("de") + " " + faturaRecebida.getPessoa().getNome();
        Caixa caixa = (Caixa) SessionUtil.getObject("caixa", FacesContext.getCurrentInstance());
        baixa = new BaixaBuilder().comFilial(faturaRecebida.getFilial()).comCotacao(cotacao).comEmissao(faturaRecebida.getEmissao()).comHistorico(historico)
                .comOperacaoFinanceira(OperacaoFinanceira.SAIDA).comPessoa(faturaRecebida.getPessoa())
                .comValor(valor).comCaixa(caixa).comValorPorCotacao(this).construir();
    }

    public void geraBaixaPor(ConhecimentoDeFrete conhecimentoDeFrete) throws DadoInvalidoException {
        setConhecimentoDeFrete(conhecimentoDeFrete);
        BundleUtil msg = new BundleUtil();
        String historico = msg.getMessage("Conhecimento_De_Frete") + " " + msg.getMessage("de") + " " + conhecimentoDeFrete.getPessoa().getNome();
        Caixa caixa = (Caixa) SessionUtil.getObject("caixa", FacesContext.getCurrentInstance());
        baixa = new BaixaBuilder().comFilial(conhecimentoDeFrete.getFilial()).comCotacao(cotacao).comEmissao(conhecimentoDeFrete.getEmissao()).comHistorico(historico)
                .comOperacaoFinanceira(conhecimentoDeFrete.getOperacao().getOperacaoFinanceira()).comPessoa(conhecimentoDeFrete.getPessoa())
                .comValor(valor).comCaixa(caixa).comValorPorCotacao(this).construir();
    }

    public Long getId() {
        return id;
    }

    public void atualizaValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void atualizaValorDeBaixa(BigDecimal valor) {
        baixa.atualizaValor(valor);
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getValorFormatado() {
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), getValor());
    }

    public Nota getNota() {
        return nota;
    }

    public Baixa getBaixa() {
        return baixa;
    }

    public FaturaEmitida getFaturaEmitida() {
        return faturaEmitida;
    }

    public FaturaRecebida getFaturaRecebida() {
        return faturaRecebida;
    }

    public void setFaturaEmitida(FaturaEmitida faturaEmitida) {
        this.faturaEmitida = faturaEmitida;
    }

    public void setFaturaRecebida(FaturaRecebida faturaRecebida) {
        this.faturaRecebida = faturaRecebida;
    }

    public void setConhecimentoDeFrete(ConhecimentoDeFrete conhecimentoDeFrete) {
        this.conhecimentoDeFrete = conhecimentoDeFrete;
    }

    public ConhecimentoDeFrete getConhecimentoDeFrete() {
        return conhecimentoDeFrete;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final ValorPorCotacao other = (ValorPorCotacao) obj;
        return Objects.equals(this.id, other.id);
    }

}
