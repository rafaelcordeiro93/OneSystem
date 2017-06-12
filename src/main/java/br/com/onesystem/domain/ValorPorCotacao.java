/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.domain.builder.BaixaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

    public ValorPorCotacao() {
    }

    public ValorPorCotacao(Long id, Cotacao cotacao, BigDecimal valor) throws DadoInvalidoException {
        this.id = id;
        this.cotacao = cotacao;
        this.valor = valor;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("valor", "cotacao");
        new ValidadorDeCampos<>().valida(this, campos);
    }

    public void geraBaixaPor(Nota nota) throws DadoInvalidoException {
        this.nota = nota;
        baixa = new BaixaBuilder().comCotacao(cotacao).comEmissao(nota.getEmissao())
                .comOperacaoFinanceira(nota.getOperacao().getOperacaoFinanceira()).comPessoa(nota.getPessoa())
                .comReceita(nota.getOperacao().getVendaAVista()).comValor(valor).construir();
    }

    public Long getId() {
        return id;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Nota getNota() {
        return nota;
    }

    public Baixa getBaixa() {
        return baixa;
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
