/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.services;

import br.com.onesystem.domain.Privilegio;
import br.com.onesystem.services.impl.RelatorioContaAbertaImpl;
import java.util.Comparator;

/**
 *
 * @author Rafael
 */
public class RelatorioPrivilegioPorModulo implements Comparator<Privilegio> {

    @Override
    public int compare(Privilegio o1, Privilegio o2) {
        int modulo = o1.getJanela().getModulo().getNome().compareTo(o2.getJanela().getModulo().getNome());
        //Caso os módulos forem diferentes, ordena pelo nome do módulo.
        if (modulo != 0) {
            return modulo;
        } //Se forem iguais, vamos ordenar pelo nome da janela.
        else {
            return o1.getJanela().getNome().compareTo(o2.getJanela().getNome());
        }
    }

}
