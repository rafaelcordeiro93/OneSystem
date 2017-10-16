/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.reportTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Rafael
 */
public class TemplateFormaPagamento {

    private int id;
    private String nome;
    private String detalhes;
    private String vencimento;
    private String valorFormatado;
    private String valorConvertido;

    public TemplateFormaPagamento(int id, String nome, String detalhes, String vencimento, String valorFormatado, String valorConvertido) {
        this.id = id;
        this.nome = nome;
        this.vencimento = vencimento;
        this.valorFormatado = valorFormatado;
        this.valorConvertido = valorConvertido;
        this.detalhes = detalhes;
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

    public String getValorConvertido() {
        return valorConvertido;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public String getAbbreviated() {
        return nome.substring(0, 3) + " " + (vencimento == null ? ".........." : vencimento) + " " + valorConvertido;
    }

    @Override
    public String toString() {
        return "TemplateFormaPagamento{" + "id=" + id + ", nome=" + nome + ", vencimento=" + vencimento + ", valorFormatado=" + valorFormatado + ", valorConvertido=" + valorConvertido + '}';
    }

}
