package br.com.onesystem.war.service;

import br.com.onesystem.dao.EstoqueDAO;
import br.com.onesystem.domain.Comanda;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeComanda;
import br.com.onesystem.domain.ItemDePedido;
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
import javax.inject.Inject;

/**
 * @author Rafael Fernando Rauber
 *
 */
public class EstoqueService implements Serializable {

    @Inject
    private EstoqueDAO dao;

    @Inject
    private DepositoService depositoService;

    @Inject
    private ConfiguracaoEstoque configuracaoEstoque;

    public List<Estoque> buscarEstoques() {
        return dao.listaDeResultados();
    }

    public BigDecimal buscaSaldoTotalDeEstoque(Item item, Date data) {
        BigDecimal saldo = BigDecimal.ZERO;
        List<SaldoDeEstoque> listaDeEstoque = buscaListaDeSaldoDeEstoque(item, data);
        for (SaldoDeEstoque s : listaDeEstoque) {
            saldo = saldo.add(s.getSaldo());
        }
        return saldo;
    }

    /**
     * @author Rafael Fernando Rauber
     *
     * Traz uma lista com o saldo de estoque dos itens em cada depósito,
     * indiferente se tem ou não movimentação no depósito.
     *
     * @param item Item a ser buscado o saldo
     * @param data O saldo será buscado até a data informada
     *
     * @return Retorna uma lista de saldo de estoque do item em todos os
     * depósito até a data informada.
     */
    public List<SaldoDeEstoque> buscaListaDeSaldoDeEstoqueEmTodosDepositos(Item item, Date data) {
        //Busca todos os depósitos
        List<Deposito> depositos = depositoService.buscarDepositos();

        //Busca as movimentações de estoque do item.
        List<Estoque> estoque = dao.porItem(item).ateEmissao(data).porContaDeEstoque(configuracaoEstoque.getContaDeEstoqueEmpresa())
                .porEstoqueAlterado().porNaoCancelado().listaDeResultados();

        //Cria a lista de saldo de estoque
        List<SaldoDeEstoque> saldos = new ArrayList<>();

        for (Deposito deposito : depositos) {
            // Busca todas as entradas do item no depósito iterado e soma as quantidades
            BigDecimal entradas = BigDecimal.ZERO;
            List<Estoque> estoqueEntradas = estoque.stream().filter(e -> e.getDeposito().equals(deposito) && e.getOperacaoDeEstoque().getOperacaoFisica().equals(OperacaoFisica.ENTRADA)).collect(Collectors.toList());
            if (!estoqueEntradas.isEmpty()) {
                entradas = estoqueEntradas.stream().map(Estoque::getQuantidade).reduce(BigDecimal.ZERO, BigDecimal::add);
            }

            // Busca todas as saídas do item no depósito iterado e soma as quantidades
            BigDecimal saidas = BigDecimal.ZERO;
            List<Estoque> estoqueSaidas = estoque.stream().filter(e -> e.getDeposito().equals(deposito) && e.getOperacaoDeEstoque().getOperacaoFisica().equals(OperacaoFisica.SAIDA)).collect(Collectors.toList());
            if (!estoqueSaidas.isEmpty()) {
                saidas = estoqueSaidas.stream().map(Estoque::getQuantidade).reduce(BigDecimal.ZERO, BigDecimal::add);
            }

            // Realiza a operação de entradas menos saidas
            BigDecimal resultado = entradas.subtract(saidas);
            saldos.add(new SaldoDeEstoque(new Long(saldos.size() + 1), deposito, resultado));
        }
        return saldos;
    }

