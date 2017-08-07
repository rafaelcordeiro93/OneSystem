package br.com.onesystem.war.builder;

import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Comissao;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.Grupo;
import br.com.onesystem.domain.Margem;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeComanda;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.ItemOrcado;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.domain.PrecoDeItem;
import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.valueobjects.TipoItem;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ItemBV implements Serializable, BuilderView<Item> {

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
    private List<ItemOrcado> itensOrcados;
    private List<ItemDeComanda> itensDeComanda;
    private List<PrecoDeItem> precos;
    private Margem margem;
    private Comissao comissao;

    public ItemBV(Item itemSelecionado) {
        this.id = itemSelecionado.getId();
        this.nome = itemSelecionado.getNome();
        this.barras = itemSelecionado.getBarras();
        this.grupo = itemSelecionado.getGrupo();
        this.estoqueMaximo = itemSelecionado.getEstoqueMaximo();
        this.estoqueMinimo = itemSelecionado.getEstoqueMinimo();
        this.idContabil = itemSelecionado.getIdContabil();
        this.idFabricante = itemSelecionado.getIdFabricante();
        this.grupoFiscal = itemSelecionado.getGrupoFiscal();
        this.marca = itemSelecionado.getMarca();
        this.ncm = itemSelecionado.getNcm();
        this.tipoItem = itemSelecionado.getTipoItem();
        this.unidadeDeMedida = itemSelecionado.getUnidadeDeMedida();
        this.ativo = itemSelecionado.isAtivo();
        this.margem = itemSelecionado.getMargem();
        this.comissao = itemSelecionado.getComissao();
    }

    public ItemBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBarras() {
        return barras;
    }

    public void setBarras(String barras) {
        this.barras = barras;
    }

    public Comissao getComissao() {
        return comissao;
    }

    public void setComissao(Comissao comissao) {
        this.comissao = comissao;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public BigDecimal getEstoqueMaximo() {
        return estoqueMaximo;
    }

    public Margem getMargem() {
        return margem;
    }

    public void setMargem(Margem margem) {
        this.margem = margem;
    }

    public void setEstoqueMaximo(BigDecimal estoqueMaximo) {
        this.estoqueMaximo = estoqueMaximo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public UnidadeMedidaItem getUnidadeDeMedida() {
        return unidadeDeMedida;
    }

    public void setUnidadeDeMedida(UnidadeMedidaItem unidadeDeMedida) {
        this.unidadeDeMedida = unidadeDeMedida;
    }

    public List<AjusteDeEstoque> getListaDeAjustes() {
        return listaDeAjustes;
    }

    public void setListaDeAjustes(List<AjusteDeEstoque> listaDeAjustes) {
        this.listaDeAjustes = listaDeAjustes;
    }

    public List<ItemDeNota> getItensEmitidos() {
        return itensEmitidos;
    }

    public void setItensEmitidos(List<ItemDeNota> itensEmitidos) {
        this.itensEmitidos = itensEmitidos;
    }

    public List<ItemOrcado> getItensOrcados() {
        return itensOrcados;
    }

    public void setItensOrcados(List<ItemOrcado> itensOrcados) {
        this.itensOrcados = itensOrcados;
    }

    public List<ItemDeComanda> getItensDeComanda() {
        return itensDeComanda;
    }

    public void setItensDeComanda(List<ItemDeComanda> itensDeComanda) {
        this.itensDeComanda = itensDeComanda;
    }

    public List<PrecoDeItem> getPrecos() {
        return precos;
    }

    public void setPrecos(List<PrecoDeItem> precos) {
        this.precos = precos;
    }

   

    public TipoItem getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(TipoItem tipoItem) {
        this.tipoItem = tipoItem;
    }

    public String getNcm() {
        return ncm;
    }

    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public GrupoFiscal getGrupoFiscal() {
        return grupoFiscal;
    }

    public void setGrupoFiscal(GrupoFiscal grupoFiscal) {
        this.grupoFiscal = grupoFiscal;
    }

    public String getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(String idFabricante) {
        this.idFabricante = idFabricante;
    }

    public String getIdContabil() {
        return idContabil;
    }

    public void setIdContabil(String idContabil) {
        this.idContabil = idContabil;
    }

    public BigDecimal getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(BigDecimal estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public Item construir() throws DadoInvalidoException {
        return new Item(null, barras, nome, idFabricante, tipoItem, ncm, idContabil, ativo, grupoFiscal, unidadeDeMedida, marca, grupo, estoqueMinimo, estoqueMaximo, margem, comissao);
    }

    public Item construirComID() throws DadoInvalidoException {
        return new Item(id, barras, nome, idFabricante, tipoItem, ncm, idContabil, ativo, grupoFiscal, unidadeDeMedida, marca, grupo, estoqueMinimo, estoqueMaximo, margem, comissao);
    }
}
