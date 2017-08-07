/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.valueobjects.TipoDeBusca;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.SortedSet;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_FILTRODERELATORIO",
        sequenceName = "SEQ_FILTRODERELATORIO")
public class FiltroDeRelatorio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FILTRODERELATORIO")
    private Long id;
    @OneToOne
    private Coluna coluna;
    @org.hibernate.annotations.Type(type = "java.math.BigDecimal")
    private SortedSet<BigDecimal> filtrosBigDecimal;
    @org.hibernate.annotations.Type(type = "java.lang.Long")
    private SortedSet<Long> filtrosLong;
    @org.hibernate.annotations.Type(type = "java.lang.String")
    private SortedSet<String> filtrosString;
    @Enumerated(EnumType.ORDINAL)
    private TipoDeBusca tipoDaBusca;
    @Temporal(TemporalType.DATE)
    private Date filtroDeData;
    @ManyToOne
    private ModeloDeRelatorio modelo;

    public FiltroDeRelatorio() {
    }

    public FiltroDeRelatorio(Long id, Coluna coluna, SortedSet filtros, TipoDeBusca tipoDeBusca) {
        if (filtros.first().getClass() == Long.class) {
            this.filtrosLong = filtros;
        } else if (filtros.first().getClass() == BigDecimal.class) {
            this.filtrosString = filtros;
        } else {
            this.filtrosString = filtros;
        }
        this.coluna = coluna;
        this.tipoDaBusca = tipoDeBusca;
        this.id = id;
    }

    public FiltroDeRelatorio(Long id, Coluna coluna, TipoDeBusca tipoDaBusca) {
        this.id = id;
        this.coluna = coluna;
        this.tipoDaBusca = tipoDaBusca;
    }

    public FiltroDeRelatorio(Long id, Coluna coluna, Date filtros, TipoDeBusca filtroDeData) {
        this.id = id;
        this.coluna = coluna;
        this.filtroDeData = filtros;
        this.tipoDaBusca = filtroDeData;
    }

    public void setModelo(ModeloDeRelatorio modelo) {
        this.modelo = modelo;
    }

    public Coluna getColuna() {
        return coluna;
    }

    public SortedSet getFiltros() {
        return filtrosLong != null ? filtrosLong : filtrosBigDecimal != null ? filtrosBigDecimal : filtrosString;
    }

    public SortedSet<BigDecimal> getFiltrosBigDecimal() {
        return filtrosBigDecimal;
    }

    public SortedSet<Long> getFiltrosLong() {
        return filtrosLong;
    }

    public SortedSet<String> getFiltrosString() {
        return filtrosString;
    }

    public TipoDeBusca getTipoDaBusca() {
        return tipoDaBusca;
    }

    public ModeloDeRelatorio getModelo() {
        return modelo;
    }

    public Date getFiltroDeData() {
        return filtroDeData;
    }

    public String getFiltroDeDataFormatado() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(filtroDeData);
    }

    public void setFiltroDeData(Date filtroDeData) {
        this.filtroDeData = filtroDeData;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final FiltroDeRelatorio other = (FiltroDeRelatorio) obj;
        if (!Objects.equals(this.coluna, other.coluna)) {
            return false;
        }
        if (this.tipoDaBusca != other.tipoDaBusca) {
            return false;
        }
        return true;
    }

}
