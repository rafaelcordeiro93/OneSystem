package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.EstoqueDAO;
import br.com.onesystem.dao.TituloDAO;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.reportTemplate.SomaSaldoDeTituloPorMoedaReportTemplate;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "estoqueService")
@ApplicationScoped
public class EstoqueService implements Serializable {

    public List<Estoque> buscarEstoques() {
       return new ArmazemDeRegistros<Estoque>(Estoque.class).listaTodosOsRegistros();
    }

}
