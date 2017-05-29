package br.com.onesystem.war.converter;

import br.com.onesystem.domain.FaturaLegada;
import br.com.onesystem.war.builder.FaturaLegadaBV;
import br.com.onesystem.war.service.FaturaLegadaService;
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
@FacesConverter(value = "faturaLegadaBVConverter", forClass = FaturaLegadaBV.class)
public class FaturaLegadaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            FaturaLegadaService service = new FaturaLegadaService();
            try {
                List<FaturaLegada> lista = service.buscarFaturasLegadas();
               
                    for (FaturaLegada faturaLegada : lista) {
                        if (faturaLegada.getId().equals(new Long(value))) {
                            return new FaturaLegadaBV(faturaLegada);
                        }
                    }
                
                return new FaturaLegadaBV();
            } catch (Exception e) {
                return new FaturaLegadaBV();
            }
        } else {
            return new FaturaLegadaBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((FaturaLegadaBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
