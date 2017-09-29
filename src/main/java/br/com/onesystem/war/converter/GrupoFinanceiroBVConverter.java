/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.war.builder.GrupoFinanceiroBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoGrupoFinanceiroView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "grupoFinanceiroBVConverter", forClass = GrupoFinanceiroBV.class)
public class GrupoFinanceiroBVConverter extends BasicBVConverter<GrupoFinanceiro, GrupoFinanceiroBV, SelecaoGrupoFinanceiroView> implements Converter, Serializable {

    public GrupoFinanceiroBVConverter() {
        super(GrupoFinanceiro.class, GrupoFinanceiroBV.class, SelecaoGrupoFinanceiroView.class);
    }

}