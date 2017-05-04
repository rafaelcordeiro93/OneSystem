/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.PessoaBV;
import br.com.onesystem.war.service.PessoaService;
import java.io.Serializable;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "pessoaBVConverter", forClass = PessoaBV.class)
public class PessoaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<Pessoa> lista = new PessoaService().buscarPessoas();
                if (StringUtils.containsLetter(value)) {
                    for (Pessoa pessoa : lista) {
                        if (pessoa.getNome().equals(value)) {
                            return new PessoaBV(pessoa);
                        }
                    }
                } else {
                    for (Pessoa pessoa : lista) {
                        if (pessoa.getId().equals(new Long(value))) {
                            return new PessoaBV(pessoa);
                        }
                    }
                }
                return new PessoaBV();
            } catch (Exception e) {
                return new PessoaBV();
            }
        } else {
            return new PessoaBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((PessoaBV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
