package br.com.onesystem.war.converter;

import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.war.builder.GrupoDePrivilegioBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoGrupoDePrivilegioView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "grupoDePrivilegioBVConverter", forClass = GrupoDePrivilegioBV.class)
public class GrupoDePrivilegioBVConverter extends BasicBVConverter<GrupoDePrivilegio, GrupoDePrivilegioBV, SelecaoGrupoDePrivilegioView> implements Converter, Serializable {

    public GrupoDePrivilegioBVConverter() {
        super(GrupoDePrivilegio.class, GrupoDePrivilegioBV.class, SelecaoGrupoDePrivilegioView.class);
    }

}