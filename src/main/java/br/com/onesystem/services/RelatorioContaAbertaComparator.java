/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.services;

import br.com.onesystem.services.impl.RelatorioContaAbertaImpl;
import java.util.Comparator;

/**
 *
 * @author Rafael
 */
public class RelatorioContaAbertaComparator implements Comparator<RelatorioContaAbertaImpl> {

    @Override
    public int compare(RelatorioContaAbertaImpl o1, RelatorioContaAbertaImpl o2) {
        return o1.getMoeda().getId().compareTo(o2.getMoeda().getId());
    }
    
}
