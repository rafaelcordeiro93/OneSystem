package br.com.onesystem.war.service;

import br.com.onesystem.dao.AjusteDeEstoqueDAO;
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "ajusteDeEstoqueService")
@ApplicationScoped
public class AjusteDeEstoqueService implements Serializable {

    public List<AjusteDeEstoque> buscarAjusteDeEstoques() {
        return new ArmazemDeRegistros<AjusteDeEstoque>(AjusteDeEstoque.class).listaTodosOsRegistros();
    }

    public AjusteDeEstoque buscaUltimoAjuste(Item item) {
        List<AjusteDeEstoque> lista = new AjusteDeEstoqueDAO().buscarAjustesDeEstoque().porItem(item)
                .ordenadoPorEmissaoDescrescente().listaDeResultados();
        return lista.size() > 0 ? lista.get(0) : null;
    }

    public BigDecimal buscaMediaDeCustoDeAjuste(Item item) {
        List<AjusteDeEstoque> lista = new AjusteDeEstoqueDAO().buscarAjustesDeEstoque().porItem(item)
                .listaDeResultados();
        if (lista.size() == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal media = BigDecimal.ZERO;
        for (AjusteDeEstoque ade : lista) {
            if (ade.getCusto() == null) {
                break;
            }
            media = media.add(ade.getCusto());
        }
        return media.divide(new BigDecimal(lista.size()), 2, RoundingMode.HALF_UP);
    }

}
