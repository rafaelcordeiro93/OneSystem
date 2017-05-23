/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class CaixaBuilder {

    private Long id;
    private BigDecimal saldo;
    private Usuario usuario;
    private List<Cotacao> cotacao;

    public CaixaBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public CaixaBuilder comSaldo(BigDecimal saldo) {
        this.saldo = saldo;
        return this;
    }

    public CaixaBuilder comUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public CaixaBuilder comCotacao(List<Cotacao> cotacao) {
        this.cotacao = cotacao;
        return this;
    }

    public Caixa construir() throws DadoInvalidoException {
        return new Caixa(id, saldo, usuario, cotacao);
    }

}
