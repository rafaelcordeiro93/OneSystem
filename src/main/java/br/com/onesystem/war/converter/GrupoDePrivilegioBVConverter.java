package br.com.onesystem.war.converter;

import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.war.builder.GrupoDePrivilegioBV;
import br.com.onesystem.war.service.GrupoPrivilegioService;
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
@FacesConverter(value = "grupoDePrivilegioBVConverter", forClass = GrupoDePrivilegioBV.class)
public class GrupoDePrivilegioBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            GrupoPrivilegioService service = new GrupoPrivilegioService();
            try {
                List<GrupoDePrivilegio> lista = service.buscarGrupoDePrivilegio();

                for (GrupoDePrivilegio grupoDePrivilegio : lista) {
                    if (grupoDePrivilegio.getId().equals(new Long(value))) {
                        return new GrupoDePrivilegioBV(grupoDePrivilegio);
                    }
                }
                return new GrupoDePrivilegioBV();
            } catch (Exception e) {
                return new GrupoDePrivilegioBV();
            }
        } else {
            return new GrupoDePrivilegioBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((GrupoDePrivilegioBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
