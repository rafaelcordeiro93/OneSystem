/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.war.service.ModeloDeRelatorioService;
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
@FacesConverter(value = "modeloDeRelatorioConverter", forClass = ModeloDeRelatorio.class)
public class ModeloDeRelatorioConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                ModeloDeRelatorioService service = (ModeloDeRelatorioService) fc.getExternalContext().getApplicationMap().get("modeloDeRelatorioService");
                List<ModeloDeRelatorio> lista = service.buscarModeloDeRelatorio();
                for (ModeloDeRelatorio mdr : lista) {
                    if (mdr.getId().equals(new Long(value))) {
                        return mdr;
                    }
                }
                return null;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return String.valueOf(((ModeloDeRelatorio) object).getId());
        } else {
            return null;
        }
    }
}
