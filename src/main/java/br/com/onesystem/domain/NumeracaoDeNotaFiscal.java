/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Rafael Cordeiro
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_NUMERACAODENOTAFISCAL",
        sequenceName = "SEQ_NUMERACAODENOTAFISCAL")
public class NumeracaoDeNotaFiscal implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_NUMERACAODENOTAFISCAL", strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = true)
    private Long numeroNF;
    @ManyToOne
    private LoteNotaFiscal loteNotaFiscal;
    @ManyToOne
    private Filial filial;

    public NumeracaoDeNotaFiscal() {
    }

    public NumeracaoDeNotaFiscal(Long id, Long numeroNF, LoteNotaFiscal loteNotaFiscal, Filial filial) {
        this.id = id;
        this.numeroNF = numeroNF;
        this.loteNotaFiscal = loteNotaFiscal;
        this.filial = filial;
    }

    public void interarNumeracao() {
        this.numeroNF++;
    }

    public void setLoteNotaFiscal(LoteNotaFiscal loteNotaFiscal) {
        this.loteNotaFiscal = loteNotaFiscal;
    }

    public Long getId() {
        return id;
    }

    public Long getNumeroNF() {
        return numeroNF;
    }

    public LoteNotaFiscal getLoteNotaFiscal() {
        return loteNotaFiscal;
    }

    public Filial getFilial() {
        return filial;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof NumeracaoDeNotaFiscal)) {
            return false;
        }
        NumeracaoDeNotaFiscal outro = (NumeracaoDeNotaFiscal) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.getId());
    }
}
