/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class DadosNecessariosBV implements Serializable {

    private List<String> lista = new ArrayList<String>();
    private String janela;
    private String caminho;

    public DadosNecessariosBV(String janela, String caminho) {
        this.janela = janela;
        this.caminho = caminho;
    }

    public DadosNecessariosBV(List<String> lista, String janela, String caminho) {
        this.lista = lista;
        this.janela = janela;
        this.caminho = caminho;
    }

    public List<String> getLista() {
        return lista;
    }

    public void setLista(List<String> lista) {
        this.lista = lista;
    }

    public String getJanela() {
        return janela;
    }

    public void setJanela(String janela) {
        this.janela = janela;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

}
