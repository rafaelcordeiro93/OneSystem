/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.services;

import br.com.onesystem.exception.DadoInvalidoException;

/**
 *
 * @author rauber
 * @param <T> Objeto para persistência
 */
public interface BuilderView<T> {
    
    public Long getId();

    public T construir() throws DadoInvalidoException;

    public T construirComID() throws DadoInvalidoException;
}
