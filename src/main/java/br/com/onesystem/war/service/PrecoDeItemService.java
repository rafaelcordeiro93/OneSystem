package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.PrecoDeItemDAO;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.PrecoDeItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class PrecoDeItemService implements Serializable {

    public List<PrecoDeItem> buscarPrecos() {
        return new ArmazemDeRegistros<PrecoDeItem>(PrecoDeItem.class).listaTodosOsRegistros();
    }

    public PrecoDeItem buscaListaDePrecoAtual(Item item, ListaDePreco listaDePreco, Date emissao) {
        List<PrecoDeItem> precos = new PrecoDeItemDAO().porItem(item).porListaDePreco(listaDePreco)
                .eNaoExpirado().porUltimaEmissao(emissao).listaDeResultados();
        if (!precos.isEmpty()) {
            return precos.stream().max(Comparator.comparing(PrecoDeItem::getEmissao)).get();
        } else {
            return null;
        }
    }

    public List<PrecoDeItem> buscaListaDePrecoAtual(Item item) {
        List<PrecoDeItem> precos = new PrecoDeItemDAO().porItem(item)
                .eNaoExpirado().listaDeResultados();
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
        List<PrecoDeItem> precos = new PrecoDeItemDAO().porItem(item).listaDeResultados();
        return precos;
    }

}
