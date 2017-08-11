/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.TipoPessoa;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_SITUACAOFISCAL",
        sequenceName = "SEQ_SITUACAOFISCAL")
public class SituacaoFiscal implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_SITUACAOFISCAL", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{sequencia_not_null}")
    private Integer sequencia;
    @NotNull(message = "{operacao_not_null}")
    @ManyToOne(optional = false)
    private Operacao operacao;
    @NotNull(message = "{grupo_fiscal_not_null}")
    @ManyToOne(optional = false)
    private GrupoFiscal grupoFiscal;
    @NotNull(message = "{tabela_de_tributacao_not_null}")
    @ManyToOne(optional = false)
    private TabelaDeTributacao tabelaDeTributacao;
    @ManyToOne
    private Estado estado;
    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;
    @NotNull(message = "{CFOP_not_null}")
    @ManyToOne(optional = false)
    private Cfop cfop;
    @Column(length = 1000)
    private String observacao;

    public SituacaoFiscal() {
    }

    public SituacaoFiscal(Long id, Integer sequencia, Operacao operacao, GrupoFiscal grupoFiscal, TabelaDeTributacao tabelaDeTributacao, Estado estado, TipoPessoa tipoPessoa, Cfop cfop, String observacao) throws DadoInvalidoException {
        this.id = id;
        this.sequencia = sequencia;
        this.operacao = operacao;
        this.grupoFiscal = grupoFiscal;
        this.tabelaDeTributacao = tabelaDeTributacao;
        this.estado = estado;
        this.tipoPessoa = tipoPessoa;
        this.cfop = cfop;
        this.observacao = observacao;
        ehValido();
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("sequencia", "operacao", "grupoFiscal", "tabelaDeTributacao", "cfop");
        new ValidadorDeCampos<SituacaoFiscal>().valida(this, campos);
    }

    public Long getId() {
        return id;
    }

    public void setSequencia(Integer sequencia) {
        this.sequencia = sequencia;
    }

    public Integer getSequencia() {
        return sequencia;
    }

    public Operacao getOperacao() {
        return operacao;
    }

    public GrupoFiscal getGrupoFiscal() {
        return grupoFiscal;
    }

    public TabelaDeTributacao getTabelaDeTributacao() {
        return tabelaDeTributacao;
    }

    public Estado getEstado() {
        return estado;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public Cfop getCfop() {
        return cfop;
    }

    public String getObservacao() {
        return observacao;
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
        final SituacaoFiscal other = (SituacaoFiscal) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SituacaoFiscal{" + "id=" + id + ", sequencia=" + sequencia + ", operacao=" + operacao + ", grupoFiscal=" + grupoFiscal + ", tabelaDeTributacao=" + tabelaDeTributacao + ", estado=" + estado + ", tipoPessoa=" + tipoPessoa + ", cfop=" + cfop + '}';
    }

}
