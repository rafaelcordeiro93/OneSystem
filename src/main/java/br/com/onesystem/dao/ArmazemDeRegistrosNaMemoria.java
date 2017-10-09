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

    public void initialize(Object objeto, Class selecaoBeanClass) {
        SelecaoBean mb = (SelecaoBean) BeanUtil.getBeanNaSessao(selecaoBeanClass);
        mb.inicializaRegistro(objeto);
    }

}
