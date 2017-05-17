/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.service;

import br.com.onesystem.dao.EstoqueDAO;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.builder.ItemBuilder;
import br.com.onesystem.domain.builder.NotaEmitidaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class EstoqueService {

    @Test
    public void buscaNotasDeDevolucaoDeItem() {

        try {
            ConfiguracaoEstoqueService serv = new ConfiguracaoEstoqueService();
            ConfiguracaoEstoque conf = serv.buscar();
            Item item = new ItemBuilder().comId(new Long(1)).construir();
            Nota nota = new NotaEmitidaBuilder().comId(new Long(1)).construir();

            List<Estoque> estoque = new EstoqueDAO().buscarEstoques().porItem(item).porContaDeEstoque(conf.getContaDeEstoqueEmpresa()).
                    porNotaDeOrigem(nota).porTipoDeOperacaoDeNota(TipoOperacao.DEVOLUCAO_CLIENTE).listaDeResultados();

            System.out.println("Estoque: " + estoque.size());

        } catch (DadoInvalidoException ex) {
            Logger.getLogger(EstoqueService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
