/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;

/**
 *
 * @author rauber
 */
public class BeanUtil {

    public Object getBeanNaSessao(String bean) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory factory = facesContext.getApplication().getExpressionFactory();
        return factory.createValueExpression(elContext, "#{" + bean + "}", Object.class).getValue(elContext);
    }

}
