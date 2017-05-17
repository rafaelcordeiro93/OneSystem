package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.EstoqueDAO;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.valueobjects.OperacaoFisica;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EstoqueService implements Serializable {

    public List<Estoque> buscarEstoques() {
        return new ArmazemDeRegistros<Estoque>(Estoque.class).listaTodosOsRegistros();
    }

    public BigDecimal buscaSaldoTotalDeEstoque(Item item, Date data) {
        BigDecimal saldo = BigDecimal.ZERO;
        List<SaldoDeEstoque> listaDeEstoque = buscaListaDeSaldoDeEstoque(item, data);
        for (SaldoDeEstoque s : listaDeEstoque) {
            saldo = saldo.add(s.getSaldo());
        }
        return saldo;
    }

    public List<SaldoDeEstoque> buscaListaDeSaldoDeEstoque(Item item, Date data) {
        ConfiguracaoEstoqueService serv = new ConfiguracaoEstoqueService();
        ConfiguracaoEstoque conf = serv.buscar();
        List<Estoque> estoque = new EstoqueDAO().buscarEstoques().porItem(item).porEmissao(data).porContaDeEstoque(conf.getContaDeEstoqueEmpresa())
                .listaDeResultados();
        List<SaldoDeEstoque> saldoDeEstoque = new ArrayList<SaldoDeEstoque>();
        for (Estoque e : estoque) {
            boolean operacao = false;
            for (SaldoDeEstoque saldo : saldoDeEstoque) {
                if (e.getDeposito().getId().equals(saldo.getDeposito().getId())) {
                    if (e.getOperacaoDeEstoque().getOperacaoFisica().equals(OperacaoFisica.ENTRADA)) {
                        saldo.setSaldo(saldo.getSaldo().add(e.getQuantidade()));
                        operacao = true;
                    } else if (e.getOperacaoDeEstoque().getOperacaoFisica().equals(OperacaoFisica.SAIDA)) {
                        saldo.setSaldo(saldo.getSaldo().subtract(e.getQuantidade()));
                        operacao = true;
                    }
                }
            }
            if (!operacao) {
                saldoDeEstoque.add(new SaldoDeEstoque((new Long(saldoDeEstoque.size() + 1)), e.getDeposito(), e.getQuantidade()));
            }
        }

        return saldoDeEstoque;
    }

    public List<SaldoDeEstoque> buscaListaDeSaldoDeDevolucao(Item item, Nota notaDeOrigem) {
        ConfiguracaoEstoqueService serv = new ConfiguracaoEstoqueService();
        ConfiguracaoEstoque conf = serv.buscar();
        List<SaldoDeEstoque> saldoDeEstoque = new ArrayList<SaldoDeEstoque>();
        List<Estoque> estoque = new EstoqueDAO().buscarEstoques().porItem(item).porContaDeEstoque(conf.getContaDeEstoqueEmpresa()).
                porNota(notaDeOrigem).listaDeResultados();
        List<Estoque> estoqueDeDevolucao = new EstoqueDAO().buscarEstoques().porItem(item).porContaDeEstoque(conf.getContaDeEstoqueEmpresa()).
                porNotaDeOrigem(notaDeOrigem).porTipoDeOperacaoDeNota(TipoOperacao.DEVOLUCAO_CLIENTE).listaDeResultados();

        for (Estoque e : estoque) {
            saldoDeEstoque.add(new SaldoDeEstoque((new Long(saldoDeEstoque.size() + 1)), e.getDeposito(), e.getQuantidade()));
        }

        for (Estoque e : estoqueDeDevolucao) {
            for (SaldoDeEstoque saldo : saldoDeEstoque) {
                if (e.getDeposito().getId().equals(saldo.getDeposito().getId())) {
                    if (e.getOperacaoDeEstoque().getOperacaoFisica().equals(OperacaoFisica.ENTRADA)) {
                        saldo.setSaldo(saldo.getSaldo().add(e.getQuantidade()));
                    } else if (e.getOperacaoDeEstoque().getOperacaoFisica().equals(OperacaoFisica.SAIDA)) {
                        saldo.setSaldo(saldo.getSaldo().subtract(e.getQuantidade()));
                    }
                }
            }
        }

        return saldoDeEstoque;
    }

}
