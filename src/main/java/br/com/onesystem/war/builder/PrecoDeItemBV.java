package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.PrecoDeItem;
import br.com.onesystem.domain.builder.PrecoDeItemBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PrecoDeItemBV implements Serializable {

    private Long id;
    private Item item;
    private ListaDePreco listaDePreco;
    private BigDecimal valor;
    private Date dataDeExpiracao;
    
    public PrecoDeItemBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ListaDePreco getListaDePreco() {
        return listaDePreco;
    }

    public void setListaDePreco(ListaDePreco listaDePreco) {
        this.listaDePreco = listaDePreco;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getDataDeExpiracao() {
        return dataDeExpiracao;
    }

    public void setDataDeExpiracao(Date dataDeExpiracao) {
        this.dataDeExpiracao = dataDeExpiracao;
    }

    public PrecoDeItem construir() throws DadoInvalidoException {
        return new PrecoDeItemBuilder().comItem(item).comDataDeExpiracao(dataDeExpiracao)
                .comListaDePreco(listaDePreco).comValor(valor).construir();
    }

    public PrecoDeItem construirComID() throws DadoInvalidoException {
        return new PrecoDeItemBuilder().comID(id).comItem(item).comDataDeExpiracao(dataDeExpiracao)
                .comListaDePreco(listaDePreco).comValor(valor).construir();
    }
}
