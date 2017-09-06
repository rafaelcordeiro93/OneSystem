package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.EstoqueDAO;
import br.com.onesystem.domain.Comanda;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeComanda;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.ItemDePedido;
import br.com.onesystem.domain.ItemDePedidoCancelado;
import br.com.onesystem.domain.PedidoAFornecedores;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.NotaRecebida;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.valueobjects.OperacaoFisica;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EstoqueService implements Serializable {

    public List<Estoque> buscarEstoques() {
      return new EstoqueDAO().listaDeResultados();
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
        List<Estoque> estoque = new EstoqueDAO().porItem(item).porEmissao(data).porContaDeEstoque(conf.getContaDeEstoqueEmpresa())
                .porEstoqueAlterado().porNaoCancelado().listaDeResultados();
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

    public BigDecimal buscaUltimoCustoItem(Item item, Date data) {
        ConfiguracaoEstoqueService serv = new ConfiguracaoEstoqueService();
        ConfiguracaoEstoque conf = serv.buscar();
        List<Estoque> estoque = new EstoqueDAO().porItem(item).porEmissao(data).porContaDeEstoque(conf.getContaDeEstoqueEmpresa())
                .porEstoqueAlterado().porNaoCancelado().listaDeResultados();

        if (!estoque.isEmpty()) {
            return estoque.stream().max(Comparator.comparing(Estoque::getEmissao)).get().getValor();
        } else {
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal buscaCustoMedioDeItem(Item item, Date data) {
        ConfiguracaoEstoqueService serv = new ConfiguracaoEstoqueService();
        ConfiguracaoEstoque conf = serv.buscar();
        List<Estoque> estoque = new EstoqueDAO().porItem(item).porEmissao(data).porContaDeEstoque(conf.getContaDeEstoqueEmpresa())
                .porEstoqueAlterado().porNaoCancelado().listaDeResultados();
        if (!estoque.isEmpty()) {
            List<Estoque> collect = estoque.stream().filter((e) -> e.getValor().compareTo(BigDecimal.ZERO) > 0).collect(Collectors.toList());
            if (!collect.isEmpty()) {
                BigDecimal soma = collect.stream().map(Estoque::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
                if (soma.compareTo(BigDecimal.ZERO) > 0) {
                    return soma.divide(new BigDecimal(collect.size()), 2, RoundingMode.HALF_UP);
                } else {
                    return BigDecimal.ZERO;
                }
            } else {
                return BigDecimal.ZERO;
            }
        } else {
            return BigDecimal.ZERO;
        }
    }

    public List<SaldoDeEstoque> buscaListaDeSaldoDe(Item item, Nota notaDeOrigem, TipoOperacao operacaoDesejada) {
        ConfiguracaoEstoqueService serv = new ConfiguracaoEstoqueService();
        ConfiguracaoEstoque conf = serv.buscar();
        List<SaldoDeEstoque> saldoDeEstoque = new ArrayList<SaldoDeEstoque>();
        List<Estoque> estoque = new EstoqueDAO().porItem(item).porContaDeEstoque(conf.getContaDeEstoqueEmpresa()).
                porNota(notaDeOrigem).listaDeResultados();
        List<Estoque> estoqueDaOperacao = new EstoqueDAO().porItem(item).porContaDeEstoque(conf.getContaDeEstoqueEmpresa()).
                porNotaDeOrigem(notaDeOrigem).porTipoDeOperacaoDeNota(operacaoDesejada).listaDeResultados();

        for (Estoque e : estoque) {
            saldoDeEstoque.add(new SaldoDeEstoque((new Long(saldoDeEstoque.size() + 1)), e.getDeposito(), e.getQuantidade()));
        }

        geraSaldoDeEstoque(estoqueDaOperacao, saldoDeEstoque, operacaoDesejada);

        return saldoDeEstoque;
    }

    private void geraSaldoDeEstoque(List<Estoque> estoqueDaOperacao, List<SaldoDeEstoque> saldoDeEstoque, TipoOperacao operacaoDesejada) {
        for (Estoque e : estoqueDaOperacao) {
            for (SaldoDeEstoque saldo : saldoDeEstoque) {
                if (operacaoDesejada == TipoOperacao.DEVOLUCAO_CLIENTE) {
                    if (e.getDeposito().getId().equals(saldo.getDeposito().getId())) {
                        if (e.getOperacaoDeEstoque().getOperacaoFisica().equals(OperacaoFisica.ENTRADA)) {
                            saldo.setSaldo(saldo.getSaldo().subtract(e.getQuantidade()));
                        } else if (e.getOperacaoDeEstoque().getOperacaoFisica().equals(OperacaoFisica.SAIDA)) {
                            saldo.setSaldo(saldo.getSaldo().add(e.getQuantidade()));
                        }
                    }
                } else {
                    if (e.getDeposito().getId().equals(saldo.getDeposito().getId())) {
                        if (e.getOperacaoDeEstoque().getOperacaoFisica().equals(OperacaoFisica.ENTRADA)) {
                            saldo.setSaldo(saldo.getSaldo().add(e.getQuantidade()));
                        } else if (e.getOperacaoDeEstoque().getOperacaoFisica().equals(OperacaoFisica.SAIDA)) {
                            saldo.setSaldo(saldo.getSaldo().subtract(e.getQuantidade()));
                        }
                    }
                }
            }
        }
    }

    public List<SaldoDeEstoque> buscaListaDeSaldoDe(ItemDeComanda item, Comanda comanda, TipoOperacao operacaoDesejada) throws DadoInvalidoException {
        ConfiguracaoEstoqueService serv = new ConfiguracaoEstoqueService();
        ConfiguracaoEstoque conf = serv.buscar();

        List<SaldoDeEstoque> saldoDeEstoque = new ArrayList<SaldoDeEstoque>();
        saldoDeEstoque.add(new SaldoDeEstoque((new Long(saldoDeEstoque.size() + 1)), conf.getDepositoPadrao(), item.getQuantidade()));

        if (comanda.getNotasEmitidas() != null && !comanda.getNotasEmitidas().isEmpty()) {
            List<Estoque> estoqueDaOperacao = new EstoqueDAO().porItem(item.getItem()).porContaDeEstoque(conf.getContaDeEstoqueEmpresa()).
                    porNotasEmitidas(comanda.getNotasEmitidas()).porNaoCancelado().listaDeResultados();
            geraSaldoDeEstoque(estoqueDaOperacao, saldoDeEstoque, operacaoDesejada);
        }

        return saldoDeEstoque;
    }

    public List<SaldoDeEstoque> buscaListaDeSaldoDe(ItemDePedido item, PedidoAFornecedores pedido, TipoOperacao operacaoDesejada) throws DadoInvalidoException {
        ConfiguracaoEstoqueService serv = new ConfiguracaoEstoqueService();
        ConfiguracaoEstoque conf = serv.buscar();

        List<SaldoDeEstoque> saldoDeEstoque = new ArrayList<SaldoDeEstoque>();
        saldoDeEstoque.add(new SaldoDeEstoque((new Long(saldoDeEstoque.size() + 1)), conf.getDepositoPadrao(), item.getQuantidade()));

        if (pedido.getNotasrecebidas() != null && !pedido.getNotasrecebidas().isEmpty()) {
            List<Estoque> estoqueDaOperacao = new EstoqueDAO().porItem(item.getItem()).porContaDeEstoque(conf.getContaDeEstoqueEmpresa())
                    .porNotasRecebidas(pedido.getNotasrecebidas()).porNaoCancelado().listaDeResultados();
            geraSaldoDeEstoque(estoqueDaOperacao, saldoDeEstoque, operacaoDesejada);
        }

        return saldoDeEstoque;
    }

}
