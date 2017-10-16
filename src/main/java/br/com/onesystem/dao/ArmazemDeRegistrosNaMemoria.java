package br.com.onesystem.dao;

import br.com.onesystem.util.BeanUtil;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rafael
 */
public class ArmazemDeRegistrosNaMemoria<SelecaoBean extends BasicCrudMBImpl> {

    public Object initialize(Object objeto, Class selecaoBeanClass, String metodoParaInicializar) {
        if (objeto != null && selecaoBeanClass != null) {
            SelecaoBean mb = (SelecaoBean) BeanUtil.getBeanNaSessao(selecaoBeanClass);
            objeto = mb.inicializaRegistro(objeto, metodoParaInicializar);
        }
        return objeto;
    }

}
