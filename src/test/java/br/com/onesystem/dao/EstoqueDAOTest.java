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
import br.com.onesystem.domain.builder.ItemBuilder;
import br.com.onesystem.domain.builder.NotaEmitidaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.util.ArrayList;
import java.util.Date;
import org.hibernate.TransientObjectException;
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
            item = new ItemBuilder().comId(new Long(1)).construir();
        } catch (DadoInvalidoException ex) {
        }
    }

    @Test
    public void porTipoDeLancamentoDeNota() {
        try {
            dao.porTipoDeLancamentoDeNota(TipoLancamento.EMITIDA).listaResultados();
        } catch (IllegalArgumentException iae) {
            Assert.fail("Os atributos da consulta foram alterados, por favor corrija.");
        }
    }

    @Test
    public void porTipoDeOperacaoDeNota() {
        try {
            dao.porTipoDeOperacaoDeNota(TipoOperacao.AVULSO).listaResultados();
        } catch (IllegalArgumentException iae) {
            Assert.fail("Os atributos da consulta foram alterados, por favor corrija.");
        }
    }

    @Test(expected = IllegalStateException.class)
    public void porNotaDeOrigem() {
        try {
            dao.porNotaDeOrigem(nota).listaResultados();
        } catch (IllegalArgumentException iae) {
            Assert.fail("Os atributos da consulta foram alterados, por favor corrija.");
        }
    }

    @Test(expected = IllegalStateException.class)
    public void porNota() {
        try {
            dao.porNota(nota).listaResultados();
        } catch (IllegalArgumentException iae) {
            Assert.fail("Os atributos da consulta foram alterados, por favor corrija.");
        }
    }

    @Test
    public void porContaDeEstoque() {
        try {
            dao.porContaDeEstoque(contaDeEstoque).listaResultados();
        } catch (IllegalArgumentException iae) {
            Assert.fail("Os atributos da consulta foram alterados, por favor corrija.");
        }
    }

    @Test
    public void porItem() {
        try {
            dao.porItem(item).listaResultados();
        } catch (IllegalArgumentException iae) {
            Assert.fail("Os atributos da consulta foram alterados, por favor corrija.");
        }
    }

    @Test
    public void porEmissao() {
        try {
            dao.porEmissao(new Date()).listaResultados();
        } catch (IllegalArgumentException iae) {
            Assert.fail("Os atributos da consulta foram alterados, por favor corrija.");
        }
    }

}
