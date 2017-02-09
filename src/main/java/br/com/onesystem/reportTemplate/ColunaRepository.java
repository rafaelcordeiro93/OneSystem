/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.reportTemplate;

import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import java.util.Objects;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ColunaRepository {

    private AbstractColumn coluna;
    private DJCalculation totalizador;

    public ColunaRepository(AbstractColumn coluna, DJCalculation totalizador) {
        this.coluna = coluna;
        this.totalizador = totalizador;
    }

    public AbstractColumn getColuna() {
        return coluna;
    }

    public DJCalculation getTotalizador() {
        return totalizador;
    }

}
