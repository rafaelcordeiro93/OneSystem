/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Comissao;
import br.com.onesystem.domain.Grupo;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemImagem;
import br.com.onesystem.domain.LoteItem;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.domain.Margem;
import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.DetalhamentoDeItem;
import br.com.onesystem.valueobjects.TipoItem;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Rafael Cordeiro
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
    private Margem margem;
    private Comissao comissao;
    private List<ItemImagem> imagens;
    private DetalhamentoDeItem detalhamento;
    private List<LoteItem> loteItem;

    public ItemBuilder comEstoqueMaximo(BigDecimal estoqueMaximo) {
        this.estoqueMaximo = estoqueMaximo;
        return this;
    }

    public ItemBuilder comMargem(Margem margem) {
        this.margem = margem;
        return this;
    }

    public ItemBuilder comComissao(Comissao comissao) {
        this.comissao = comissao;
        return this;
    }

    public ItemBuilder comImagens(List<ItemImagem> imagens) {
        this.imagens = imagens;
        return this;
    }

    public ItemBuilder comDetalhamento(DetalhamentoDeItem detalhamento) {
        this.detalhamento = detalhamento;
        return this;
    }

    public ItemBuilder comUnidadeDeMedida(UnidadeMedidaItem unidadeDeMedida) {
        this.unidadeDeMedida = unidadeDeMedida;
        return this;
    }

    public ItemBuilder comMarca(Marca marca) {
        this.marca = marca;
        return this;
    }

    public ItemBuilder comGrupo(Grupo grupo) {
        this.grupo = grupo;
        return this;
    }

    public ItemBuilder comEstoqueMinimo(BigDecimal estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
        return this;
    }

    public ItemBuilder comGrupoFiscal(GrupoFiscal grupoFiscal) {
        this.grupoFiscal = grupoFiscal;
        return this;
    }

    public ItemBuilder comAtivo(boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public ItemBuilder comIdContabil(String idContabil) {
        this.idContabil = idContabil;
        return this;
    }

    public ItemBuilder comNCM(String ncm) {
        this.ncm = ncm;
        return this;
    }

    public ItemBuilder comTipoItem(TipoItem tipoItem) {
        this.tipoItem = tipoItem;
        return this;
    }

    public ItemBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public ItemBuilder comBarras(String barras) {
        this.barras = barras;
        return this;
    }

    public ItemBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ItemBuilder comIdFabricante(String idFabricante) {
        this.idFabricante = idFabricante;
        return this;
    }

    public ItemBuilder comLoteItem(List<LoteItem> loteItem) {
        this.loteItem = loteItem;
        return this;
    }

    public Item construir() throws DadoInvalidoException {
        return new Item(id, barras, nome, idFabricante, tipoItem, ncm, idContabil, ativo, grupoFiscal, unidadeDeMedida,
                marca, grupo, estoqueMinimo, estoqueMaximo, margem, comissao, imagens, detalhamento, loteItem);
    }

}
