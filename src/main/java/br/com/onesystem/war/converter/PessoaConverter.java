/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.dao.ArmazemDeRegistrosNaMemoria;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.PessoaService;
import br.com.onesystem.war.view.selecao.SelecaoPessoaView;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "pessoaConverter", forClass = Pessoa.class)
public class PessoaConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Pessoa) {
                return (Pessoa) object;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        try {
            if (object != null) {
                if (object instanceof Pessoa) {
                    Pessoa bean = (Pessoa) object;

                    //Pega o id do objeto
                    Method m = bean.getClass().getMethod("getId", null);
                    m.setAccessible(true);
                    Long idObject = (Long) m.invoke(bean, null);

                    //Inicializa o objeto dentro do managed bean;
                    if (idObject != null) {
                        new ArmazemDeRegistrosNaMemoria<SelecaoPessoaView>().initialize(bean, SelecaoPessoaView.class);
                    } else {
                        return "";
                    }

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
            throw new RuntimeException("Erro de acesso ao método - Converter.");
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Erro parametros inválidos ao acessar o método - Converter.");
        } catch (InvocationTargetException ex) {
            throw new RuntimeException("Erro na invocação do método - Converter.");
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException("Erro o método não existe - Converter.");
        } catch (SecurityException ex) {
            throw new RuntimeException("Erro de segurança ao realizar o acesso - Converter.");
        }
    }
}
