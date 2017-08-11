/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.builder.ContaDeEstoqueBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoOperacao;
import br.com.onesystem.war.builder.ItemBV;
import java.util.Date;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class EstoqueDAOTest {

    private EstoqueDAO dao;
    private NotaEmitida nota;
    private ContaDeEstoque contaDeEstoque;
    private Item item;

    @Before
    public void setup() {
        try {
            dao = new EstoqueDAO();
            nota = new NotaEmitida();
            contaDeEstoque = new ContaDeEstoqueBuilder().comID(new Long(1)).construir();
            ItemBV itemBv = new ItemBV();
            itemBv.setId(new Long(1));
            item = itemBv.construir();
        } catch (DadoInvalidoException ex) {
        }
    }

    @Test
    public void porTipoDeLancamentoDeNota() {
        try {
            dao.porTipoDeLancamentoDeNota(TipoLancamento.EMITIDA).listaDeResultados();
        } catch (IllegalArgumentException iae) {
            Assert.fail("Os atributos da consulta foram alterados, por favor corrija.");
        }
    }

    @Test
    public void porTipoDeOperacaoDeNota() {
        try {
            dao.porTipoDeOperacaoDeNota(TipoOperacao.AVULSO).listaDeResultados();
        } catch (IllegalArgumentException iae) {
            Assert.fail("Os atributos da consulta foram alterados, por favor corrija.");
        }
    }

    @Test(expected = IllegalStateException.class)
    public void porNotaDeOrigem() {
        try {
            dao.porNotaDeOrigem(nota).listaDeResultados();
        } catch (IllegalArgumentException iae) {
            Assert.fail("Os atributos da consulta foram alterados, por favor corrija.");
        }
    }

    @Test(expected = IllegalStateException.class)
    public void porNota() {
        try {
            dao.porNota(nota).listaDeResultados();
        } catch (IllegalArgumentException iae) {
            Assert.fail("Os atributos da consulta foram alterados, por favor corrija.");
        }
    }

    @Test
    public void porContaDeEstoque() {
        try {
            dao.porContaDeEstoque(contaDeEstoque).listaDeResultados();
        } catch (IllegalArgumentException iae) {
            Assert.fail("Os atributos da consulta foram alterados, por favor corrija.");
        }
    }

    @Test
    public void porItem() {
        try {
            dao.porItem(item).listaDeResultados();
        } catch (IllegalArgumentException iae) {
            Assert.fail("Os atributos da consulta foram alterados, por favor corrija.");
        }
    }

    @Test
    public void porEmissao() {
        try {
            dao.porEmissao(new Date()).listaDeResultados();
        } catch (IllegalArgumentException iae) {
            Assert.fail("Os atributos da consulta foram alterados, por favor corrija.");
        }
    }

}
