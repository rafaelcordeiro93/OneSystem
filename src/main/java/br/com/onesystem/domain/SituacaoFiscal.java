/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.valueobjects.TipoPessoa;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_SITUACAOFISCAL",
        sequenceName = "SEQ_SITUACAOFISCAL")
public class SituacaoFiscal implements Serializable {

    @Id
    private Long id;
    private Integer sequencia;
    @ManyToOne
    private Operacao operacao;
    @ManyToOne
    private GrupoFiscal grupoFiscal;
    @ManyToOne
    private TabelaDeTributacao tabelaDeTributacao;
    @ManyToOne
    private Estado estado;
    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;
    @ManyToOne
    private CFOP cfop;

    public SituacaoFiscal() {
    }

    public SituacaoFiscal(Long id, Integer sequencia, Operacao operacao, GrupoFiscal grupoFiscal, TabelaDeTributacao tabelaDeTributacao, Estado estado, TipoPessoa tipoPessoa, CFOP cfop) {
        this.id = id;
        this.sequencia = sequencia;
        this.operacao = operacao;
        this.grupoFiscal = grupoFiscal;
        this.tabelaDeTributacao = tabelaDeTributacao;
        this.estado = estado;
        this.tipoPessoa = tipoPessoa;
        this.cfop = cfop;
    }

    public Long getId() {
        return id;
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

    public CFOP getCfop() {
        return cfop;
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
