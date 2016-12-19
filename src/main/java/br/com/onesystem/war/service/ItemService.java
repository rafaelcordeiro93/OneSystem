package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Item;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "itemService")
@ApplicationScoped
public class ItemService implements Serializable {
    
    public List<Item> buscarItems(){
        return new ArmazemDeRegistros<Item>(Item.class).listaTodosOsRegistros();
    }
    
    public BigDecimal ultimoCusto(Item item){
        AjusteDeEstoqueService aj = new AjusteDeEstoqueService();
        AjusteDeEstoque uAjuste = aj.buscaUltimoAjuste(item);
        return uAjuste.getCusto() != null ? uAjuste.getCusto() : BigDecimal.ZERO;
    }
    
    public BigDecimal custoMedio(Item item){
        AjusteDeEstoqueService aj = new AjusteDeEstoqueService();
        return aj.buscaMediaDeCustoDeAjuste(item);
    }
    
}
