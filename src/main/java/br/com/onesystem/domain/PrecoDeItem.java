/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafael
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_PRECODEITEM",
        sequenceName = "SEQ_PRECODEITEM")
public class PrecoDeItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRECODEITEM")
    private Long id;
    @NotNull(message = "{item_not_null}")
    @ManyToOne
    private Item item;
    @NotNull(message = "{lista_preco_not_null}")
    @ManyToOne
    private ListaDePreco listaDePreco;
    @NotNull(message = "{valor_not_null}")
    @Max(message = "{valor_max}", value = 999999999)
    private BigDecimal valor;
    @NotNull(message = "{emissao_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao = new Date();
    @NotNull(message = "{data_expiracao_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeExpiracao;

    public PrecoDeItem() {
    }

    public PrecoDeItem(Long id, Item item, ListaDePreco listaDePreco, BigDecimal valor, Date dataDeExpiracao) throws DadoInvalidoException {
        this.id = id;
        this.item = item;
        this.listaDePreco = listaDePreco;
        this.valor = valor;
        this.dataDeExpiracao = dataDeExpiracao;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public ListaDePreco getListaDePreco() {
        return listaDePreco;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Date getEmissao() {
        return emissao;
    }

    public Date getDataDeExpiracao() {
        return dataDeExpiracao;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("item", "listaDePreco", "valor", "dataDeExpiracao");
        new ValidadorDeCampos<PrecoDeItem>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Conta)) {
            return false;
        }
        PrecoDeItem outro = (PrecoDeItem) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "PrecoDeItem{" + "id=" + id + ", item=" + item.getId() + ", listaDePreco=" + listaDePreco.getId() + ", valor=" + valor + ", emissao=" + emissao + ", dataDeExpiracao=" + dataDeExpiracao + '}';
    }

}
