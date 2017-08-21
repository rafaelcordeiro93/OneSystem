package br.com.onesystem.dao;

import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.PrecoDeItem;
import java.util.Calendar;
import java.util.Date;

public class PrecoDeItemDAO extends GenericDAO<PrecoDeItem> {

    public PrecoDeItemDAO() {
        super(PrecoDeItem.class);
    }

    public PrecoDeItemDAO porItem(Item item) {
        where += " and precoDeItem.item = :pItem ";
        parametros.put("pItem", item);
        return this;
    }

    public PrecoDeItemDAO porUltimaEmissao(Date emissao) {
        where += " and precoDeItem.emissao <= :pEmissao ";
        parametros.put("pEmissao", emissao);
        return this;
    }
    
     public PrecoDeItemDAO naMaiorEmissao(Date emissao) {
        where += " and precoDeItem.emissao in (select max(pdi.emissao) from PrecoDeItem pdi "
                + "where pdi.emissao <= :pEmissao "
                + "group by pdi.listaDePreco) ";
        parametros.put("pEmissao", emissao);
        return this;
    }

    public PrecoDeItemDAO eNaoExpirado() {
        where += " and (precoDeItem.dataDeExpiracao >= :pDataDeExpiracao or precoDeItem.dataDeExpiracao is null) ";
        parametros.put("pDataDeExpiracao", new Date());
        return this;
    }

    public PrecoDeItemDAO porListaDePreco(ListaDePreco listaDePreco) {
        where += " and precoDeItem.listaDePreco = :pListaDePreco ";
        parametros.put("pListaDePreco", listaDePreco);
        return this;
    }

    public PrecoDeItemDAO ordenadoPorEmissaoDecrescente() {
        order += " order by emissao desc ";
        return this;
    }
    
}
