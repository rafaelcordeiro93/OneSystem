/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.domain.builder.EstoqueBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.war.builder.EstoqueBV;
import br.com.onesystem.war.builder.QuantidadeDeItemPorDeposito;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafael
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_ITEMDENOTA",
        sequenceName = "SEQ_ITEMDENOTA")
public class ItemDeNota implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEMDENOTA")
    private Long id;
    @NotNull(message = "{item_not_null}")
    @ManyToOne
    private Item item;
    @NotNull(message = "{valor_unitario_not_null}")
    @Max(value = 9999999, message = "{valor_unitario_max}")
    @Min(value = 0, message = "{valor_unitario_min}")
    @Column(nullable = false)
    private BigDecimal unitario = BigDecimal.ZERO;
    @ManyToOne
    private Nota nota;
    @OneToMany(mappedBy = "itemDeNota", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Estoque> estoques;
    private BigDecimal quantidade;
    @Transient
    List<QuantidadeDeItemPorDeposito> listaDeQuantidade;

    public ItemDeNota() {
    }

    public ItemDeNota(Long id, Item item, BigDecimal valorUnitario, List<QuantidadeDeItemPorDeposito> listaDeQuantidade) throws DadoInvalidoException {
        this.id = id;
        this.item = item;
        this.unitario = valorUnitario;
        adicionaQuantidade(listaDeQuantidade);
        this.listaDeQuantidade = listaDeQuantidade;
        ehValido();
    }

    private void adicionaQuantidade(List<QuantidadeDeItemPorDeposito> lista) throws EDadoInvalidoException {
        if (lista == null || lista.isEmpty()) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("Quantidade_De_Item_Por_Deposito_Not_Null_Empty"));
        }
        this.quantidade = lista.stream().map(QuantidadeDeItemPorDeposito::getQuantidade).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    public void geraEstoque() throws DadoInvalidoException {
        estoques = new ArrayList<>();
        for (QuantidadeDeItemPorDeposito q : listaDeQuantidade) {
            for (OperacaoDeEstoque operacaoDeEstoque : nota.getOperacao().getOperacaoDeEstoque()) {
                Estoque e = new EstoqueBuilder().comDeposito(q.getSaldoDeEstoque().getDeposito()).comQuantidade(q.getQuantidade())
                        .comItem(item).comOperacaoDeEstoque(operacaoDeEstoque).comEmissao(nota.getEmissao()).comItemDeNota(this).construir();
                // Adiciona no estoque
                estoques.add(e);
            }
        }
    }

    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public BigDecimal getUnitario() {
        return unitario;
    }

    public Nota getNota() {
        return nota;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public BigDecimal getTotal() {
        return getQuantidade().multiply(unitario);
    }

    public List<QuantidadeDeItemPorDeposito> getListaDeQuantidade() {
        return listaDeQuantidade;
    }

    public String getTotalFormatado() {
        if (nota != null) {
            return MoedaFormatter.format(nota.getMoedaPadrao(), getTotal());
        } else {
            return NumberFormat.getNumberInstance().format(getTotal());
        }
    }

    public String getUnitarioFormatado() {
        if (nota != null) {
            return MoedaFormatter.format(nota.getMoedaPadrao(), getUnitario());
        } else {
            return NumberFormat.getNumberInstance().format(getUnitario());
        }
    }

    public List<Estoque> getEstoques() {
        return estoques;
    }

    public void setEstoques(List<Estoque> estoques) {
        this.estoques = estoques;
    }

    public void preparaInclusao(NotaEmitida nota) {
        if (this.nota == null) {
            this.id = null;
            this.nota = nota;
        }
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("item", "nota", "unitario");
        new ValidadorDeCampos<ItemDeNota>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof ItemDeNota)) {
            return false;
        }
        ItemDeNota outro = (ItemDeNota) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "ItemEmitido{" + "id=" + id + ", item=" + (item != null ? item.getId() : null) + ", quantidade " + quantidade + ", unitario=" + unitario + ", notaEmitida=" + (nota != null ? nota.getId() : null) + ", estoques=" + estoques + '}';
    }

}
