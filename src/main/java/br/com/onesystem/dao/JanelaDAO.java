/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Janela;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rafael
 */
public class JanelaDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public JanelaDAO() {
        limpar();
    }

    public JanelaDAO buscarJanelasW() {
        consulta += "select j from Janela j where j.id != null ";
        return this;
    }

    public List<Janela> listaDeResultados() {
        List<Janela> resultado = new ArmazemDeRegistros<Janela>(Janela.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

}
