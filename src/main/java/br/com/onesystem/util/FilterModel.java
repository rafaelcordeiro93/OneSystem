/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import br.com.onesystem.valueobjects.TipoDeBusca;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class FilterModel {

    private FieldModel field;
    private List filters;
    private TipoDeBusca typeSearch;
    private Date filterDate;

    public FilterModel(FieldModel field, List filters, TipoDeBusca typeSearch) {
        this.field = field;
        this.filters = filters;
        this.typeSearch = typeSearch;
    }

    public FilterModel(FieldModel field, Date filterDate, TipoDeBusca typeSearch) {
        this.field = field;
        this.filterDate = filterDate;
        this.typeSearch = typeSearch;
    }

    public FieldModel getField() {
        return field;
    }

    public List getFilters() {
        return filters;
    }

    public TipoDeBusca getTypeSearch() {
        return typeSearch;
    }

    public Date getFilterDate() {
        return filterDate;
    }

    public String getFilterDateFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(filterDate);
    }

    public void setFilterDate(Date filterDate) {
        this.filterDate = filterDate;
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
        final FilterModel other = (FilterModel) obj;
        if (!Objects.equals(this.field, other.field)) {
            return false;
        }
        if (this.typeSearch != other.typeSearch) {
            return false;
        }
        return true;
    }

}
