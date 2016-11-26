package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Grupo;
import br.com.onesystem.domain.IVA;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.valueobjects.TipoItem;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.math.BigDecimal;

public class ItemBV implements Serializable {

    private Long id;
    private String nome;
    private String barras;
    private Grupo grupo;
    private BigDecimal estoqueMaximo;
    private boolean ativo = true;
    private UnidadeMedidaItem unidadeMedida;
    private TipoItem tipoItem;
    private BigDecimal saldo = BigDecimal.ZERO;
    private String ncm;
    private Marca marca;
    private IVA iva;
    private String idFabricante;
    private String idContabil;
    private BigDecimal estoqueMinimo;

    public ItemBV(Item itemSelecionado) {
        this.id = itemSelecionado.getId();
        this.nome = itemSelecionado.getNome();
        this.barras = itemSelecionado.getBarras();
        this.grupo = itemSelecionado.getGrupo();
        this.estoqueMaximo = itemSelecionado.getEstoqueMaximo();
        this.estoqueMinimo = itemSelecionado.getEstoqueMinimo();
        this.idContabil = itemSelecionado.getIdContabil();
        this.idFabricante = itemSelecionado.getIdFabricante();
        this.iva = itemSelecionado.getIva();
        this.marca = itemSelecionado.getMarca();
        this.ncm = itemSelecionado.getNcm();
        this.saldo = itemSelecionado.getSaldo();
        this.tipoItem = itemSelecionado.getTipoItem();
        this.unidadeMedida = itemSelecionado.getUnidadeDeMedida();
        this.ativo = itemSelecionado.isAtivo();
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

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public BigDecimal getEstoqueMaximo() {
        return estoqueMaximo;
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

    public UnidadeMedidaItem getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedidaItem unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public TipoItem getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(TipoItem tipoItem) {
        this.tipoItem = tipoItem;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
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

    public IVA getIva() {
        return iva;
    }

    public void setIva(IVA iva) {
        this.iva = iva;
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
        return new Item(null, barras, nome, idFabricante, tipoItem, ncm, idContabil, ativo, iva, unidadeMedida, marca, grupo, estoqueMinimo, estoqueMaximo, saldo);
    }

    public Item construirComID() throws DadoInvalidoException {
        return new Item(id, barras, nome, idFabricante, tipoItem, ncm, idContabil, ativo, iva, unidadeMedida, marca, grupo, estoqueMinimo, estoqueMaximo, saldo);
    }
}
