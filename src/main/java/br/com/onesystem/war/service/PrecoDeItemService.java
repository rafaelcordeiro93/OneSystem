package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.PrecoDeItemDAO;
import br.com.onesystem.dao.TituloDAO;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.PrecoDeItem;
import br.com.onesystem.reportTemplate.SaldoDeConta;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.valueobjects.OperacaoFisica;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "precoDeItemService")
@ApplicationScoped
public class PrecoDeItemService implements Serializable {

    public List<PrecoDeItem> buscarPrecos() {
        return new ArmazemDeRegistros<PrecoDeItem>(PrecoDeItem.class).listaTodosOsRegistros();
    }

    public List<PrecoDeItem> buscaListaDePrecoAtual(Item item) {
        List<PrecoDeItem> precos = new PrecoDeItemDAO().buscarPrecos().porItem(item)
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
        List<PrecoDeItem> precos = new PrecoDeItemDAO().buscarPrecos().porItem(item).listaDeResultados();
        return precos;
    }

}
