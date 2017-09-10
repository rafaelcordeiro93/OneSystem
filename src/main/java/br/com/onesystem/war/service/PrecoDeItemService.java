package br.com.onesystem.war.service;

import br.com.onesystem.dao.PrecoDeItemDAO;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.PrecoDeItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

public class PrecoDeItemService implements Serializable {

    @Inject
    private PrecoDeItemDAO dao;
    
    public List<PrecoDeItem> buscarPrecos() {
        return dao.listaDeResultados();
    }

    public PrecoDeItem buscaListaDePrecoAtual(Item item, ListaDePreco listaDePreco, Date emissao) {
        PrecoDeItem preco = dao.porItem(item).porListaDePreco(listaDePreco)
                .eNaoExpirado().naMaiorEmissao(emissao).resultado();
        return preco;
    }

    public List<PrecoDeItem> buscaListaDePrecoAtual(Item item) {
        List<PrecoDeItem> precos = dao.porItem(item)
                .naMaiorEmissao(new Date()).eNaoExpirado().listaDeResultados();
        List<PrecoDeItem> lista = new ArrayList<PrecoDeItem>();
        for (PrecoDeItem p : precos) {
            boolean operacao = true;
            for (PrecoDeItem l : lista) {
                if (p.getListaDePreco().equals(l.getListaDePreco())) {
                    operacao = false;
                    if (p.getEmissao().after(l.getEmissao())) {
                        lista.set(lista.indexOf(l), p);
                    }
                }
            }
            if (operacao) {
                lista.add(p);
            }
        }
        return lista;
    }

    public List<PrecoDeItem> buscaTodosPrecos(Item item) {
        List<PrecoDeItem> precos = dao.porItem(item).listaDeResultados();
        return precos;
    }

}
