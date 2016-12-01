package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.EstoqueDAO;
import br.com.onesystem.dao.TituloDAO;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
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

@ManagedBean(name = "estoqueService")
@ApplicationScoped
public class EstoqueService implements Serializable {

    public List<Estoque> buscarEstoques() {
        return new ArmazemDeRegistros<Estoque>(Estoque.class).listaTodosOsRegistros();
    }

    public List<SaldoDeEstoque> buscaSaldoDeEstoque(Item item) {
        List<Estoque> estoque = new EstoqueDAO().buscarEstoques().eItem(item)
                .listaDeResultados();
        List<SaldoDeEstoque> saldoDeEstoque = new ArrayList<SaldoDeEstoque>();
        for (Estoque e : estoque) {
            boolean operacao = false;
            for (SaldoDeEstoque saldo : saldoDeEstoque) {
                if (e.getDeposito().getId().equals(saldo.getDeposito().getId())) {
                    if (e.getTipo().equals(OperacaoFisica.ENTRADA)) {
                        saldo.setSaldo(saldo.getSaldo().add(e.getSaldo()));
                        operacao = true;
                    } else {
                        saldo.setSaldo(saldo.getSaldo().subtract(e.getSaldo()));
                        operacao = true;
                    }
                }
            }
            if (!operacao) {
                saldoDeEstoque.add(new SaldoDeEstoque((new Long(saldoDeEstoque.size() + 1)), e.getDeposito(), e.getSaldo()));
            }
        }

        return saldoDeEstoque;
    }

}
