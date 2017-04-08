package br.com.onesystem.war.service.impl;

import org.primefaces.event.SelectEvent;

public abstract class BasicMBImpl<Bean> {

    protected Bean bean;

    public abstract void selecionar(SelectEvent event);

    @Deprecated
    public void buscaPorId(){};

}
