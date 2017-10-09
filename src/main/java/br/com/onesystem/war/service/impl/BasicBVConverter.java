/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.service.impl;

import br.com.onesystem.dao.ArmazemDeRegistrosNaMemoria;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.util.BeanUtil;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * @author Rafael
 */
public abstract class BasicBVConverter<Bean, BeanBV extends BuilderView, SelecaoBean extends BasicCrudMBImpl> implements Serializable {

    private Class clazz;
    private Class clazzBV;
    private Class selecaoClazz;

    public BasicBVConverter(Class clazz, Class clazzBV, Class selecaoClazz) {
        this.clazz = clazz;
        this.clazzBV = clazzBV;
        this.selecaoClazz = selecaoClazz;
    }

    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        try {
            if (value != null && !value.isEmpty()) {
                Object object = uic.getAttributes().get(value);
                if (object.getClass().equals(clazz)) {
                    return clazzBV.getConstructor(clazz).newInstance((Bean) object);
                } else if (object.getClass().equals(clazzBV)) {
                    return (BeanBV) object;
                }
            }
            return clazzBV.newInstance();
        } catch (InstantiationException ex) {
            throw new RuntimeException("Erro de instanciação - ConverterBV.");
        } catch (IllegalAccessException ex) {
            throw new RuntimeException("Erro de acesso - ConverterBV.");
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException("Erro método não encontrado - ConverterBV.");
        } catch (SecurityException ex) {
            throw new RuntimeException("Erro de segurança - ConverterBV.");
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Erro parâmetro inválido - ConverterBV.");
        } catch (InvocationTargetException ex) {
            throw new RuntimeException("Erro de invocação - ConverterBV.");
        }
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        try {
            if (object != null) {
                if (object.getClass().equals(clazzBV)) {
                    BeanBV bean = (BeanBV) object;

                    //Pega o id do objeto
                    Method m = bean.getClass().getMethod("getId", null);
                    m.setAccessible(true);
                    Long idObject = (Long) m.invoke(bean, null);

                    //Grava o objeto no componente e devolve o id
                    String id = String.valueOf(idObject);
                    uic.getAttributes().put(id, (BeanBV) object);
                    return id;
                } else if (object.getClass().equals(clazz)) {
                    Bean bean = (Bean) object;

                    //Pega o id do objeto
                    Method m = bean.getClass().getMethod("getId", null);
                    m.setAccessible(true);
                    Long idObject = (Long) m.invoke(bean, null);

                    //Inicializa o objeto dentro do managed bean;
                    new ArmazemDeRegistrosNaMemoria<SelecaoBean>().initialize(bean, selecaoClazz);

                    //Grava o objeto no componente e devolve o id
                    String id = String.valueOf(idObject);
                    uic.getAttributes().put(id, bean);
                    return id;
                } else {
                    return object.toString();
                }
            } else {
                return "";
            }
        } catch (IllegalAccessException ex) {
            throw new RuntimeException("Erro de acesso ao método - ConverterBV STRING.");
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Erro parametros inválidos ao acessar o método - ConverterBV STRING.");
        } catch (InvocationTargetException ex) {
            throw new RuntimeException("Erro na invocação do método - ConverterBV STRING.");
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException("Erro o método não existe - ConverterBV STRING.");
        } catch (SecurityException ex) {
            throw new RuntimeException("Erro de segurança ao realizar o acesso - ConverterBV STRING.");
        }
    }

}
