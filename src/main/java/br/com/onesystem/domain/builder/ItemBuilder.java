/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Comissao;
import br.com.onesystem.domain.Grupo;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.domain.Margem;
import br.com.onesystem.domain.PrecoDeItem;
import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoItem;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ItemBuilder {

    private Long id;
    private String barras;
    private String nome;
    private String idFabricante;
    private TipoItem tipoItem;
    private String ncm;
    private String idContabil;
    private boolean ativo;
    private GrupoFiscal grupoFiscal;
    private UnidadeMedidaItem unidadeDeMedida;
    private Marca marca;
    private Grupo grupo;
    private BigDecimal estoqueMinimo;
    private BigDecimal estoqueMaximo;
    private List<AjusteDeEstoque> listaDeAjustes;
    private List<ItemDeNota> itensEmitidos;
    private List<PrecoDeItem> precos;
    private Margem margem;
    private Comissao comissao;

    public ItemBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public Item construir() throws DadoInvalidoException {
        return new Item(id, barras, nome, idFabricante, tipoItem, ncm, idContabil, ativo, grupoFiscal, unidadeDeMedida, marca, grupo, estoqueMinimo, estoqueMaximo, margem, comissao);
    }

}
