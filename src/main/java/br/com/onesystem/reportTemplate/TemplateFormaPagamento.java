/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.reportTemplate;

import java.util.Date;

/**
 *
 * @author Rafael
 */
public class TemplateFormaPagamento {
    
    private int id;
    private String nome;
    private String vencimento;
    private String valorFormatado;

    public TemplateFormaPagamento(int id, String nome, String vencimento, String valorFormatado) {
        this.id = id;
        this.nome = nome;
        this.vencimento = vencimento;
        this.valorFormatado = valorFormatado;
    }

    public int getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }

    public String getVencimento() {
        return vencimento;
    }

    public String getValorFormatado() {
        return valorFormatado;
    }
    
}
