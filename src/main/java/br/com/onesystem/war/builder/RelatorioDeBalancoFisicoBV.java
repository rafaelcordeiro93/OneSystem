package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Item;
import java.io.Serializable;
import java.util.Date;

public class RelatorioDeBalancoFisicoBV implements Serializable {

    private Date emissao;
    private Item item;

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

}
