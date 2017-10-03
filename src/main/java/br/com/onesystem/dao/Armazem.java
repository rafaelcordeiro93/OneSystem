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
public class Armazem<SelecaoBean extends BasicCrudMBImpl> {

    private final Class selecaoBeanClass;

    public Armazem(Class selecaoBeanClass) {
        this.selecaoBeanClass = selecaoBeanClass;
    }

    public void initailize(Object objeto) {
        SelecaoBean mb = (SelecaoBean) BeanUtil.getBeanNaSessao(selecaoBeanClass);
        mb.inicializaRegistro(objeto);
    }

}