    /**
     * @author Rafael Fernando Rauber
     *
     * Traz uma lista com o saldo de estoque dos itens em cada depósito,
     * filtrando sobre as movimentações efetuadas na CONTA DE ESTOQUE PADRAO DA
     * EMPRESA. Caso o item não tiver movimentação a lista será vazia.
     *
     * @param item Item a ser buscado o saldo
     * @param data O saldo será buscado até a data informada
     *
     * @return Retorna uma lista de saldo de estoque das movimentações do item
     * informado até a data informada.
     */
    public List<SaldoDeEstoque> buscaListaDeSaldoDeEstoque(Item item, Date data) {

        //Busca as movimentações de estoque do item.
        List<Estoque> estoque = dao.porItem(item).ateEmissao(data).porContaDeEstoque(configuracaoEstoque.getContaDeEstoqueEmpresa())
                .porEstoqueAlterado().porNaoCancelado().listaDeResultados();

        List<SaldoDeEstoque> saldos = new ArrayList<>();

        // Itera sobre todos os estoques e é constituido por 3 fazes.
        for (Estoque e : estoque) {
            /* Fase 1
             * Instancia nova operacao - Variável de controle para saber se 
             * houve operação na fase 2. */
            boolean realizouOperacao = false;

            /* Fase 2 
             * Itera sobre os saldos de estoque. A primeira passagem não será efetuada
             * devido a lista de saldo estar vazia, assim será efetuada a fase 3 que tem o
             * objetivo de incluir um saldo dentro da lista de saldos.
             * 
             * Após a primeira passagem a lista de saldo não está mais vazia, e 
             * é realizado a primeira condição verificando se o id do depósito do estoque
             * é o mesmo do id do depósito no saldo. Se isso for verdadeiro, a segunda e 
             * terceita condição irão verificar se a operação será de entrada ou saída de estoque
             * e realizarão a operação. Por fim será setado a variável de controle realizouOperacao para verdadeiro
             * a fim de não criar um novo saldo, pois o mesmo já foi alterado.
             */
            for (SaldoDeEstoque saldo : saldos) {
                // Primeira condição
                if (e.getDeposito().getId().equals(saldo.getDeposito().getId())) {

                    // Segunda condição
                    if (e.getOperacaoDeEstoque().getOperacaoFisica().equals(OperacaoFisica.ENTRADA)) {
                        saldo.setSaldo(saldo.getSaldo().add(e.getQuantidade()));
                        realizouOperacao = true;
                    } // Terceira condição
                    else if (e.getOperacaoDeEstoque().getOperacaoFisica().equals(OperacaoFisica.SAIDA)) {
                        saldo.setSaldo(saldo.getSaldo().subtract(e.getQuantidade()));
                        realizouOperacao = true;
                    }
                }
            }

            /* Fase 3
             * Cria um saldo novo se a operação não foi realizada devido a inexistência de um saldo
             * com o mesmo depósito do elemento de Estoque.
             * Realiza somente uma comparação para verifica se a operação fisica deve ser de saída ou de entrada.
             */
            if (!realizouOperacao) {
                BigDecimal saldo;
                if (e.getOperacaoDeEstoque().getOperacaoFisica().equals(OperacaoFisica.SAIDA)) {
                    saldo = BigDecimal.ZERO.subtract(e.getQuantidade());
                } else {
                    saldo = e.getQuantidade();
                }
                saldos.add(new SaldoDeEstoque((new Long(saldos.size() + 1)), e.getDeposito(), saldo));
            }

        }

        return saldos;
    }

    public BigDecimal buscaUltimoCustoItem(Item item, Date data) {
        List<Estoque> estoque = dao.porItem(item).ateEmissao(data).porContaDeEstoque(configuracaoEstoque.getContaDeEstoqueEmpresa())
                .porEstoqueAlterado().porNaoCancelado().porNaoSerItemCondicional().listaDeResultados();

        if (!estoque.isEmpty()) {
            Estoque est = estoque.stream().filter(e -> (e.getItemDeNota() != null && e.getItemDeNota().getNota() instanceof NotaRecebida)
                    || e.getAjusteDeEstoque() != null).max(Comparator.comparing(Estoque::getEmissao)).orElse(null);
            if (est == null) {
                return BigDecimal.ZERO;
            }
            return est.getValor();
        } else {
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal buscaCustoMedioDeItem(Item item, Date data) {
        List<Estoque> estoque = dao.porItem(item).ateEmissao(data).porContaDeEstoque(configuracaoEstoque.getContaDeEstoqueEmpresa())
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
        List<SaldoDeEstoque> saldoDeEstoque = new ArrayList<SaldoDeEstoque>();
        List<Estoque> estoque = dao.porItem(item).porContaDeEstoque(configuracaoEstoque.getContaDeEstoqueEmpresa()).
                porNota(notaDeOrigem).listaDeResultados();
        List<Estoque> estoqueDaOperacao = dao.porItem(item).porContaDeEstoque(configuracaoEstoque.getContaDeEstoqueEmpresa()).
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
        List<SaldoDeEstoque> saldoDeEstoque = new ArrayList<SaldoDeEstoque>();
        saldoDeEstoque.add(new SaldoDeEstoque((new Long(saldoDeEstoque.size() + 1)), configuracaoEstoque.getDepositoPadrao(), item.getQuantidade()));

        if (comanda.getNotasEmitidas() != null && !comanda.getNotasEmitidas().isEmpty()) {
            List<Estoque> estoqueDaOperacao = dao.porItem(item.getItem()).porContaDeEstoque(configuracaoEstoque.getContaDeEstoqueEmpresa()).
                    porNotasEmitidas(comanda.getNotasEmitidas()).porNaoCancelado().listaDeResultados();
            geraSaldoDeEstoque(estoqueDaOperacao, saldoDeEstoque, operacaoDesejada);
        }

        return saldoDeEstoque;
    }

    public List<SaldoDeEstoque> buscaListaDeSaldoDe(ItemDePedido item, PedidoAFornecedores pedido, TipoOperacao operacaoDesejada) throws DadoInvalidoException {
        List<SaldoDeEstoque> saldoDeEstoque = new ArrayList<SaldoDeEstoque>();
        saldoDeEstoque.add(new SaldoDeEstoque((new Long(saldoDeEstoque.size() + 1)), configuracaoEstoque.getDepositoPadrao(), item.getQuantidade()));

        if (pedido.getNotasrecebidas() != null && !pedido.getNotasrecebidas().isEmpty()) {
            List<Estoque> estoqueDaOperacao = dao.porItem(item.getItem()).porContaDeEstoque(configuracaoEstoque.getContaDeEstoqueEmpresa())
                    .porNotasRecebidas(pedido.getNotasrecebidas()).porNaoCancelado().listaDeResultados();
            geraSaldoDeEstoque(estoqueDaOperacao, saldoDeEstoque, operacaoDesejada);
        }

        return saldoDeEstoque;
    }

}
