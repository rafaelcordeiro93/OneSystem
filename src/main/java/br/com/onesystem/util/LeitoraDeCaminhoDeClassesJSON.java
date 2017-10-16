/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import br.com.onesystem.reportTemplate.CaminhoDeClasse;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Rafael
 */
//@Stateless
//@Startup
//@ApplicationScoped
public class LeitoraDeCaminhoDeClassesJSON implements Serializable {

    //Diretório Console: System.getProperty("user.dir") + "\\src\\main\\resources\\conf\\extraClass.json";
    private final String diretorio = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "\\WEB-INF\\classes\\conf\\extraClass.json";

    public List<CaminhoDeClasse> getCaminhos(Class classeOriginal) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(diretorio));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray dados = (JSONArray) jsonObject.get(classeOriginal.getCanonicalName());

            List<CaminhoDeClasse> listaLayout = new ArrayList<>();

            if (dados != null) {
                for (Object o : dados) {
                    JSONObject j = (JSONObject) o;
                    Class classeDestino = Class.forName((String) j.get("classeDestino"));
                    String caminho = (String) j.get("caminho");

                    CaminhoDeClasse c = new CaminhoDeClasse(classeOriginal, classeDestino, caminho);
                    listaLayout.add(c);
                }
            }
            return listaLayout;
        } catch (IOException | ParseException ex) {
            throw new RuntimeException("Erro desconhecido: " + ex.getMessage());
        } catch (ClassNotFoundException cnf) {
            throw new RuntimeException("Classe nao encontrada! " + cnf.getMessage());
        }
    }

}
