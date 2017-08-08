/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.valueobjects.TipoDeBusca;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    @OneToOne(cascade = CascadeType.ALL)
    private Coluna coluna;
    @OneToMany(mappedBy = "filtroDeRelatorio", cascade = CascadeType.ALL)
    private List<ParametroDeFiltroDeRelatorio> parametros = new ArrayList<>();
    @Enumerated(EnumType.ORDINAL)
    private TipoDeBusca tipoDaBusca;
    @Temporal(TemporalType.DATE)
    private Date filtroDeData;
    @ManyToOne
    private ModeloDeRelatorio modelo;

    public FiltroDeRelatorio() {
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

    public void add(Long l) {
        boolean present = parametros.stream().filter(p -> p.getParametroLong().equals(l)).findAny().isPresent();
        if (!present) {
            this.parametros.add(new ParametroDeFiltroDeRelatorio(null, l, this));
        }
    }

    public void add(BigDecimal b) {
        boolean present = parametros.stream().filter(p -> p.getParametroBigDecimal() == b).findAny().isPresent();
        if (!present) {
            this.parametros.add(new ParametroDeFiltroDeRelatorio(null, b, this));
        }
    }

    public void add(String s) {
        boolean present = parametros.stream().filter(p -> p.getParametroString().equals(s)).findAny().isPresent();
        if (!present) {
            this.parametros.add(new ParametroDeFiltroDeRelatorio(null, s, this));
        }
    }

    public void addEnum(String e) {
        boolean encontrou = false;
        for (ParametroDeFiltroDeRelatorio p : parametros) {
            if (p.getParametroEnum().equalsIgnoreCase(e)) {
                encontrou = true;
                break;
            }
        }
        if (!encontrou) {
            parametros.add(new ParametroDeFiltroDeRelatorio(null, this, e));
        }
    }

    public void setModelo(ModeloDeRelatorio modelo) {
        this.modelo = modelo;
    }

    public Coluna getColuna() {
        return coluna;
    }

    public Long getId() {
        return id;
    }

    public SortedSet getFiltros() {
        SortedSet filtros = new TreeSet();

        parametros.forEach((p) -> {
            filtros.add(
                    p.getParametroLong() != null ? p.getParametroLong()
                    : p.getParametroBigDecimal() != null ? p.getParametroBigDecimal()
                    : p.getParametroString() != null ? p.getParametroString()
                    : p.getParametroEnum() != null ? getEnum(p.getParametroEnum()) : null);
        });

        return filtros;
    }

    public SortedSet getFiltrosFormatados() {
        SortedSet filtros = new TreeSet();

        parametros.forEach((p) -> {
            filtros.add(
                    p.getParametroLong() != null ? getPattern(p.getParametroLong())
                    : p.getParametroBigDecimal() != null ? getPattern(p.getParametroBigDecimal())
                    : p.getParametroString() != null ? p.getParametroString()
                    : p.getParametroEnum() != null ? getEnumName(getEnum(p.getParametroEnum())) : null);
        });

        return filtros;
    }

    private Enum getEnum(String str) {
        return Enum.valueOf((Class<? extends Enum>) coluna.getClasseOriginal(), str);
    }

    private String getEnumName(Enum enums) {
        try {
            return enums.getClass().getMethod("getNome", null).invoke(enums, null).toString();
        } catch (Exception ex) {
            return enums.toString();
        }
    }

    private String getPattern(Object obj) {
        if (obj.getClass() == Long.class) {
            NumberFormat nf = NumberFormat.getIntegerInstance();
            return nf.format(obj);
        } else {
            return NumberFormat.getNumberInstance().format(obj);
        }
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

    public void setId(Object object) {
        this.id = null;
    }

}
