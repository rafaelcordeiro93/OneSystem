/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.reportTemplate;

/**
 *
 * @author Rafael
 */
public class CaminhoDeClasse {
    
    private Class classeDeOrigem;
    private Class classeDeDestino;
    private String caminho;

    public CaminhoDeClasse(Class classeDeOrigem, Class classeDeDestino, String caminho) {
        this.classeDeOrigem = classeDeOrigem;
        this.classeDeDestino = classeDeDestino;
        this.caminho = caminho;
    }

    public Class getClasseDeOrigem() {
        return classeDeOrigem;
    }

    public Class getClasseDeDestino() {
        return classeDeDestino;
    }

    public String getCaminho() {
        return caminho;
    }

    @Override
    public String toString() {
        return "CaminhoDeClasse{" + "classeDeOrigem=" + classeDeOrigem + ", classeDeDestino=" + classeDeDestino + ", caminho=" + caminho + '}';
    }
    
}
