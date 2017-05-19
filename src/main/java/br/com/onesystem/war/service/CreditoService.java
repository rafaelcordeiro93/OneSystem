/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.service;

import br.com.onesystem.dao.CreditoDAO;
import br.com.onesystem.domain.Credito;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class CreditoService implements Serializable {

    public BigDecimal buscarSaldo(Pessoa pessoa) {

        List<Credito> resutados = new CreditoDAO().buscarCreditos().porPessoa(pessoa).listaDeResultados();

        BigDecimal entradas = resutados.stream().filter(c -> c.getOperacaoFinanceira() == OperacaoFinanceira.ENTRADA).map(Credito::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal saidas = resutados.stream().filter(c -> c.getOperacaoFinanceira() == OperacaoFinanceira.SAIDA).map(Credito::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);

        return saidas.subtract(entradas);
    }

}
