
import br.com.onesystem.domain.builder.EstadoBuilder;
import br.com.onesystem.domain.builder.PaisBuilder;
import br.com.onesystem.dao.AdicionaDAOConsole;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Cep;
import br.com.onesystem.domain.Cfop;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Cidade;
import br.com.onesystem.domain.CobrancaVariavel;
import br.com.onesystem.domain.CobrancaFixa;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.ConfiguracaoContabil;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Estado;
import br.com.onesystem.domain.FiltroDeRelatorio;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.TabelaDeTributacao;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.Janela;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.domain.Modulo;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Pais;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.PessoaFisica;
import br.com.onesystem.domain.Privilegio;
import br.com.onesystem.domain.SituacaoFiscal;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.domain.builder.CepBuilder;
import br.com.onesystem.domain.builder.CfopBuilder;
import br.com.onesystem.domain.builder.CidadeBuilder;
import br.com.onesystem.domain.builder.ContaDeEstoqueBuilder;
import br.com.onesystem.domain.builder.ModeloDeRelatorioBuilder;
import br.com.onesystem.domain.builder.OperacaoBuilder;
import br.com.onesystem.domain.builder.SituacaoFiscalBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.ClassificacaoFinanceira;
import br.com.onesystem.valueobjects.DetalhamentoDeItem;
import br.com.onesystem.valueobjects.EstadoDeBaixa;
import br.com.onesystem.valueobjects.EstadoDeCheque;
import br.com.onesystem.valueobjects.EstadoDeNota;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.OperacaoFisica;
import br.com.onesystem.valueobjects.SituacaoDeCobranca;
import br.com.onesystem.valueobjects.TipoBandeira;
import br.com.onesystem.valueobjects.TipoContabil;
import br.com.onesystem.valueobjects.TipoCorMenu;
import br.com.onesystem.valueobjects.TipoDeBusca;
import br.com.onesystem.valueobjects.TipoDeCalculoDeCusto;
import br.com.onesystem.valueobjects.TipoDeFormacaoDePreco;
import br.com.onesystem.valueobjects.TipoFormatacaoNumero;
import br.com.onesystem.valueobjects.TipoItem;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoOperacao;
import br.com.onesystem.valueobjects.TipoPessoa;
import br.com.onesystem.valueobjects.TipoRelatorio;
import br.com.onesystem.valueobjects.Totalizador;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.hibernate.exception.ConstraintViolationException;

public class DadosIniciais {

    public static void main(String[] args) throws DadoInvalidoException {

        //Bundle ====================================================
        BundleUtil bundle = new BundleUtil();

        //País ====================================================
        Pais pais = new PaisBuilder().comNome("Paraguai").comCodigoPais(new Long(12)).comCodigoReceita(new Long(32)).construir();
        new AdicionaDAOConsole<>().adiciona(pais);

        //Estado ====================================================
        Estado estado = new EstadoBuilder().comNome("Alto Paraná").comPais(pais).comSigla("CD").construir();
        new AdicionaDAOConsole<>().adiciona(estado);

        //Cidade ====================================================
        Cidade city = new CidadeBuilder().comNome("Ciudad del Leste").comEstado(estado).construir();
        new AdicionaDAOConsole<>().adiciona(city);

        //Cep =======================================================
        Cep cep = new CepBuilder().comCep("85950-000").comCidade(city).construir();
        new AdicionaDAOConsole<>().adiciona(cep);

        Pessoa rauber = new PessoaFisica(null, null, null, "Rauber", TipoPessoa.PESSOA_FISICA, null, true, null, null, true, true, true, true, null, new Double(0), null, null, null, cep, null, "rauber@rrminds.com", null, "768");
        Pessoa cordeiro = new PessoaFisica(null, null, null, "Cordeiro", TipoPessoa.PESSOA_FISICA, null, true, null, null, true, true, true, true, null, new Double(0), null, null, null, cep, null, "cordeiro@rrminds.com", null, "635A");
        new AdicionaDAOConsole<Pessoa>().adiciona(cordeiro);
        new AdicionaDAOConsole<Pessoa>().adiciona(rauber);

        GrupoDePrivilegio grupoDePrivilegio = new GrupoDePrivilegio(null, "Administrador");
        new AdicionaDAOConsole<GrupoDePrivilegio>().adiciona(grupoDePrivilegio);

        // -- Adiciona Módulos
        AdicionaDAOConsole<Modulo> daoModulo = new AdicionaDAOConsole<>();

        Modulo arq = new Modulo(null, "Arquivo");
        Modulo fin = new Modulo(null, "Financeiro");
        Modulo con = new Modulo(null, bundle.getLabel("Contabil"));
        Modulo cam = new Modulo(null, "Câmbio");
        Modulo rela = new Modulo(null, "Relatórios");
        Modulo pref = new Modulo(null, "Preferências");
        Modulo login = new Modulo(null, "Login");
        Modulo admin = new Modulo(null, "Administrativo");
        Modulo estoque = new Modulo(null, "Estoque");
        Modulo vendas = new Modulo(null, "Vendas");

        daoModulo.adiciona(arq);
        daoModulo.adiciona(fin);
        daoModulo.adiciona(con);
        daoModulo.adiciona(cam);
        daoModulo.adiciona(rela);
        daoModulo.adiciona(admin);
        daoModulo.adiciona(pref);
        daoModulo.adiciona(login);
        daoModulo.adiciona(estoque);
        daoModulo.adiciona(vendas);

        // -- Adiciona Módulos
        AdicionaDAOConsole<Janela> daoJanela = new AdicionaDAOConsole<>();

        //Modulo de Arquivo
        Janela dashboard = new Janela(null, "Dashbord", "/dashboard.xhtml", arq);
        Janela dashboardGestao = new Janela(null, "Dashbord de Gestao", "/dashboardGestao.xhtml", arq);

        Janela jpais = new Janela(null, "País", "/menu/arquivo/pais.xhtml", arq);
        Janela jestado = new Janela(null, "Estado", "/menu/arquivo/estado.xhtml", arq);
        Janela cidade = new Janela(null, "Cidade", "/menu/arquivo/cidade.xhtml", arq);
        Janela jcep = new Janela(null, "Cep", "/menu/arquivo/cep.xhtml", arq);
        Janela jpessoa = new Janela(null, "Pessoa", "/menu/arquivo/pessoa.xhtml", arq);
        Janela jpessoaimport = new Janela(null, "Importador de Pessoas", "/menu/arquivo/importarPessoa.xhtml", arq);

        daoJanela.adiciona(dashboard);
        daoJanela.adiciona(jpais);
        daoJanela.adiciona(jestado);
        daoJanela.adiciona(cidade);
        daoJanela.adiciona(jcep);
        daoJanela.adiciona(jpessoa);
        daoJanela.adiciona(jpessoaimport);
        daoJanela.adiciona(dashboardGestao);

        //Modulo de Notas
        Janela notaSaida = new Janela(null, "Nota de Saida", "/menu/vendas/notaEmitida.xhtml", vendas);
        Janela consultaNotaSaida = new Janela(null, "Consulta Nota de Saida", "/menu/vendas/consultar/consultaNotaEmitida.xhtml", vendas);
        Janela orcamento = new Janela(null, "Orçamento", "/menu/vendas/orcamento.xhtml", vendas);
        Janela consultaOrcamento = new Janela(null, "Consulta Orçamento", "/menu/vendas/consultar/consultaOrcamento.xhtml", vendas);
        Janela comanda = new Janela(null, "Comanda", "/menu/vendas/comanda.xhtml", vendas);
        Janela consultaComanda = new Janela(null, "Consulta Comanda", "/menu/vendas/consultar/consultaComanda.xhtml", vendas);
        Janela condicional = new Janela(null, "Condicional", "/menu/vendas/condicional.xhtml", vendas);
        Janela consultaCondicional = new Janela(null, "Consulta Condicional", "/menu/vendas/consultar/consultaCondicional.xhtml", vendas);
        Janela retificacaoDeVenda = new Janela(null, "Retificação de Venda", "/menu/vendas/retificacaoDeVenda.xhtml", vendas);

        daoJanela.adiciona(notaSaida);
        daoJanela.adiciona(consultaNotaSaida);
        daoJanela.adiciona(orcamento);
        daoJanela.adiciona(consultaOrcamento);
        daoJanela.adiciona(comanda);
        daoJanela.adiciona(consultaComanda);
        daoJanela.adiciona(condicional);
        daoJanela.adiciona(consultaCondicional);
        daoJanela.adiciona(retificacaoDeVenda);

        //Modulo de Estoque
        Janela jitem = new Janela(null, "Item", "/menu/estoque/item.xhtml", estoque);
        Janela conhecimentoFrete = new Janela(null, "Conhecimento de Frete", "/menu/estoque/conhecimentoDeFrete.xhtml", estoque);
        Janela ajusteEstoque = new Janela(null, "Ajuste de Estoque", "/menu/estoque/ajusteDeEstoque.xhtml", estoque);
        Janela contaEstoque = new Janela(null, "Conta de Estoque", "/menu/estoque/contaDeEstoque.xhtml", estoque);
        Janela retificacaoDeCompra = new Janela(null, "Retificação de Venda", "/menu/estoque/retificacaoDeCompra.xhtml", estoque);
        Janela notaRecebida = new Janela(null, "Nota Recebida", "/menu/estoque/notaRecebida.xhtml", estoque);

        Janela margem = new Janela(null, "Margem", "/menu/estoque/cadastros/margem.xhtml", estoque);
        Janela unidadeMedida = new Janela(null, "Unidade de Medida", "/menu/estoque/cadastros/unidadeMedidaItem.xhtml", estoque);
        Janela marca = new Janela(null, "Marca", "/menu/estoque/cadastros/marca.xhtml", estoque);
        Janela depositos = new Janela(null, "Depositos", "/menu/estoque/cadastros/deposito.xhtml", estoque);
        Janela listaPreco = new Janela(null, "Lista de Preco", "/menu/estoque/cadastros/listaDePreco.xhtml", estoque);
        Janela comissao = new Janela(null, "Comissao", "/menu/estoque/cadastros/comissao.xhtml", estoque);
        Janela grupo = new Janela(null, "Grupo", "/menu/estoque/cadastros/grupo.xhtml", estoque);

        Janela consultaNotaRecebida = new Janela(null, "Consulta Nota Recebida", "/menu/estoque/consultar/consultaNotaRecebida.xhtml", estoque);

        daoJanela.adiciona(jitem);
        daoJanela.adiciona(margem);
        daoJanela.adiciona(conhecimentoFrete);
        daoJanela.adiciona(ajusteEstoque);
        daoJanela.adiciona(contaEstoque);
        daoJanela.adiciona(unidadeMedida);
        daoJanela.adiciona(marca);
        daoJanela.adiciona(depositos);
        daoJanela.adiciona(listaPreco);
        daoJanela.adiciona(comissao);
        daoJanela.adiciona(grupo);
        daoJanela.adiciona(notaRecebida);
        daoJanela.adiciona(consultaNotaRecebida);
        daoJanela.adiciona(retificacaoDeCompra);

        //Modulo Financeiro
        Janela baixa = new Janela(null, "Baixa", "/menu/financeiro/baixa.xhtml", fin);
        Janela caixa = new Janela(null, "Caixa", "/menu/financeiro/caixa.xhtml", fin);
        Janela extratoConta = new Janela(null, "Extrato Conta", "/menu/financeiro/extratoConta.xhtml", fin);
        Janela cotacoes = new Janela(null, "Cotacao", "/menu/financeiro/cotacao.xhtml", fin);
        Janela pagarValores = new Janela(null, "Pagar Valores", "/menu/financeiro/pagamento.xhtml", fin);
        Janela receberValores = new Janela(null, "Receber Valores", "/menu/financeiro/recebimento.xhtml", fin);
        Janela faturaLegada = new Janela(null, "Fatura Legada", "/menu/financeiro/faturaLegada.xhtml", fin);
        Janela faturaEmitida = new Janela(null, "Fatura Emitida", "/menu/financeiro/faturaEmitida.xhtml", fin);
        Janela faturaRecebida = new Janela(null, "Fatura Recebida", "/menu/financeiro/faturaRecebida.xhtml", fin);

        Janela jbanco = new Janela(null, "Banco", "/menu/financeiro/cadastros/banco.xhtml", fin);
        Janela formarecebimento = new Janela(null, "Formas de Recebimento", "/menu/financeiro/cadastros/formaDeRecebimento.xhtml", fin);
        Janela boletoDeCartao = new Janela(null, "Boleto de Cartao", "/menu/financeiro/cadastros/boletoDeCartao.xhtml", fin);
        Janela cheque = new Janela(null, "Cheque", "/menu/financeiro/cadastros/cheque.xhtml", fin);
        Janela moeda = new Janela(null, "Moeda", "/menu/financeiro/cadastros/moeda.xhtml", fin);
        Janela cartao = new Janela(null, "Cartao", "/menu/financeiro/cadastros/cartao.xhtml", fin);
        Janela despProvisi = new Janela(null, "Despesa Provisionada", "/menu/financeiro/cadastros/despesaProvisionada.xhtml", fin);
        Janela jconta = new Janela(null, "Conta", "/menu/financeiro/cadastros/conta.xhtml", fin);
        Janela transfLucro = new Janela(null, "Tranferência de Lucro", "/menu/financeiro/cadastros/transferenciaDespesaProvisionada.xhtml", fin);
        Janela receitProvisi = new Janela(null, "Receita Provisionada", "/menu/financeiro/cadastros/receitaProvisionada.xhtml", fin);

        Janela consultaCobranca = new Janela(null, "Consulta de Cobrança", "/menu/financeiro/consultar/consultaCobranca.xhtml", fin);
        Janela consultaDepositoBancario = new Janela(null, "Consulta Depósito Bancário", "/menu/financeiro/consultar/consultaDepositoBancario.xhtml", fin);
        Janela consultaLancamentoBancario = new Janela(null, "Consulta Lançamento Bancário", "/menu/financeiro/consultar/consultaLancamentoBancario.xhtml", fin);
        Janela consultaSaqueBancario = new Janela(null, "Consulta Saque Bancário", "/menu/financeiro/consultar/consultaSaqueBancario.xhtml", fin);
        Janela consultaTransferencia = new Janela(null, "Consulta Transferência Bancária", "/menu/financeiro/consultar/consultaTransferencia.xhtml", fin);
        Janela conRecebimento = new Janela(null, "Consulta Receber Valores", "/menu/financeiro/consultar/consultaRecebimento.xhtml", fin);
        Janela conPagamentos = new Janela(null, "Consulta Pagar Valores", "/menu/financeiro/consultar/consultaPagamento.xhtml", fin);

        daoJanela.adiciona(receberValores);
        daoJanela.adiciona(conRecebimento);
        daoJanela.adiciona(pagarValores);
        daoJanela.adiciona(conPagamentos);
        daoJanela.adiciona(extratoConta);
        daoJanela.adiciona(cotacoes);
        daoJanela.adiciona(baixa);
        daoJanela.adiciona(caixa);
        daoJanela.adiciona(formarecebimento);
        daoJanela.adiciona(jbanco);
        daoJanela.adiciona(moeda);
        daoJanela.adiciona(jconta);
        daoJanela.adiciona(transfLucro);
        daoJanela.adiciona(despProvisi);
        daoJanela.adiciona(receitProvisi);
        daoJanela.adiciona(cartao);
        daoJanela.adiciona(boletoDeCartao);
        daoJanela.adiciona(cheque);
        daoJanela.adiciona(consultaCobranca);
        daoJanela.adiciona(faturaLegada);
        daoJanela.adiciona(faturaEmitida);
        daoJanela.adiciona(faturaRecebida);
        daoJanela.adiciona(consultaDepositoBancario);
        daoJanela.adiciona(consultaLancamentoBancario);
        daoJanela.adiciona(consultaSaqueBancario);
        daoJanela.adiciona(consultaTransferencia);

        //Modulo Contábil
        Janela tipoReceita = new Janela(null, "Tipo Receita", "/menu/contabil/tipoReceita.xhtml", con);
        Janela tipoDespesa = new Janela(null, "Tipo Despesa", "/menu/contabil/tipoDespesa.xhtml", con);
        Janela grupoFinance = new Janela(null, "Grupo Financeiro", "/menu/contabil/grupoFinanceiro.xhtml", con);
        Janela jgrupoFiscal = new Janela(null, "Grupo Fiscal", "/menu/contabil/grupoFiscal.xhtml", con);
        Janela jcfop = new Janela(null, "Cfop", "/menu/contabil/cfop.xhtml", con);
        Janela operacoes = new Janela(null, "Operacoes", "/menu/contabil/operacoes.xhtml", con);
        Janela jiva = new Janela(null, "Tabela De Tributação", "/menu/contabil/tabelaDeTributacao.xhtml", con);

        daoJanela.adiciona(jiva);
        daoJanela.adiciona(tipoDespesa);
        daoJanela.adiciona(tipoReceita);
        daoJanela.adiciona(grupoFinance);
        daoJanela.adiciona(jgrupoFiscal);
        daoJanela.adiciona(jcfop);
        daoJanela.adiciona(operacoes);

        //Modulo de Cambio
        Janela recepcao = new Janela(null, "Recepção", "/menu/cambio/recepcao.xhtml", cam);
        Janela contraCambio = new Janela(null, "Contrato de Câmbio", "/menu/cambio/contratoDeCambio.xhtml", cam);
        Janela cambio = new Janela(null, "Câmbio", "/menu/cambio/cambio.xhtml", cam);

        daoJanela.adiciona(recepcao);
        daoJanela.adiciona(contraCambio);
        daoJanela.adiciona(cambio);

        //Modulo de Relatorios
        //Arquivo
        Janela relPessoas = new Janela(null, "Relatório de Pessoas", "/menu/arquivo/listar/relatorioDePessoas.xhtml", rela);

        //Vendas
        Janela relNotaEmitida = new Janela(null, "Relatório de Notas Emitidas", "/menu/vendas/listar/relatorioDeNotasEmitidas.xhtml", rela);
        Janela relCondicional = new Janela(null, "Relatório de Condicional", "/menu/vendas/listar/relatorioDeCondicional.xhtml", rela);
        Janela relComanda = new Janela(null, "Relatório de Comanda", "/menu/vendas/listar/relatorioDeComanda.xhtml", rela);
        Janela relOrcamento = new Janela(null, "Relatório de Orçamento", "/menu/vendas/listar/relatorioDeOrcamento.xhtml", rela);
        Janela relItemEmitido = new Janela(null, "Relatório de Item Emitido", "/menu/vendas/listar/relatorioDeItemEmitido.xhtml", rela);
        Janela relItemOrcado = new Janela(null, "Relatório de Item Orçado", "/menu/vendas/listar/relatorioDeItemOrcado.xhtml", rela);
        Janela relItemCondicional = new Janela(null, "Relatório de Item de Condicional", "/menu/vendas/listar/relatorioDeItemCondicional.xhtml", rela);
        Janela relItemComanda = new Janela(null, "Relatório de Item de Comanda", "/menu/vendas/listar/relatorioDeItemComanda.xhtml", rela);

        //Estoque
        Janela relAjusteEstoque = new Janela(null, "Relatório de Ajuste de Estoque", "/menu/estoque/listar/relatorioDeAjusteDeEstoque.xhtml", rela);
        Janela relNotaRecebida = new Janela(null, "Relatório de Notas Recebidas", "/menu/estoque/listar/relatorioDeNotasRecebidas.xhtml", rela);
        Janela relItem = new Janela(null, "Relatório de Item", "/menu/estoque/listar/relatorioDeItem.xhtml", rela);
        Janela relItemRecebido = new Janela(null, "Relatório de Item Recebido", "/menu/vendas/listar/relatorioDeItemRecebido.xhtml", rela);
        Janela relEstoque = new Janela(null, "Relatório de Estoque", "/menu/estoque/listar/relatorioDeEstoque.xhtml", rela);
        Janela relConhecimentoDeFrete = new Janela(null, "Relatório de Conhecimento de Frete", "/menu/estoque/listar/relatorioDeConhecimentoDeFrete.xhtml", rela);

        //Financeiro
        Janela relConta = new Janela(null, "Relatório de Contas", "/menu/financeiro/listar/relatorioDeConta.xhtml", rela);
        Janela relContaFixa = new Janela(null, "Relatório de Contas Fixas", "/menu/financeiro/listar/relatorioDeContaFixa.xhtml", rela);
        Janela relCheques = new Janela(null, "Relatório de Cheques", "/menu/financeiro/listar/relatorioDeCheques.xhtml", rela);
        Janela relDespPro = new Janela(null, "Relatório de Despesas Provisionadas", "/menu/financeiro/listar/relatorioDeDespesaProvisionada.xhtml", rela);
        Janela relReceitPro = new Janela(null, "Relatório de Receitas Provisionadas", "/menu/financeiro/listar/relatorioDeReceitaProvisionada.xhtml", rela);
        Janela relSaldo = new Janela(null, "Relatório de Saldo de Conta", "/menu/financeiro/listar/relatorioDeSaldoDeConta.xhtml", rela);
        Janela relSaldoDivisao = new Janela(null, "Relatório de Saldo de Divisão de Lucro", "/menu/financeiro/listar/relatorioDeSaldoDeDivisaoDeLucro.xhtml", rela);
        Janela relBaixa = new Janela(null, "Relatório de Baixas", "/menu/financeiro/listar/relatorioDeBaixa.xhtml", rela);

        //Cambio
        Janela relRecepcao = new Janela(null, "Relatório de Recepção", "/menu/cambio/listar/relatorioDeRecepcao.xhtml", rela);
        Janela relContrato = new Janela(null, "Relatório de Contrato de Câmbio", "/menu/cambio/listar/relatorioDeContratoDeCambio.xhtml", rela);
        Janela relCambio = new Janela(null, "Relatório de Câmbio", "/menu/cambio/listar/relatorioDeCambio.xhtml", rela);

        daoJanela.adiciona(relItemComanda);
        daoJanela.adiciona(relItemCondicional);
        daoJanela.adiciona(relItemOrcado);
        daoJanela.adiciona(relCondicional);
        daoJanela.adiciona(relComanda);
        daoJanela.adiciona(relOrcamento);
        daoJanela.adiciona(relCambio);
        daoJanela.adiciona(relPessoas);
        daoJanela.adiciona(relBaixa);
        daoJanela.adiciona(relConta);
        daoJanela.adiciona(relContaFixa);
        daoJanela.adiciona(relNotaEmitida);
        daoJanela.adiciona(relNotaRecebida);
        daoJanela.adiciona(relAjusteEstoque);
        daoJanela.adiciona(relCheques);
        daoJanela.adiciona(relDespPro);
        daoJanela.adiciona(relReceitPro);
        daoJanela.adiciona(relContrato);
        daoJanela.adiciona(relRecepcao);
        daoJanela.adiciona(relSaldo);
        daoJanela.adiciona(relSaldoDivisao);
        daoJanela.adiciona(relItem);
        daoJanela.adiciona(relItemEmitido);
        daoJanela.adiciona(relItemRecebido);
        daoJanela.adiciona(relEstoque);
        daoJanela.adiciona(relConhecimentoDeFrete);

        //Modulo Administrativo
        Janela usuario = new Janela(null, "Usuário", "/menu/topbar/preferencias/usuario.xhtml", admin);
        Janela jconfiguracao = new Janela(null, "Configuração", "/menu/topbar/preferencias/configuracao.xhtml", admin);
        Janela jgrupoPrivilegio = new Janela(null, "Grupo de Privilegios", "/menu/topbar/preferencias/grupoPrivilegio.xhtml", admin);
        Janela privilegio = new Janela(null, "Privilégio", "/menu/topbar/preferencias/privilegio.xhtml", admin);
        Janela jconfigNecessario = new Janela(null, "Configuracao Necessaria", "/configuracaoNecessaria.xhtml", admin);
        Janela filial = new Janela(null, "Filial", "/menu/topbar/preferencias/filial.xhtml", admin);

        daoJanela.adiciona(usuario);
        daoJanela.adiciona(jconfiguracao);
        daoJanela.adiciona(jgrupoPrivilegio);
        daoJanela.adiciona(privilegio);
        daoJanela.adiciona(jconfigNecessario);
        daoJanela.adiciona(filial);

        //Modulo de Preferencias
        Janela perfilUsuario = new Janela(null, "Perfil", "/menu/topbar/perfil/perfilUsuario.xhtml", pref);
        daoJanela.adiciona(perfilUsuario);

        //Modulo de Login
        Janela jlogin = new Janela(null, "Login", "/login.xhtml", login);
        daoJanela.adiciona(jlogin);

        // -- Adiciona Privilégios
        AdicionaDAOConsole<Privilegio> daoPrivilegio = new AdicionaDAOConsole<>();
        List<Privilegio> listaPrivilegios = Arrays.asList(
                new Privilegio(null, dashboard, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, jpessoa, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, cidade, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, jcep, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, jpessoaimport, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, notaSaida, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, retificacaoDeVenda, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, consultaNotaSaida, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, orcamento, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, consultaOrcamento, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, comanda, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, consultaComanda, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, condicional, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, consultaCondicional, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, jitem, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, notaRecebida, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, consultaNotaRecebida, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, retificacaoDeCompra, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, margem, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, conhecimentoFrete, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, ajusteEstoque, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, contaEstoque, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, unidadeMedida, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, jiva, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, marca, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, depositos, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, listaPreco, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, comissao, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, grupo, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, jgrupoFiscal, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, receberValores, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, conRecebimento, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, pagarValores, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, conPagamentos, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, extratoConta, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, cotacoes, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, baixa, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, caixa, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, formarecebimento, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, jbanco, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, moeda, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, jconta, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, tipoDespesa, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, tipoReceita, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, transfLucro, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, grupoFinance, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, despProvisi, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, receitProvisi, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, cartao, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, boletoDeCartao, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, cheque, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, consultaCobranca, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, faturaLegada, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, faturaEmitida, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, faturaRecebida, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, consultaDepositoBancario, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, consultaLancamentoBancario, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, consultaSaqueBancario, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, consultaTransferencia, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, recepcao, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, contraCambio, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, cambio, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relConta, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relContaFixa, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relBaixa, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relPessoas, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relCambio, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relNotaEmitida, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relNotaRecebida, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relAjusteEstoque, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relCheques, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relDespPro, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relReceitPro, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relContrato, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relRecepcao, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relSaldo, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relSaldoDivisao, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relItem, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relItemEmitido, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relItemRecebido, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relCondicional, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relOrcamento, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relComanda, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relItemComanda, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relItemCondicional, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relItemOrcado, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relEstoque, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relConhecimentoDeFrete, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, usuario, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, jconfiguracao, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, jconfigNecessario, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, filial, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, operacoes, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, jgrupoPrivilegio, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, privilegio, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, perfilUsuario, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, jestado, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, jpais, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, jlogin, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, jcfop, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, dashboardGestao, true, true, true, true, grupoDePrivilegio)
        );

        for (Privilegio p : listaPrivilegios) {
            daoPrivilegio.adiciona(p);
        }
        Usuario usercordeiro = new Usuario(null, cordeiro, "81dc9bdb52d04dc20036dbd8313ed055", grupoDePrivilegio, true, "steel", "blue", true, TipoCorMenu.CINZA, false);
        Usuario userrauber = new Usuario(null, rauber, "81dc9bdb52d04dc20036dbd8313ed055", grupoDePrivilegio, true, "steel", "blue", true, TipoCorMenu.CINZA, false);
        new AdicionaDAOConsole<Usuario>().adiciona(usercordeiro);
        new AdicionaDAOConsole<Usuario>().adiciona(userrauber);
        // -- Adiciona Grupo Financeiro
        AdicionaDAOConsole<GrupoFinanceiro> daoGrupoFinanceiro = new AdicionaDAOConsole<>();

        GrupoFinanceiro gfDAjuste = new GrupoFinanceiro(null, bundle.getLabel("Ajuste_Saldo_Inicial"), NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.OUTRAS);
        GrupoFinanceiro gfDDevVendas = new GrupoFinanceiro(null, "(-) " + bundle.getLabel("Devolucoes_de_Vendas"), NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.DEDUCOES_DE_RECEITA_BRUTA);
        GrupoFinanceiro gfDImpFaturamento = new GrupoFinanceiro(null, bundle.getLabel("Impostos_Sobre_Faturamento"), NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.DEDUCOES_DE_RECEITA_BRUTA);
        GrupoFinanceiro gfDCustoVariavel = new GrupoFinanceiro(null, bundle.getLabel("Total_Custos_Variaveis"), NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.CUSTOS_OPERACIONAIS);
        GrupoFinanceiro gfDVendas = new GrupoFinanceiro(null, bundle.getLabel("Despesas_Vendas"), NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.OPERACIONAIS);
        GrupoFinanceiro gfDPessoal = new GrupoFinanceiro(null, bundle.getLabel("Despesas_Pessoal"), NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.OPERACIONAIS);
        GrupoFinanceiro gfDAdm = new GrupoFinanceiro(null, bundle.getLabel("Despesas_Administrativas"), NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.OPERACIONAIS);
        GrupoFinanceiro gfDTributarias = new GrupoFinanceiro(null, bundle.getLabel("Despesas_Tributarias"), NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.OPERACIONAIS);
        GrupoFinanceiro gfDFinan = new GrupoFinanceiro(null, bundle.getLabel("Despesas_Financeiras"), NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.CUSTOS_OPERACIONAIS);
        GrupoFinanceiro gfDNaoOP = new GrupoFinanceiro(null, bundle.getLabel("Despesas_Nao_Operacionais"), NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.NAO_OPERACIONAIS);
        GrupoFinanceiro gfDEstoque = new GrupoFinanceiro(null, bundle.getLabel("Operacoes_Estoque"), NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.OUTRAS);
        GrupoFinanceiro gfDImobilizado = new GrupoFinanceiro(null, bundle.getLabel("Operacoes_Imobilizado"), NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.NAO_OPERACIONAIS);
        GrupoFinanceiro gfDOperacoes = new GrupoFinanceiro(null, bundle.getLabel("Outras_Operacoes"), NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.NAO_OPERACIONAIS);
        GrupoFinanceiro gfDAProducao = new GrupoFinanceiro(null, bundle.getLabel("Custos_Producao"), NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.CUSTOS_OPERACIONAIS);
        daoGrupoFinanceiro.adiciona(gfDAjuste);
        daoGrupoFinanceiro.adiciona(gfDDevVendas);
        daoGrupoFinanceiro.adiciona(gfDImpFaturamento);
        daoGrupoFinanceiro.adiciona(gfDCustoVariavel);
        daoGrupoFinanceiro.adiciona(gfDVendas);
        daoGrupoFinanceiro.adiciona(gfDPessoal);
        daoGrupoFinanceiro.adiciona(gfDAdm);
        daoGrupoFinanceiro.adiciona(gfDTributarias);
        daoGrupoFinanceiro.adiciona(gfDFinan);
        daoGrupoFinanceiro.adiciona(gfDNaoOP);
        daoGrupoFinanceiro.adiciona(gfDEstoque);
        daoGrupoFinanceiro.adiciona(gfDImobilizado);
        daoGrupoFinanceiro.adiciona(gfDOperacoes);
        daoGrupoFinanceiro.adiciona(gfDAProducao);

        GrupoFinanceiro gfRAjuste = new GrupoFinanceiro(null, bundle.getLabel("Ajuste_Saldo_Inicial"), NaturezaFinanceira.RECEITA, ClassificacaoFinanceira.OUTRAS);
        GrupoFinanceiro gfRVendaMercad = new GrupoFinanceiro(null, bundle.getLabel("Receita_com_Vendas_de_Mercadorias"), NaturezaFinanceira.RECEITA, ClassificacaoFinanceira.OPERACIONAIS);
        GrupoFinanceiro gfRPrestServicos = new GrupoFinanceiro(null, bundle.getLabel("Receitas_com_Prestacao_de_Servicos"), NaturezaFinanceira.RECEITA, ClassificacaoFinanceira.OPERACIONAIS);
        GrupoFinanceiro gfRDevoVendas = new GrupoFinanceiro(null, "(-) " + bundle.getLabel("Devolucoes_de_Vendas"), NaturezaFinanceira.RECEITA, ClassificacaoFinanceira.DEDUCOES_DE_RECEITA_BRUTA);
        GrupoFinanceiro gfRFinan = new GrupoFinanceiro(null, bundle.getLabel("Receitas_Financeiras"), NaturezaFinanceira.RECEITA, ClassificacaoFinanceira.FINANCEIRAS);
        GrupoFinanceiro gfRDiversas = new GrupoFinanceiro(null, bundle.getLabel("Receitas_Diversas"), NaturezaFinanceira.RECEITA, ClassificacaoFinanceira.OUTRAS_OPERACIONAIS);
        GrupoFinanceiro gfRNaoOP = new GrupoFinanceiro(null, bundle.getLabel("Receitas_nao_Operacionais"), NaturezaFinanceira.RECEITA, ClassificacaoFinanceira.NAO_OPERACIONAIS);
        GrupoFinanceiro gfREstoque = new GrupoFinanceiro(null, bundle.getLabel("Operacoes_com_Estoque"), NaturezaFinanceira.RECEITA, ClassificacaoFinanceira.OUTRAS);
        GrupoFinanceiro gfRImobilizado = new GrupoFinanceiro(null, bundle.getLabel("Operacoes_com_Imobilizado"), NaturezaFinanceira.RECEITA, ClassificacaoFinanceira.OUTRAS);
        GrupoFinanceiro gfROutrasOP = new GrupoFinanceiro(null, bundle.getLabel("Outras_Operacoes"), NaturezaFinanceira.RECEITA, ClassificacaoFinanceira.OUTRAS);
        daoGrupoFinanceiro.adiciona(gfRAjuste);
        daoGrupoFinanceiro.adiciona(gfRVendaMercad);
        daoGrupoFinanceiro.adiciona(gfRPrestServicos);
        daoGrupoFinanceiro.adiciona(gfRDevoVendas);
        daoGrupoFinanceiro.adiciona(gfRFinan);
        daoGrupoFinanceiro.adiciona(gfRDiversas);
        daoGrupoFinanceiro.adiciona(gfRNaoOP);
        daoGrupoFinanceiro.adiciona(gfREstoque);
        daoGrupoFinanceiro.adiciona(gfRImobilizado);
        daoGrupoFinanceiro.adiciona(gfROutrasOP);

        // -- Adiciona TipoDespesa
        AdicionaDAOConsole<TipoDespesa> daoDespesa = new AdicionaDAOConsole<>();

        TipoDespesa descontosConcedidos = new TipoDespesa(null, bundle.getLabel("Descontos_Concedidos"), gfDCustoVariavel);
        TipoDespesa jurosPagos = new TipoDespesa(null, bundle.getLabel("Juros_Pagos"), gfDCustoVariavel);
        TipoDespesa multasPagas = new TipoDespesa(null, bundle.getLabel("Multas_Pagas"), gfDFinan);
        TipoDespesa despesaCambial = new TipoDespesa(null, bundle.getLabel("Despesa_Variacao_Cambial"), gfDCustoVariavel);
        TipoDespesa comprasNormais = new TipoDespesa(null, bundle.getLabel("Compras_Normais"), gfDEstoque);
        TipoDespesa outrasD = new TipoDespesa(null, bundle.getLabel("Outras"), gfDEstoque);
        TipoDespesa custoDaMercadoriaVendida = new TipoDespesa(null, bundle.getLabel("Custo_Mercadoria_Vendida"), gfDCustoVariavel);
        daoDespesa.adiciona(descontosConcedidos);
        daoDespesa.adiciona(jurosPagos);
        daoDespesa.adiciona(multasPagas);
        daoDespesa.adiciona(despesaCambial);
        daoDespesa.adiciona(comprasNormais);
        daoDespesa.adiciona(custoDaMercadoriaVendida);
        daoDespesa.adiciona(outrasD);
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Ajuste_Saldo_Inicial"), gfDAjuste));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("PIS_Sobre_Faturamento"), gfDImpFaturamento));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("COFINS_Sobre_Faturamento"), gfDImpFaturamento));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("ICMS_Sobre_Mercadorias"), gfDImpFaturamento));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("ISS_Sobre_Servicos"), gfDImpFaturamento));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("DARF_Simples_Sobre_Faturamento"), gfDTributarias));
        daoDespesa.adiciona(new TipoDespesa(null, "(-) " + bundle.getLabel("Devolucoes_Vendas"), gfDDevVendas));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Custo_Servicos_Prestados"), gfDCustoVariavel));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Comissoes_Sobre_Venda"), gfDCustoVariavel));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Propaganda_Publicidade"), gfDVendas));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Doacoes_Brindes_Bonificacoes"), gfDVendas));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Desp_Frete_Entregas"), gfDVendas));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Salarios"), gfDPessoal));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Outras_Comissoes"), gfDPessoal));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Gratificacoes"), gfDPessoal));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Estagiarios"), gfDPessoal));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("13_Salario"), gfDPessoal));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Ferias"), gfDPessoal));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Vale_Transporte"), gfDPessoal));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Assistencia_Empregados"), gfDPessoal));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Aviso_Previo_Indenizacao_Trabalhista"), gfDPessoal));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Treinamentos"), gfDPessoal));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("FGTS"), gfDPessoal));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("INSS"), gfDPessoal));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Pro_labore"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Agua"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Telefone"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Energia_Eletrica"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Aluguel_Comercial"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Aluguel_Sistemas"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Honorarios_Profissionais"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Combustiveis_Lubrificantes"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Contribuicoes_Associacoes_Entidades"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Contribuicoes_Sindicais"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Taxas_Diversas"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Pedagio"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Cartorio"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Correios"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Dispendio_Alimentacao"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Feiras_Congressos_Cursos"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Passagens_Viagens_Estadias"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Fotocopias_Impressoes"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Fretes_Compras"), gfDCustoVariavel));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("IOF_Imp_Oper_Financeira"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Depreciacoes"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Manutencao_Conservacao_Predio"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Manutencao_Veiculos"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Material_Expediente_Limpeza_Consumo"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Servicos_Tercerizado_Pessoa_Fisica"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Confraternizacoes"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Outras_Administrativas"), gfDAdm));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Medicina_Trabalho"), gfDPessoal));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("IRPJ"), gfDTributarias));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Contribuicao_Social_Lucro_Liquido"), gfDTributarias));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Outros_Impostos_Estaduais"), gfDTributarias));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Outros_Impostos_Municipais"), gfDTributarias));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Impostos_Taxas_Diversas"), gfDTributarias));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Outras_Financeiras"), gfDFinan));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Tarifas_Bancarias"), gfDCustoVariavel));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Correcao_Monetaria_Paga"), gfDFinan));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Encargos_Moras_Fiscais"), gfDFinan));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Despesas_Cobrancas"), gfDCustoVariavel));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Taxa_Adm_Cartao_Credito"), gfDCustoVariavel));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Despesas_Indedutiveis"), gfDNaoOP));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Infracoes_Fiscais"), gfDNaoOP));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Prejuizos_Venda_Imobilizado"), gfDNaoOP));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Sinistros"), gfDNaoOP));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Perdas_Estoque"), gfDNaoOP));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Outras_Operacionais"), gfDNaoOP));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Compras_Consignacao"), gfDEstoque));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Compras_Ativo_Imobilizado"), gfDImobilizado));
        daoDespesa.adiciona(new TipoDespesa(null, bundle.getLabel("Compras_Recebimento_Futuro"), gfDEstoque));

        // -- Adiciona Receitas
        AdicionaDAOConsole<TipoReceita> daoReceita = new AdicionaDAOConsole<>();

        TipoReceita descontosRecebidos = new TipoReceita(null, bundle.getLabel("Descontos_Recebidos"), gfRFinan);
        TipoReceita jurosRecebidos = new TipoReceita(null, bundle.getLabel("Juros_Recebidos"), gfRFinan);
        TipoReceita multasRecebidas = new TipoReceita(null, bundle.getLabel("Multas_Recebidas"), gfRFinan);
        TipoReceita receitaCambial = new TipoReceita(null, bundle.getLabel("Receita_Variacao_Cambial"), gfRFinan);
        TipoReceita receitaFrete = new TipoReceita(null, bundle.getLabel("Receita_Frete"), gfRDiversas);
        TipoReceita outras = new TipoReceita(null, bundle.getLabel("Outras"), gfROutrasOP);
        TipoReceita vendaAVista = new TipoReceita(null, bundle.getLabel("Venda_AVista"), gfRVendaMercad);
        TipoReceita servicoAVista = new TipoReceita(null, bundle.getLabel("Servico_AVista"), gfRPrestServicos);
        TipoReceita vendaAPrazo = new TipoReceita(null, bundle.getLabel("Venda_APrazo"), gfRVendaMercad);
        TipoReceita servicoAPrazo = new TipoReceita(null, bundle.getLabel("Servico_APrazo"), gfRPrestServicos);
        daoReceita.adiciona(new TipoReceita(null, bundle.getLabel("Receitas_Aplicacoes_Financeiras"), gfRFinan));
        daoReceita.adiciona(new TipoReceita(null, bundle.getLabel("Bonificacoes_Brindes_Recebidos"), gfRDiversas));
        daoReceita.adiciona(new TipoReceita(null, bundle.getLabel("Reembolso_Sinistros"), gfRNaoOP));
        daoReceita.adiciona(new TipoReceita(null, bundle.getLabel("Agio_Venda_Imobilizado"), gfRNaoOP));
        daoReceita.adiciona(new TipoReceita(null, bundle.getLabel("Venda_Imobilizado"), gfRImobilizado));
        daoReceita.adiciona(new TipoReceita(null, bundle.getLabel("Ajuste_Saldo_Inicial"), gfDAjuste));

        daoReceita.adiciona(descontosRecebidos);
        daoReceita.adiciona(jurosRecebidos);
        daoReceita.adiciona(multasRecebidas);
        daoReceita.adiciona(receitaCambial);
        daoReceita.adiciona(outras);
        daoReceita.adiciona(receitaFrete);
        daoReceita.adiciona(vendaAVista);
        daoReceita.adiciona(vendaAPrazo);
        daoReceita.adiciona(servicoAVista);
        daoReceita.adiciona(servicoAPrazo);

        // -- Adiciona Bancos
        AdicionaDAOConsole<Banco> daoBanco = new AdicionaDAOConsole<>();

        daoBanco.adiciona(new Banco(null, "246", "Banco ABC Brasil S.A.", "www.abcbrasil.com.br"));
        daoBanco.adiciona(new Banco(null, "75", "Banco ABN AMRO S.A.", "www.bancocr2.com.br"));
        daoBanco.adiciona(new Banco(null, "25", "Banco Alfa S.A.", "www.bancoalfa.com.br"));
        daoBanco.adiciona(new Banco(null, "641", "Banco Alvorada S.A.", null));
        daoBanco.adiciona(new Banco(null, "65", "Banco Andbank (Brasil) S.A.", "www.bancobracce.com.br"));
        daoBanco.adiciona(new Banco(null, "24", "Banco BANDEPE S.A.", "www.bandepe.com.br"));
        daoBanco.adiciona(new Banco(null, "0", "Banco Bankpar S.A.", "www.aexp.com"));
        daoBanco.adiciona(new Banco(null, "740", "Banco Barclays S.A.", "www.barclays.com"));
        daoBanco.adiciona(new Banco(null, "107", "Banco BBM S.A.", "www.bbmbank.com.br"));
        daoBanco.adiciona(new Banco(null, "31", "Banco Beg S.A.", "www.itau.com.br"));
        daoBanco.adiciona(new Banco(null, "96", "Banco BM&FBOVESPA de Serviços de Liquidação e Custódia S.A", "www.bmf.com.br"));
        daoBanco.adiciona(new Banco(null, "318", "Banco BMG S.A.", "www.bancobmg.com.br"));
        daoBanco.adiciona(new Banco(null, "752", "Banco BNP Paribas Brasil S.A.", "www.bnpparibas.com.br"));
        daoBanco.adiciona(new Banco(null, "248", "Banco Boavista Interatlântico S.A.", null));
        daoBanco.adiciona(new Banco(null, "218", "Banco Bonsucesso S.A.", "www.bancobonsucesso.com.br"));
        daoBanco.adiciona(new Banco(null, "63", "Banco Bradescard S.A.", "www.ibi.com.br"));
        daoBanco.adiciona(new Banco(null, "36", "Banco Bradesco BBI S.A.", null));
        daoBanco.adiciona(new Banco(null, "204", "Banco Bradesco Cartões S.A.", "www.iamex.com.br"));
        daoBanco.adiciona(new Banco(null, "394", "Banco Bradesco Financiamentos S.A.", "www.bmc.com.br"));
        daoBanco.adiciona(new Banco(null, "237", "Banco Bradesco S.A.", "www.bradesco.com.br"));
        daoBanco.adiciona(new Banco(null, "208", "Banco BTG Pactual S.A.", "www.pactual.com.br"));
        daoBanco.adiciona(new Banco(null, "263", "Banco Cacique S.A.", "www.bancocacique.com.br"));
        daoBanco.adiciona(new Banco(null, "473", "Banco Caixa Geral - Brasil S.A.", "www.bcgbrasil.com.br"));
        daoBanco.adiciona(new Banco(null, "40", "Banco Cargill S.A.", "www.bancocargill.com.br"));
        daoBanco.adiciona(new Banco(null, null, "Banco Caterpillar S.A.", "www.catfinancial.com.br"));
        daoBanco.adiciona(new Banco(null, "739", "Banco Cetelem S.A.", "www.cetelem.com.br"));
        daoBanco.adiciona(new Banco(null, "233", "Banco Cifra S.A.", "www.bancocifra.com.br"));
        daoBanco.adiciona(new Banco(null, "745", "Banco Citibank S.A.", "www.citibank.com.br"));
        daoBanco.adiciona(new Banco(null, "0", "Banco CNH Industrial Capital S.A.", "www.bancocnh.com.br"));
        daoBanco.adiciona(new Banco(null, "215", "Banco Comercial e de Investimento Sudameris S.A.", "www.sudameris.com.br"));
        daoBanco.adiciona(new Banco(null, "95", "Banco Confidence de Câmbio S.A.", "www.bancoconfidence.com.br"));
        daoBanco.adiciona(new Banco(null, "756", "Banco Cooperativo do Brasil S.A. - BANCOOB", "www.bancoob.com.br"));
        daoBanco.adiciona(new Banco(null, "748", "Banco Cooperativo Sicredi S.A.", "www.sicredi.com.br"));
        daoBanco.adiciona(new Banco(null, "0", "Banco Credicard S.A.", "www.credicardbanco.com.br"));
        daoBanco.adiciona(new Banco(null, "222", "Banco Credit Agricole Brasil S.A.", "www.calyon.com.br"));
        daoBanco.adiciona(new Banco(null, "505", "Banco Credit Suisse (Brasil) S.A.", "www.csfb.com"));
        daoBanco.adiciona(new Banco(null, null, "Banco CSF S.A.", null));
        daoBanco.adiciona(new Banco(null, "3", "Banco da Amazônia S.A.", "www.bancoamazonia.com.br"));
        daoBanco.adiciona(new Banco(null, "083-3", "Banco da China Brasil S.A.", null));
        daoBanco.adiciona(new Banco(null, "707", "Banco Daycoval S.A.", "www.daycoval.com.br"));
        daoBanco.adiciona(new Banco(null, "0", "Banco de Lage Landen Brasil S.A.", "www.delagelanden.com"));
        daoBanco.adiciona(new Banco(null, "456", "Banco de Tokyo-Mitsubishi UFJ Brasil S.A.", "www.btm.com.br"));
        daoBanco.adiciona(new Banco(null, "1", "Banco do Brasil S.A.", "www.bb.com.br"));
        daoBanco.adiciona(new Banco(null, "47", "Banco do Estado de Sergipe S.A.", "www.banese.com.br"));
        daoBanco.adiciona(new Banco(null, "37", "Banco do Estado do Pará S.A.", "www.banparanet.com.br"));
        daoBanco.adiciona(new Banco(null, "41", "Banco do Estado do Rio Grande do Sul S.A.", "www.banrisul.com.br"));
        daoBanco.adiciona(new Banco(null, "4", "Banco do Nordeste do Brasil S.A.", "www.banconordeste.gov.br"));
        daoBanco.adiciona(new Banco(null, "265", "Banco Fator S.A.", "www.bancofator.com.br"));
        daoBanco.adiciona(new Banco(null, "224", "Banco Fibra S.A.", "www.bancofibra.com.br"));
        daoBanco.adiciona(new Banco(null, "626", "Banco Ficsa S.A.", "www.ficsa.com.br"));
        daoBanco.adiciona(new Banco(null, null, "Banco Fidis S.A.", "www.bancofidis.com.br"));
        daoBanco.adiciona(new Banco(null, "0", "Banco Ford S.A.", "www.bancoford.com.br"));
        daoBanco.adiciona(new Banco(null, "0", "Banco GMAC S.A.", "www.bancogmac.com.br"));
        daoBanco.adiciona(new Banco(null, "612", "Banco Guanabara S.A.", "www.bancoguanabara.com.br"));
        daoBanco.adiciona(new Banco(null, "0", "Banco Honda S.A.", "www.bancohonda.com.br"));
        daoBanco.adiciona(new Banco(null, "0", "Banco IBM S.A.", "www.ibm.com/br/financing/"));
        daoBanco.adiciona(new Banco(null, "12", "Banco INBURSA de Investimentos S.A.", "www.bancoinbursa.com"));
        daoBanco.adiciona(new Banco(null, "604", "Banco Industrial do Brasil S.A.", "www.bancoindustrial.com.br"));
        daoBanco.adiciona(new Banco(null, "320", "Banco Industrial e Comercial S.A.", "www.bicbanco.com.br"));
        daoBanco.adiciona(new Banco(null, "653", "Banco Indusval S.A.", "www.bip.b.br"));
        daoBanco.adiciona(new Banco(null, "249", "Banco Investcred Unibanco S.A.", null));
        daoBanco.adiciona(new Banco(null, "184", "Banco Itaú BBA S.A.", "www.itaubba.com.br"));
        daoBanco.adiciona(new Banco(null, "29", "Banco Itaú BMG Consignado S.A.", "www.bancobmg.com.br"));
        daoBanco.adiciona(new Banco(null, "0", "Banco Itaú Veículos S.A.", "www.bancofiat.com.br"));
        daoBanco.adiciona(new Banco(null, "479", "Banco ItaúBank S.A", "www.itaubank.com.br"));
        daoBanco.adiciona(new Banco(null, null, "Banco Itaucard S.A.", null));
        daoBanco.adiciona(new Banco(null, "376", "Banco J. P. Morgan S.A.", "www.jpmorgan.com"));
        daoBanco.adiciona(new Banco(null, "74", "Banco J. Safra S.A.", "www.jsafra.com.br"));
        daoBanco.adiciona(new Banco(null, "217", "Banco John Deere S.A.", "www.johndeere.com.br"));
        daoBanco.adiciona(new Banco(null, "600", "Banco Luso Brasileiro S.A.", "www.lusobrasileiro.com.br"));
        daoBanco.adiciona(new Banco(null, "389", "Banco Mercantil do Brasil S.A.", "www.mercantil.com.br"));
        daoBanco.adiciona(new Banco(null, "370", "Banco Mizuho do Brasil S.A.", "www.mizuhobank.com/brazil/pt/"));
        daoBanco.adiciona(new Banco(null, "746", "Banco Modal S.A.", "www.bancomodal.com.br"));
        daoBanco.adiciona(new Banco(null, "45", "Banco Opportunity de Investimento S.A.", "www.opportunity.com.br"));
        daoBanco.adiciona(new Banco(null, "212", "Banco Original S.A.", "www.bancooriginal.com.br"));
        daoBanco.adiciona(new Banco(null, "623", "Banco PAN S.A.", "www.bancopan.com.br"));
        daoBanco.adiciona(new Banco(null, "611", "Banco Paulista S.A.", "www.bancopaulista.com.br"));
        daoBanco.adiciona(new Banco(null, "094-2", "Banco Petra S.A.", "www.bancopetra.com.br"));
        daoBanco.adiciona(new Banco(null, "643", "Banco Pine S.A.", "www.bancopine.com.br"));
        daoBanco.adiciona(new Banco(null, "0", "Banco PSA Finance Brasil S.A.", null));
        daoBanco.adiciona(new Banco(null, "747", "Banco Rabobank International Brasil S.A.", "www.rabobank.com.br"));
        daoBanco.adiciona(new Banco(null, "356", "Banco Real S.A.", "www.bancoreal.com.br"));
        daoBanco.adiciona(new Banco(null, "633", "Banco Rendimento S.A.", "www.rendimento.com.br"));
        daoBanco.adiciona(new Banco(null, "0", "Banco Rodobens S.A.", "www.rodobens.com.br"));
        daoBanco.adiciona(new Banco(null, "422", "Banco Safra S.A.", "www.safra.com.br"));
        daoBanco.adiciona(new Banco(null, "33", "Banco Santander (Brasil) S.A.", "www.santander.com.br"));
        daoBanco.adiciona(new Banco(null, "366", "Banco Société Générale Brasil S.A.", "www.sgbrasil.com.br"));
        daoBanco.adiciona(new Banco(null, "464", "Banco Sumitomo Mitsui Brasileiro S.A.", "não possue site"));
        daoBanco.adiciona(new Banco(null, "082-5", "Banco Topázio S.A.", "www.bancotopazio.com.br"));
        daoBanco.adiciona(new Banco(null, "0", "Banco Toyota do Brasil S.A.", "www.bancotoyota.com.br"));
        daoBanco.adiciona(new Banco(null, "634", "Banco Triângulo S.A.", "www.tribanco.com.br"));
        daoBanco.adiciona(new Banco(null, "0", "Banco Volkswagen S.A.", "www.bancovw.com.br"));
        daoBanco.adiciona(new Banco(null, "0", "Banco Volvo (Brasil) S.A.", null));
        daoBanco.adiciona(new Banco(null, "655", "Banco Votorantim S.A.", "www.bancovotorantim.com.br"));
        daoBanco.adiciona(new Banco(null, "610", "Banco VR S.A.", "www.vr.com.br"));
        daoBanco.adiciona(new Banco(null, "119", "Banco Western Union do Brasil S.A.", null));
        daoBanco.adiciona(new Banco(null, null, "Banco Yamaha Motor S.A.", "www.yamaha - motor.com.br"));
        daoBanco.adiciona(new Banco(null, "21", "BANESTES S.A. Banco do Estado do Espírito Santo", "www.banestes.com.br"));
        daoBanco.adiciona(new Banco(null, "719", "Banif-Banco Internacional do Funchal (Brasil)S.A.", "www.banif.com.br"));
        daoBanco.adiciona(new Banco(null, "755", "Bank of America Merrill Lynch Banco Múltiplo S.A.", "www.ml.com"));
        daoBanco.adiciona(new Banco(null, "73", "BB Banco Popular do Brasil S.A.", "www.bancopopulardobrasil.com.br"));
        daoBanco.adiciona(new Banco(null, "081-7", "BBN Banco Brasileiro de Negócios S.A.", "www.concordiabanco.com"));
        daoBanco.adiciona(new Banco(null, "250", "BCV - Banco de Crédito e Varejo S.A.", "www.bancobcv.com.br"));
        daoBanco.adiciona(new Banco(null, "78", "BES Investimento do Brasil S.A.-Banco de Investimento", "www.besinvestimento.com.br"));
        daoBanco.adiciona(new Banco(null, null, "BEXS Banco de Câmbio S.A.", "www.bexs.com.br"));
        daoBanco.adiciona(new Banco(null, null, "BNY Mellon Banco S.A.", "www.bnymellon.com.br"));
        daoBanco.adiciona(new Banco(null, "69", "BPN Brasil Banco Múltiplo S.A.", "www.bpnbrasil.com.br"));
        daoBanco.adiciona(new Banco(null, "125", "Brasil Plural S.A. - Banco Múltiplo", "www.brasilplural.com"));
        daoBanco.adiciona(new Banco(null, "70", "BRB - Banco de Brasília S.A.", "www.brb.com.br"));
        daoBanco.adiciona(new Banco(null, "104", "Caixa Econômica Federal", "www.caixa.gov.br"));
        daoBanco.adiciona(new Banco(null, "477", "Citibank N.A.", "www.citibank.com/brasil"));
        daoBanco.adiciona(new Banco(null, "487", "Deutsche Bank S.A. - Banco Alemão", "www.deutsche-bank.com.br"));
        daoBanco.adiciona(new Banco(null, "64", "Goldman Sachs do Brasil Banco Múltiplo S.A.", "www.goldmansachs.com"));
        daoBanco.adiciona(new Banco(null, "62", "Hipercard Banco Múltiplo S.A.", "www.hipercard.com.br"));
        daoBanco.adiciona(new Banco(null, "492", "ING Bank N.V.", "www.ing.com"));
        daoBanco.adiciona(new Banco(null, "652", "Itaú Unibanco Holding S.A.", "www.itau.com.br"));
        daoBanco.adiciona(new Banco(null, "341", "Itaú Unibanco S.A.", "www.itau.com.br"));
        daoBanco.adiciona(new Banco(null, "488", "JPMorgan Chase Bank", "www.jpmorganchase.com"));
        daoBanco.adiciona(new Banco(null, null, "MSB Bank S.A.Banco de Câmbio", "www.msbbank.com.br"));
        daoBanco.adiciona(new Banco(null, "254", "Paraná Banco S.A.", "www.paranabanco.com.br"));
        daoBanco.adiciona(new Banco(null, "751", "Scotiabank Brasil S.A. Banco Múltiplo", "www.br.scotiabank.com"));
        daoBanco.adiciona(new Banco(null, null, "Standard Chartered Bank (Brasil) S / A–Bco Invest.", "www.standardchartered.com"));
        daoBanco.adiciona(new Banco(null, null, "UBS Brasil Banco de Investimento S.A.", "WWW.UBS.COM"));

        Banco banco = new Banco(null, "399", "HSBC Bank Brasil S.A. - Banco Múltiplo", "www.hsbc.com.br");
        daoBanco.adiciona(banco);

        //Moeda
        // ---------------------------------------------------------------------
        Moeda real = new Moeda(null, "Real", "R$", "Reais", "Centavo", "Centavos", TipoBandeira.BRASIL);
        Moeda dolar = new Moeda(null, "Dólar", "$", "Dólares", "Cent", "Cents", TipoBandeira.ESTADOS_UNIDOS);
        Moeda guarani = new Moeda(null, "Guaraní", "Gs", "Guaraníes", "centimo", "centimos", TipoBandeira.PARAGUAI);

        AdicionaDAOConsole<Moeda> daoMoeda = new AdicionaDAOConsole<Moeda>();
        daoMoeda.adiciona(real);
        daoMoeda.adiciona(dolar);
        daoMoeda.adiciona(guarani);

        //Conta de Estoque
        // ---------------------------------------------------------------------
        AdicionaDAOConsole<ContaDeEstoque> contaDeEstoqueDao = new AdicionaDAOConsole<ContaDeEstoque>();
        ContaDeEstoque contaDaEmpresa = new ContaDeEstoqueBuilder().comNome(bundle.getLabel("Estoque_da_empresa")).construir();
        ContaDeEstoque contaEstoqueDaEmpresa = new ContaDeEstoqueBuilder().comNome(bundle.getLabel("Estoque_da_empresa_em_posse_da_empresa")).construir();
        ContaDeEstoque contaDaEmpresaEmPosseDeTerceiros = new ContaDeEstoqueBuilder().comNome(bundle.getLabel("Estoque_da_empresa_em_posse_de_terceiros")).construir();
        ContaDeEstoque contaDeTerceiros = new ContaDeEstoqueBuilder().comNome(bundle.getLabel("Estoque_de_terceiros_em_posse_da_empresa")).construir();

        contaDeEstoqueDao.adiciona(contaDaEmpresa);
        contaDeEstoqueDao.adiciona(contaEstoqueDaEmpresa);
        contaDeEstoqueDao.adiciona(contaDaEmpresaEmPosseDeTerceiros);
        contaDeEstoqueDao.adiciona(contaDeTerceiros);

        // Deposito
        // ---------------------------------------------------------------------
        Deposito deposito = new Deposito(null, "Depósito Exemplo");
        new AdicionaDAOConsole<>().adiciona(deposito);

        //Configuracao
        // ---------------------------------------------------------------------
        Configuracao configuracao = new Configuracao(null, null, dolar, TipoDeFormacaoDePreco.MARKUP, TipoDeCalculoDeCusto.ULTIMO_CUSTO);
        new AdicionaDAOConsole<>().adiciona(configuracao);

        //ConfiguracaoEstoque
        // ---------------------------------------------------------------------
        ConfiguracaoEstoque configuracaoEstoque = new ConfiguracaoEstoque(null, contaDaEmpresa, null, deposito);
        new AdicionaDAOConsole<>().adiciona(configuracaoEstoque);

        //ConfiguracaoContabil
        // ---------------------------------------------------------------------
        ConfiguracaoContabil configuracaoContabil = new ConfiguracaoContabil(null, jurosRecebidos, multasRecebidas, descontosRecebidos, receitaCambial, jurosPagos, multasPagas, descontosConcedidos, despesaCambial);
        new AdicionaDAOConsole<>().adiciona(configuracaoContabil);

        // Conta
        // ---------------------------------------------------------------------
        AdicionaDAOConsole<Conta> contaDao = new AdicionaDAOConsole<Conta>();
        Conta contaReal = new Conta(null, "C/C 1222", banco, real);
        Conta contaDolar = new Conta(null, "C/C 1224", banco, dolar);
        contaDao.adiciona(contaReal);
        contaDao.adiciona(contaDolar);

        //Cotacao
        // ---------------------------------------------------------------------
        Cotacao cotacao = new Cotacao(null, BigDecimal.ZERO, new Date(), contaDolar);
        new AdicionaDAOConsole<>().adiciona(cotacao);

        // Unidade De Medida Item
        // ---------------------------------------------------------------------
        UnidadeMedidaItem unidade = new UnidadeMedidaItem(null, bundle.getLabel("Unidade"), "UN", 0);
        new AdicionaDAOConsole<UnidadeMedidaItem>().adiciona(unidade);

        // TabelaDeTributacao
        // ---------------------------------------------------------------------
        TabelaDeTributacao tabelaDeTributação = new TabelaDeTributacao(null, new BigDecimal(10), "IVA 10%");
        new AdicionaDAOConsole<TabelaDeTributacao>().adiciona(tabelaDeTributação);

        // Grupo Fiscal
        // ---------------------------------------------------------------------
        GrupoFiscal grupoFiscal = new GrupoFiscal(null, "IVA 10%", tabelaDeTributação);
        new AdicionaDAOConsole<GrupoFiscal>().adiciona(grupoFiscal);

        // Item
        Item item = new Item(null, null, "Exemplo", null, TipoItem.MERCADORIA, null, null, true,
                grupoFiscal, unidade, null, null, null, null, null, null, null, DetalhamentoDeItem.NORMAL, null);
        new AdicionaDAOConsole<Item>().adiciona(item);

        System.out.println("Dados criados com sucesso.");

        //Operação
        //------------------------------------------------------------------------
        AdicionaDAOConsole<Operacao> operacaoDao = new AdicionaDAOConsole<>();

        Operacao compraNormal = new OperacaoBuilder()
                .comNome(bundle.getLabel("Compra_Normal"))
                .comOperacaoFinanceira(OperacaoFinanceira.SAIDA)
                .comTipoOperacao(TipoOperacao.COMPRA_NORMAL)
                .comTipoNota(TipoLancamento.RECEBIDA)
                .comTipoContabil(TipoContabil.NAO_CONTABILIZAR)
                .comCompraAVista(comprasNormais)
                .comCompraAPrazo(comprasNormais)
                .comDespesaCMV(custoDaMercadoriaVendida)
                .comReceitaFrete(outras)
                .comVendaAPrazo(outras)
                .comVendaAVista(outras)
                .comServicoAPrazo(outras)
                .comServicoAVista(outras)
                .construir();

        Operacao vendaDeMercadorias = new OperacaoBuilder()
                .comNome(bundle.getLabel("Venda_de_Mercadorias"))
                .comOperacaoFinanceira(OperacaoFinanceira.ENTRADA)
                .comTipoOperacao(TipoOperacao.VENDA)
                .comTipoNota(TipoLancamento.EMITIDA)
                .comTipoContabil(TipoContabil.DEBITAR)
                .comCompraAVista(outrasD)
                .comCompraAPrazo(outrasD)
                .comDespesaCMV(custoDaMercadoriaVendida)
                .comReceitaFrete(receitaFrete)
                .comVendaAPrazo(vendaAPrazo)
                .comVendaAVista(vendaAVista)
                .comServicoAPrazo(servicoAPrazo)
                .comServicoAVista(servicoAVista)
                .construir();

        operacaoDao.adiciona(compraNormal);
        operacaoDao.adiciona(vendaDeMercadorias);

        //Cfop
        //------------------------------------------------------------------------
        AdicionaDAOConsole<Cfop> cfopDao = new AdicionaDAOConsole<>();

        Cfop cfop = new CfopBuilder()
                .comCfop(new Long(5102))
                .comNome("Venda de mercadoria adquirida ou recebida de terceiros")
                .comOperacaoFisica(OperacaoFisica.SAIDA)
                .comTexto("Classificam-se neste código as vendas de mercadorias adquiridas ou recebidas de "
                        + "terceiros para industrialização ou comercialização, que não tenham sido objeto de qualquer "
                        + "processo industrial no estabelecimento. Também serão classificadas neste código as vendas "
                        + "de mercadorias por estabelecimento comercial de cooperativa destinadas a seus cooperados "
                        + "ou estabelecimento de outra cooperativa.")
                .construir();

        cfopDao.adiciona(cfop);

        //SituacaoFiscal
        //------------------------------------------------------------------------
        AdicionaDAOConsole<SituacaoFiscal> situacaoFiscalDao = new AdicionaDAOConsole<>();
        SituacaoFiscal situacaoFiscal = new SituacaoFiscalBuilder()
                .comCFOP(cfop)
                .comTabelaDeTributacao(tabelaDeTributação)
                .comGrupoFiscal(grupoFiscal)
                .comOperacao(vendaDeMercadorias)
                .comSequencia(1)
                .construir();

        situacaoFiscalDao.adiciona(situacaoFiscal);

        //Modelo de Relatórios
        AdicionaDAOConsole<ModeloDeRelatorio> modeloDeRelatorioDAO = new AdicionaDAOConsole<ModeloDeRelatorio>();

        relatorioDeContasAPagar(bundle, modeloDeRelatorioDAO);
        relatorioDeContasAReceber(bundle, modeloDeRelatorioDAO);
        relatorioDeContasFixasAPagar(bundle, modeloDeRelatorioDAO);
        relatorioDeContasFixasAReceber(bundle, modeloDeRelatorioDAO);
        relatorioDeBalancoFisico(bundle);
        relatorioDeAniversariantes(bundle);
        relatorioDeVendasEmitidas(bundle);
        relatorioDeDevolucaoDeClientes(bundle);
        relatorioDeCompras(bundle);
        relatorioDeDevolucaoParaFornecedores();
        relatorioDeVendaParaEntregaFutura();
        extratoDeDespesas();
        extratoDeReceitas();
        relatorioDeMercadoriasVendidas();
        relatorioDeChequesRecebidosAbertos();
        relatorioDeChequesRecebidosDescontados();
        relatorioDeChequesRecebidosDevolvidos();
        relatorioDeChequesRecebidosCancelados();
        relatorioDeChequesEmitidosAbertos();
        relatorioDeChequesEmitidosDescontados();
        relatorioDeChequesEmitidosDevolvidos();
        relatorioDeChequesEmitidosCancelados();

    }

    private static ModeloDeRelatorio relatorioDeContasAPagar(BundleUtil bundle, AdicionaDAOConsole<ModeloDeRelatorio> modeloDeRelatorioDAO) throws DadoInvalidoException {
        //Relatório de Contas a Pagar.
        //========================================================
        ModeloDeRelatorio relatorioDeContasAPagar = new ModeloDeRelatorioBuilder()
                .comNome(new BundleUtil().getLabel("Relatorio_De_Contas_A_Pagar"))
                .comTipoRelatorio(TipoRelatorio.COBRANCA)
                .construir();
        //Filtros
        Coluna colOperacaoFinanceira = new Coluna(bundle.getLabel("Operacao_Financeira"), "Cobranca", "operacaoFinanceira", CobrancaVariavel.class, OperacaoFinanceira.class);
        FiltroDeRelatorio filtroOF = new FiltroDeRelatorio(null, colOperacaoFinanceira, TipoDeBusca.IGUAL_A);
        filtroOF.add(OperacaoFinanceira.SAIDA);
        Coluna colSituacaoDeCobranca = new Coluna(bundle.getLabel("Situacao_de_Cobranca"), "Cobranca", "situacaoDeCobranca", CobrancaVariavel.class, SituacaoDeCobranca.class);
        FiltroDeRelatorio filtroSDC = new FiltroDeRelatorio(null, colSituacaoDeCobranca, TipoDeBusca.IGUAL_A);
        filtroSDC.add(SituacaoDeCobranca.ABERTO);
        relatorioDeContasAPagar.addFiltro(filtroOF);
        relatorioDeContasAPagar.addFiltro(filtroSDC);
        //Colunas Exibidas
        Coluna pessoa = new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Pessoa") + ")", bundle.getLabel("Pessoa"), "pessoa", "nome", Pessoa.class, String.class);
        pessoa.setTamanho(30);
        relatorioDeContasAPagar.addColunaExibida(new Coluna(bundle.getLabel("Id"), bundle.getLabel("Cobranca"), "id", CobrancaVariavel.class, Long.class));
        relatorioDeContasAPagar.addColunaExibida(pessoa);
        relatorioDeContasAPagar.addColunaExibida(new Coluna(bundle.getLabel("Emissao"), bundle.getLabel("Cobranca"), "emissao", CobrancaVariavel.class, Date.class));
        relatorioDeContasAPagar.addColunaExibida(new Coluna(bundle.getLabel("Vencimento"), bundle.getLabel("Cobranca"), "vencimento", CobrancaVariavel.class, Date.class));
        relatorioDeContasAPagar.addColunaExibida(new Coluna(bundle.getLabel("Valor"), bundle.getLabel("Cobranca"), "valor", CobrancaVariavel.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.SUM));
        modeloDeRelatorioDAO.adiciona(relatorioDeContasAPagar);

        return relatorioDeContasAPagar;
    }

    private static void relatorioDeContasAReceber(BundleUtil bundle, AdicionaDAOConsole<ModeloDeRelatorio> modeloDeRelatorioDAO) throws ConstraintViolationException, DadoInvalidoException {
        //Relatório de Contas a Receber
        //========================================================
        ModeloDeRelatorio relatorioDeContasAReceber = new ModeloDeRelatorioBuilder()
                .comNome(new BundleUtil().getLabel("Relatorio_De_Contas_A_Receber"))
                .comTipoRelatorio(TipoRelatorio.COBRANCA)
                .construir();

        //Filtros
        Coluna colOperacaoFinanceiracCAR = new Coluna(bundle.getLabel("Operacao_Financeira"), "Cobranca", "operacaoFinanceira", CobrancaVariavel.class, OperacaoFinanceira.class);
        FiltroDeRelatorio filtroOFCAR = new FiltroDeRelatorio(null, colOperacaoFinanceiracCAR, TipoDeBusca.IGUAL_A);
        filtroOFCAR.add(OperacaoFinanceira.ENTRADA);

        Coluna colSituacaoDeCobrancaCAR = new Coluna(bundle.getLabel("Situacao_de_Cobranca"), "Cobranca", "situacaoDeCobranca", CobrancaVariavel.class, SituacaoDeCobranca.class);
        FiltroDeRelatorio filtroSDCCAR = new FiltroDeRelatorio(null, colSituacaoDeCobrancaCAR, TipoDeBusca.IGUAL_A);
        filtroSDCCAR.add(SituacaoDeCobranca.ABERTO);

        relatorioDeContasAReceber.addFiltro(filtroOFCAR);
        relatorioDeContasAReceber.addFiltro(filtroSDCCAR);

        //Colunas Exibidas
        Coluna pessoaCAR = new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Pessoa") + ")", bundle.getLabel("Pessoa"), "pessoa", "nome", Pessoa.class, String.class);
        pessoaCAR.setTamanho(30);

        relatorioDeContasAReceber.addColunaExibida(new Coluna(bundle.getLabel("Id"), bundle.getLabel("Cobranca"), "id", CobrancaVariavel.class, Long.class));
        relatorioDeContasAReceber.addColunaExibida(pessoaCAR);
        relatorioDeContasAReceber.addColunaExibida(new Coluna(bundle.getLabel("Emissao"), bundle.getLabel("Cobranca"), "emissao", CobrancaVariavel.class, Date.class));
        relatorioDeContasAReceber.addColunaExibida(new Coluna(bundle.getLabel("Vencimento"), bundle.getLabel("Cobranca"), "vencimento", CobrancaVariavel.class, Date.class));
        relatorioDeContasAReceber.addColunaExibida(new Coluna(bundle.getLabel("Valor"), bundle.getLabel("Cobranca"), "valor", CobrancaVariavel.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.SUM));

        modeloDeRelatorioDAO.adiciona(relatorioDeContasAReceber);
    }

    private static void relatorioDeContasFixasAPagar(BundleUtil bundle, AdicionaDAOConsole<ModeloDeRelatorio> modeloDeRelatorioDAO) throws ConstraintViolationException, DadoInvalidoException {
        //Relatório de Contas Fixas a Pagar.
        //========================================================
        ModeloDeRelatorio relatorioDeContasFixasAPagar = new ModeloDeRelatorioBuilder()
                .comNome(new BundleUtil().getLabel("Relatorio_De_Contas_Fixas_A_Pagar"))
                .comTipoRelatorio(TipoRelatorio.COBRANCA_FIXA)
                .construir();

        //Filtros
        Coluna colOperacaoFinanceiraCFP = new Coluna(bundle.getLabel("Operacao_Financeira"), "Cobranca", "operacaoFinanceira", CobrancaFixa.class, OperacaoFinanceira.class);
        FiltroDeRelatorio filtroOFCFP = new FiltroDeRelatorio(null, colOperacaoFinanceiraCFP, TipoDeBusca.IGUAL_A);
        filtroOFCFP.add(OperacaoFinanceira.SAIDA);

        Coluna colSituacaoDeCobrancaCFP = new Coluna(bundle.getLabel("Situacao_de_Cobranca"), "Cobranca", "situacaoDeCobranca", CobrancaFixa.class, SituacaoDeCobranca.class);
        FiltroDeRelatorio filtroSDCCFP = new FiltroDeRelatorio(null, colSituacaoDeCobrancaCFP, TipoDeBusca.IGUAL_A);
        filtroSDCCFP.add(SituacaoDeCobranca.ABERTO);

        relatorioDeContasFixasAPagar.addFiltro(filtroOFCFP);
        relatorioDeContasFixasAPagar.addFiltro(filtroSDCCFP);

        //Colunas Exibidas
        Coluna cfpPessoa = new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Pessoa") + ")", bundle.getLabel("Pessoa"), "pessoa", "nome", Pessoa.class, String.class);
        cfpPessoa.setTamanho(30);

        relatorioDeContasFixasAPagar.addColunaExibida(new Coluna(bundle.getLabel("Id"), bundle.getLabel("Cobranca"), "id", CobrancaFixa.class, Long.class));
        relatorioDeContasFixasAPagar.addColunaExibida(cfpPessoa);
        relatorioDeContasFixasAPagar.addColunaExibida(new Coluna(bundle.getLabel("Emissao"), bundle.getLabel("Cobranca"), "emissao", CobrancaFixa.class, Date.class));
        relatorioDeContasFixasAPagar.addColunaExibida(new Coluna(bundle.getLabel("Vencimento"), bundle.getLabel("Cobranca"), "vencimento", CobrancaFixa.class, Date.class));
        relatorioDeContasFixasAPagar.addColunaExibida(new Coluna(bundle.getLabel("Valor"), bundle.getLabel("Cobranca"), "valor", CobrancaFixa.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.SUM));

        modeloDeRelatorioDAO.adiciona(relatorioDeContasFixasAPagar);
    }

    private static void relatorioDeContasFixasAReceber(BundleUtil bundle, AdicionaDAOConsole<ModeloDeRelatorio> modeloDeRelatorioDAO) throws DadoInvalidoException, ConstraintViolationException {
        //Relatório de Contas Fixas a Receber
        //========================================================
        ModeloDeRelatorio relatorioDeContasFixasAReceber = new ModeloDeRelatorioBuilder()
                .comNome(new BundleUtil().getLabel("Relatorio_De_Contas_Fixas_A_Receber"))
                .comTipoRelatorio(TipoRelatorio.COBRANCA_FIXA)
                .construir();

        //Filtros
        Coluna colOperacaoFinanceiracCFR = new Coluna(bundle.getLabel("Operacao_Financeira"), "Cobranca", "operacaoFinanceira", CobrancaFixa.class, OperacaoFinanceira.class);
        FiltroDeRelatorio filtroOFCFR = new FiltroDeRelatorio(null, colOperacaoFinanceiracCFR, TipoDeBusca.IGUAL_A);
        filtroOFCFR.add(OperacaoFinanceira.ENTRADA);

        Coluna colSituacaoDeCobrancaCFR = new Coluna(bundle.getLabel("Situacao_de_Cobranca"), "Cobranca", "situacaoDeCobranca", CobrancaFixa.class, SituacaoDeCobranca.class);
        FiltroDeRelatorio filtroSDCCFR = new FiltroDeRelatorio(null, colSituacaoDeCobrancaCFR, TipoDeBusca.IGUAL_A);
        filtroSDCCFR.add(SituacaoDeCobranca.ABERTO);

        relatorioDeContasFixasAReceber.addFiltro(filtroOFCFR);
        relatorioDeContasFixasAReceber.addFiltro(filtroSDCCFR);

        //Colunas Exibidas
        Coluna CFRPessoa = new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Pessoa") + ")", bundle.getLabel("Pessoa"), "pessoa", "nome", Pessoa.class, String.class);
        CFRPessoa.setTamanho(30);

        relatorioDeContasFixasAReceber.addColunaExibida(new Coluna(bundle.getLabel("Id"), bundle.getLabel("Cobranca"), "id", CobrancaFixa.class, Long.class));
        relatorioDeContasFixasAReceber.addColunaExibida(CFRPessoa);
        relatorioDeContasFixasAReceber.addColunaExibida(new Coluna(bundle.getLabel("Emissao"), bundle.getLabel("Cobranca"), "emissao", CobrancaFixa.class, Date.class));
        relatorioDeContasFixasAReceber.addColunaExibida(new Coluna(bundle.getLabel("Vencimento"), bundle.getLabel("Cobranca"), "vencimento", CobrancaFixa.class, Date.class));
        relatorioDeContasFixasAReceber.addColunaExibida(new Coluna(bundle.getLabel("Valor"), bundle.getLabel("Cobranca"), "valor", CobrancaFixa.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.SUM));

        modeloDeRelatorioDAO.adiciona(relatorioDeContasFixasAReceber);
    }

    private static void extratoDeDespesas() throws ConstraintViolationException, DadoInvalidoException {
        //Extrato de Despesas
        //Cria o Modelo
        BundleUtil bundle = new BundleUtil();
        ModeloDeRelatorio extratoDeDespesas = new ModeloDeRelatorioBuilder().comNome(bundle.getLabel("Extrato_de_Despesas")).comTipoRelatorio(TipoRelatorio.BAIXA).construir();

        //Cria os Filtros
        Coluna naturezaFinanceira = new Coluna(bundle.getLabel("NaturezaFinanceira"), "Baixa", "naturezaFinanceira", null, null, null, Baixa.class, NaturezaFinanceira.class, null, null, 20, null);
        FiltroDeRelatorio filtroNaturezaFinanceira = new FiltroDeRelatorio(null, naturezaFinanceira, TipoDeBusca.IGUAL_A);
        filtroNaturezaFinanceira.add(NaturezaFinanceira.DESPESA);
        filtroNaturezaFinanceira.add(NaturezaFinanceira.RECEITA);
        extratoDeDespesas.addFiltro(filtroNaturezaFinanceira);

        Coluna operacaoFinanceira = new Coluna(bundle.getLabel("OperacaoFinanceira"), "Baixa", "operacaoFinanceira", null, null, null, Baixa.class, OperacaoFinanceira.class, null, null, 20, null);
        FiltroDeRelatorio filtroOperacaoFinanceira = new FiltroDeRelatorio(null, operacaoFinanceira, TipoDeBusca.IGUAL_A);
        filtroOperacaoFinanceira.add(OperacaoFinanceira.SAIDA);
        extratoDeDespesas.addFiltro(filtroOperacaoFinanceira);

        Coluna estado = new Coluna(bundle.getLabel("Estado"), "Baixa", "estado", null, null, null, Baixa.class, EstadoDeBaixa.class, null, null, 20, null);
        FiltroDeRelatorio filtroEstado = new FiltroDeRelatorio(null, estado, TipoDeBusca.DIFERENTE_DE);
        filtroEstado.add(EstadoDeBaixa.CANCELADO);
        extratoDeDespesas.addFiltro(filtroEstado);

        //Cria as Colunas Exibidas
        Coluna id = new Coluna(bundle.getLabel("Id"), "Baixa", "id", null, null, null, Baixa.class, Long.class, null, null, 20, null);
        Coluna pessoaNome = new Coluna(bundle.getLabel("Nome"), "Pessoa", "pessoa", "nome", null, null, Pessoa.class, String.class, null, null, 30, null);
        Coluna emissao = new Coluna(bundle.getLabel("Emissao"), "Baixa", "emissao", null, null, null, Baixa.class, Date.class, null, null, 20, null);
        Coluna vencimento = new Coluna(bundle.getLabel("Vencimento"), "Baixa", "vencimento", null, null, null, Baixa.class, Date.class, null, null, 20, null);
        Coluna valor = new Coluna(bundle.getLabel("Valor"), "Baixa", "valor", null, null, null, Baixa.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.SUM, 20, null);

        //Adiciona colunas exibidas no modelo
        extratoDeDespesas.addColunaExibida(id);
        extratoDeDespesas.addColunaExibida(pessoaNome);
        extratoDeDespesas.addColunaExibida(emissao);
        extratoDeDespesas.addColunaExibida(vencimento);
        extratoDeDespesas.addColunaExibida(valor);

        //Adiciona no Banco
        new AdicionaDAOConsole<>().adiciona(extratoDeDespesas);
    }

    private static void extratoDeReceitas() throws ConstraintViolationException, DadoInvalidoException {
        //Extrato de Receitas
        //Cria o Modelo
        BundleUtil bundle = new BundleUtil();
        ModeloDeRelatorio extratoDeReceitas = new ModeloDeRelatorioBuilder().comNome(bundle.getLabel("Extrato_de_Receitas")).comTipoRelatorio(TipoRelatorio.BAIXA).construir();

        //Cria os Filtros
        Coluna operacaoFinanceira = new Coluna(bundle.getLabel("OperacaoFinanceira"), "Baixa", "operacaoFinanceira", null, null, null, Baixa.class, OperacaoFinanceira.class, null, null, 20, null);
        FiltroDeRelatorio filtroOperacaoFinanceira = new FiltroDeRelatorio(null, operacaoFinanceira, TipoDeBusca.IGUAL_A);
        filtroOperacaoFinanceira.add(OperacaoFinanceira.SAIDA);
        extratoDeReceitas.addFiltro(filtroOperacaoFinanceira);

        Coluna naturezaFinanceira = new Coluna(bundle.getLabel("NaturezaFinanceira"), "Baixa", "naturezaFinanceira", null, null, null, Baixa.class, NaturezaFinanceira.class, null, null, 20, null);
        FiltroDeRelatorio filtroNaturezaFinanceira = new FiltroDeRelatorio(null, naturezaFinanceira, TipoDeBusca.IGUAL_A);
        filtroNaturezaFinanceira.add(NaturezaFinanceira.RECEITA);
        extratoDeReceitas.addFiltro(filtroNaturezaFinanceira);

        Coluna estado = new Coluna(bundle.getLabel("Estado"), "Baixa", "estado", null, null, null, Baixa.class, EstadoDeBaixa.class, null, null, 20, null);
        FiltroDeRelatorio filtroEstado = new FiltroDeRelatorio(null, estado, TipoDeBusca.DIFERENTE_DE);
        filtroEstado.add(EstadoDeBaixa.CANCELADO);
        extratoDeReceitas.addFiltro(filtroEstado);

        //Cria as Colunas Exibidas
        Coluna id = new Coluna(bundle.getLabel("Id"), "Baixa", "id", null, null, null, Baixa.class, Long.class, null, null, 20, null);
        Coluna pessoaNome = new Coluna(bundle.getLabel("Nome"), "Pessoa", "pessoa", "nome", null, null, Pessoa.class, String.class, null, null, 30, null);
        Coluna emissao = new Coluna(bundle.getLabel("Emissao"), "Baixa", "emissao", null, null, null, Baixa.class, Date.class, null, null, 20, null);
        Coluna vencimento = new Coluna(bundle.getLabel("Vencimento"), "Baixa", "vencimento", null, null, null, Baixa.class, Date.class, null, null, 20, null);
        Coluna valor = new Coluna(bundle.getLabel("Valor"), "Baixa", "valor", null, null, null, Baixa.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.SUM, 20, null);

        //Adiciona colunas exibidas no modelo
        extratoDeReceitas.addColunaExibida(id);
        extratoDeReceitas.addColunaExibida(pessoaNome);
        extratoDeReceitas.addColunaExibida(emissao);
        extratoDeReceitas.addColunaExibida(vencimento);
        extratoDeReceitas.addColunaExibida(valor);

        //Adiciona no Banco
        new AdicionaDAOConsole<>().adiciona(extratoDeReceitas);
    }

    private static void relatorioDeVendaParaEntregaFutura() throws ConstraintViolationException, DadoInvalidoException {
        //Relatório de Venda para Entrega Futura
        //Cria o Modelo
        BundleUtil bundle = new BundleUtil();
        ModeloDeRelatorio relatorioDeVendaParaEntregaFutura = new ModeloDeRelatorioBuilder().comNome(bundle.getLabel("Relatorio_de_Venda_para_Entrega_Futura")).comTipoRelatorio(TipoRelatorio.NOTAS_EMITIDAS).construir();

        //Cria os Filtros
        Coluna operacaoTipoOperacao = new Coluna(bundle.getLabel("TipoOperacao"), "Operacao", "operacao", "tipoOperacao", null, null, Operacao.class, TipoOperacao.class, null, null, 20, null);
        FiltroDeRelatorio filtroOperacaoTipoOperacao = new FiltroDeRelatorio(null, operacaoTipoOperacao, TipoDeBusca.IGUAL_A);
        filtroOperacaoTipoOperacao.add(TipoOperacao.VENDA_ENTREGA_FUTURA);
        relatorioDeVendaParaEntregaFutura.addFiltro(filtroOperacaoTipoOperacao);

        Coluna estado = new Coluna(bundle.getLabel("Estado"), "Nota", "estado", null, null, null, Nota.class, EstadoDeNota.class, null, null, 20, null);
        FiltroDeRelatorio filtroEstado = new FiltroDeRelatorio(null, estado, TipoDeBusca.DIFERENTE_DE);
        filtroEstado.add(EstadoDeNota.CANCELADO);
        relatorioDeVendaParaEntregaFutura.addFiltro(filtroEstado);

        //Cria as Colunas Exibidas
        Coluna id = new Coluna(bundle.getLabel("Id"), "Nota", "id", null, null, null, Nota.class, Long.class, null, null, 20, null);
        Coluna operacaoNome = new Coluna(bundle.getLabel("Nome"), "Operacao", "operacao", "nome", null, null, Operacao.class, String.class, null, null, 20, null);
        Coluna pessoaNome = new Coluna(bundle.getLabel("Nome"), "Pessoa", "pessoa", "nome", null, null, Pessoa.class, String.class, null, null, 20, null);
        Coluna emissao = new Coluna(bundle.getLabel("Emissao"), "Nota", "emissao", null, null, null, Nota.class, Date.class, null, null, 20, null);
        Coluna totalNota = new Coluna(bundle.getLabel("TotalNota"), "Nota", "totalNota", null, null, null, Nota.class, BigDecimal.class, null, Totalizador.SUM, 20, null);

        //Adiciona colunas exibidas no modelo
        relatorioDeVendaParaEntregaFutura.addColunaExibida(id);
        relatorioDeVendaParaEntregaFutura.addColunaExibida(operacaoNome);
        relatorioDeVendaParaEntregaFutura.addColunaExibida(pessoaNome);
        relatorioDeVendaParaEntregaFutura.addColunaExibida(emissao);
        relatorioDeVendaParaEntregaFutura.addColunaExibida(totalNota);

        //Adiciona no Banco
        new AdicionaDAOConsole<>().adiciona(relatorioDeVendaParaEntregaFutura);
    }

    private static void relatorioDeDevolucaoParaFornecedores() throws ConstraintViolationException, DadoInvalidoException {
        //Relatório de Devolução para Fornecedores
        //Cria o Modelo
        BundleUtil bundle = new BundleUtil();
        ModeloDeRelatorio relatorioDeDevolucaoParaFornecedores = new ModeloDeRelatorioBuilder().comNome(bundle.getLabel("Relatorio_de_Devolucao_para_Fornecedores")).comTipoRelatorio(TipoRelatorio.NOTAS_EMITIDAS).construir();

        //Cria os Filtros
        Coluna operacaoTipoOperacao = new Coluna(bundle.getLabel("TipoOperacao"), "Operacao", "operacao", "tipoOperacao", null, null, Operacao.class, TipoOperacao.class, null, null, 20, null);
        FiltroDeRelatorio filtroOperacaoTipoOperacao = new FiltroDeRelatorio(null, operacaoTipoOperacao, TipoDeBusca.IGUAL_A);
        filtroOperacaoTipoOperacao.add(TipoOperacao.DEVOLUCAO_FORNECEDOR);
        relatorioDeDevolucaoParaFornecedores.addFiltro(filtroOperacaoTipoOperacao);

        Coluna estado = new Coluna(bundle.getLabel("Estado"), "Nota", "estado", null, null, null, Nota.class, EstadoDeNota.class, null, null, 20, null);
        FiltroDeRelatorio filtroEstado = new FiltroDeRelatorio(null, estado, TipoDeBusca.DIFERENTE_DE);
        filtroEstado.add(EstadoDeNota.CANCELADO);
        relatorioDeDevolucaoParaFornecedores.addFiltro(filtroEstado);

        //Cria as Colunas Exibidas
        Coluna id = new Coluna(bundle.getLabel("Id"), "Nota", "id", null, null, null, Nota.class, Long.class, null, null, 20, null);
        Coluna operacaoNome = new Coluna(bundle.getLabel("Nome"), "Operacao", "operacao", "nome", null, null, Operacao.class, String.class, null, null, 20, null);
        Coluna pessoaNome = new Coluna(bundle.getLabel("Nome"), "Pessoa", "pessoa", "nome", null, null, Pessoa.class, String.class, null, null, 20, null);
        Coluna emissao = new Coluna(bundle.getLabel("Emissao"), "Nota", "emissao", null, null, null, Nota.class, Date.class, null, null, 20, null);
        Coluna totalNota = new Coluna(bundle.getLabel("TotalNota"), "Nota", "totalNota", null, null, null, Nota.class, BigDecimal.class, null, Totalizador.SUM, 20, null);

        //Adiciona colunas exibidas no modelo
        relatorioDeDevolucaoParaFornecedores.addColunaExibida(id);
        relatorioDeDevolucaoParaFornecedores.addColunaExibida(operacaoNome);
        relatorioDeDevolucaoParaFornecedores.addColunaExibida(pessoaNome);
        relatorioDeDevolucaoParaFornecedores.addColunaExibida(emissao);
        relatorioDeDevolucaoParaFornecedores.addColunaExibida(totalNota);

        //Adiciona no Banco
        new AdicionaDAOConsole<>().adiciona(relatorioDeDevolucaoParaFornecedores);
    }

    private static void relatorioDeCompras(BundleUtil bundle) throws ConstraintViolationException, DadoInvalidoException {
        //Relatório de Compras
        //Cria o Modelo
        ModeloDeRelatorio relatorioDeCompras = new ModeloDeRelatorioBuilder().comNome(new BundleUtil().getLabel("Relatorio_de_Compras")).comTipoRelatorio(TipoRelatorio.NOTAS_RECEBIDAS).construir();

        //Cria os Filtros
        Coluna operacaoTipoOperacao = new Coluna(bundle.getLabel("TipoOperacao"), "Operacao", "operacao", "tipoOperacao", null, null, Operacao.class, TipoOperacao.class, null, null, 20, null);
        FiltroDeRelatorio filtroOperacaoTipoOperacao = new FiltroDeRelatorio(null, operacaoTipoOperacao, TipoDeBusca.IGUAL_A);
        filtroOperacaoTipoOperacao.add(TipoOperacao.COMPRA_NORMAL);
        filtroOperacaoTipoOperacao.add(TipoOperacao.COMPRA_IMOBILIZADO);
        relatorioDeCompras.addFiltro(filtroOperacaoTipoOperacao);

        Coluna estado = new Coluna(bundle.getLabel("Estado"), "Nota", "estado", null, null, null, Nota.class, EstadoDeNota.class, null, null, 20, null);
        FiltroDeRelatorio filtroEstado = new FiltroDeRelatorio(null, estado, TipoDeBusca.DIFERENTE_DE);
        filtroEstado.add(EstadoDeNota.CANCELADO);
        relatorioDeCompras.addFiltro(filtroEstado);

        //Cria as Colunas Exibidas
        Coluna id = new Coluna(bundle.getLabel("Id"), "Nota", "id", null, null, null, Nota.class, Long.class, null, null, 20, null);
        Coluna operacaoNome = new Coluna(bundle.getLabel("Nome"), "Operacao", "operacao", "nome", null, null, Operacao.class, String.class, null, null, 20, null);
        Coluna pessoaNome = new Coluna(bundle.getLabel("Nome"), "Pessoa", "pessoa", "nome", null, null, Pessoa.class, String.class, null, null, 20, null);
        Coluna emissao = new Coluna(bundle.getLabel("Emissao"), "Nota", "emissao", null, null, null, Nota.class, Date.class, null, null, 20, null);
        Coluna totalNota = new Coluna(bundle.getLabel("TotalNota"), "Nota", "totalNota", null, null, null, Nota.class, BigDecimal.class, null, Totalizador.SUM, 20, null);

        //Adiciona colunas exibidas no modelo
        relatorioDeCompras.addColunaExibida(id);
        relatorioDeCompras.addColunaExibida(operacaoNome);
        relatorioDeCompras.addColunaExibida(pessoaNome);
        relatorioDeCompras.addColunaExibida(emissao);
        relatorioDeCompras.addColunaExibida(totalNota);

        //Adiciona no Banco
        new AdicionaDAOConsole<>().adiciona(relatorioDeCompras);
    }

    private static void relatorioDeBalancoFisico(BundleUtil bundle) throws ConstraintViolationException, DadoInvalidoException {
        //Relatório de Balanço Físico
        //========================================================
        ModeloDeRelatorio relatorioDeItem = new ModeloDeRelatorioBuilder()
                .comNome(new BundleUtil().getLabel("Relatorio_de_Balanco_Fisico"))
                .comTipoRelatorio(TipoRelatorio.ITEM)
                .construir();

        //Colunas Exibidas
        String itemStr = bundle.getLabel("Item");
        Coluna itemId = new Coluna(bundle.getLabel("Id"), itemStr, "id", Item.class, Long.class);
        Coluna itemSaldo = new Coluna(bundle.getLabel("Saldo"), itemStr, "saldo", Item.class, BigDecimal.class);
        Coluna itemPreco = new Coluna(bundle.getLabel("Preco"), itemStr, "preco", Item.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.SUM);
        Coluna itemCustoMedio = new Coluna(bundle.getLabel("Custo_Medio"), itemStr, "custoMedio", Item.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.AVERAGE);
        Coluna itemNome = new Coluna(bundle.getLabel("Nome"), itemStr, "nome", Item.class, String.class);
        Coluna itemPrecoTotal = new Coluna(bundle.getLabel("Preco_Total"), itemStr, "precoTotal", Item.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.SUM);
        Coluna itemCustoTotal = new Coluna(bundle.getLabel("Custo_Total"), itemStr, "custoTotal", Item.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.SUM);

        //Alterar Tamanho das Colunas
        itemId.setTamanho(10);
        itemNome.setTamanho(25);
        itemSaldo.setTamanho(15);
        itemPreco.setTamanho(15);
        itemCustoMedio.setTamanho(15);
        itemPrecoTotal.setTamanho(15);
        itemCustoTotal.setTamanho(15);

        relatorioDeItem.addColunaExibida(itemId);
        relatorioDeItem.addColunaExibida(itemNome);
        relatorioDeItem.addColunaExibida(itemSaldo);
        relatorioDeItem.addColunaExibida(itemPreco);
        relatorioDeItem.addColunaExibida(itemPrecoTotal);
        relatorioDeItem.addColunaExibida(itemCustoMedio);
        relatorioDeItem.addColunaExibida(itemCustoTotal);

        new AdicionaDAOConsole<>().adiciona(relatorioDeItem);
    }

    private static void relatorioDeAniversariantes(BundleUtil bundle) throws DadoInvalidoException, ConstraintViolationException {
        //Relatório de Aniversariantes
        //========================================================
        ModeloDeRelatorio relatorioDeAniversariantes = new ModeloDeRelatorioBuilder()
                .comNome(new BundleUtil().getLabel("Relatorio_de_Aniversariantes"))
                .comTipoRelatorio(TipoRelatorio.PESSOAS)
                .construir();

        //Colunas Exibidas
        String pessoaStr = bundle.getLabel("Pessoa");
        Coluna pessoaId = new Coluna(bundle.getLabel("Id"), pessoaStr, "id", Pessoa.class, Long.class);
        Coluna pessoaNome = new Coluna(bundle.getLabel("Nome"), pessoaStr, "nome", Pessoa.class, String.class);
        Coluna pessoaNascimento = new Coluna(bundle.getLabel("Nascimento"), pessoaStr, "nascimento", Pessoa.class, Date.class);
        Coluna pessoaTelefone = new Coluna(bundle.getLabel("Telefone"), pessoaStr, "telefone", Pessoa.class, String.class);
        Coluna pessoaCidadeNome = new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Cidade") + ")", "Cidade", "cidade", "nome", Cidade.class, String.class);

        //Alterar Tamanho das Colunas
        pessoaNome.setTamanho(40);
        pessoaNascimento.setTamanho(15);

        relatorioDeAniversariantes.addColunaExibida(pessoaId);
        relatorioDeAniversariantes.addColunaExibida(pessoaNome);
        relatorioDeAniversariantes.addColunaExibida(pessoaNascimento);
        relatorioDeAniversariantes.addColunaExibida(pessoaTelefone);
        relatorioDeAniversariantes.addColunaExibida(pessoaCidadeNome);

        new AdicionaDAOConsole<>().adiciona(relatorioDeAniversariantes);
    }

    private static void relatorioDeVendasEmitidas(BundleUtil bundle) throws DadoInvalidoException, ConstraintViolationException {
        //Relatório de Notas Emitidas
        //Cria o Modelo
        ModeloDeRelatorio relatorioDeVendaDeMercadorias = new ModeloDeRelatorioBuilder().comNome(new BundleUtil().getLabel("Relatorio_de_Vendas")).comTipoRelatorio(TipoRelatorio.NOTAS_EMITIDAS).construir();

        //Cria os Filtros
        Coluna operacaoTipoOperacao = new Coluna(bundle.getLabel("TipoOperacao"), "Operacao", "operacao", "tipoOperacao", null, null, Operacao.class, TipoOperacao.class, null, null, 20, null);
        FiltroDeRelatorio filtroOperacaoTipoOperacao = new FiltroDeRelatorio(null, operacaoTipoOperacao, TipoDeBusca.IGUAL_A);
        filtroOperacaoTipoOperacao.add(TipoOperacao.VENDA);
        filtroOperacaoTipoOperacao.add(TipoOperacao.VENDA_ENTREGA_FUTURA);
        filtroOperacaoTipoOperacao.add(TipoOperacao.VENDA_IMOBILIZADO);
        relatorioDeVendaDeMercadorias.addFiltro(filtroOperacaoTipoOperacao);

        Coluna estadoDeNota = new Coluna(bundle.getLabel("Estado"), "Nota", "estado", null, null, null, Nota.class, EstadoDeNota.class, null, null, 20, null);
        FiltroDeRelatorio filtroEstado = new FiltroDeRelatorio(null, estadoDeNota, TipoDeBusca.DIFERENTE_DE);
        filtroEstado.add(EstadoDeNota.CANCELADO);
        relatorioDeVendaDeMercadorias.addFiltro(filtroEstado);

        //Cria as Colunas Exibidas
        Coluna id = new Coluna(bundle.getLabel("Id"), "Nota", "id", null, null, null, Nota.class, Long.class, null, null, 20, null);
        Coluna operacaoNome = new Coluna(bundle.getLabel("Nome"), "Operacao", "operacao", "nome", null, null, Operacao.class, String.class, null, null, 20, null);
        Coluna pessoaNomeNota = new Coluna(bundle.getLabel("Nome"), "Pessoa", "pessoa", "nome", null, null, Pessoa.class, String.class, null, null, 20, null);
        Coluna emissao = new Coluna(bundle.getLabel("Emissao"), "Nota", "emissao", null, null, null, Nota.class, Date.class, null, null, 20, null);
        Coluna totalNota = new Coluna(bundle.getLabel("TotalNota"), "Nota", "totalNota", null, null, null, Nota.class, BigDecimal.class, null, Totalizador.SUM, 20, null);

        //Adiciona colunas exibidas no modelo
        relatorioDeVendaDeMercadorias.addColunaExibida(id);
        relatorioDeVendaDeMercadorias.addColunaExibida(operacaoNome);
        relatorioDeVendaDeMercadorias.addColunaExibida(pessoaNomeNota);
        relatorioDeVendaDeMercadorias.addColunaExibida(emissao);
        relatorioDeVendaDeMercadorias.addColunaExibida(totalNota);

        //Adiciona no Banco
        new AdicionaDAOConsole<>().adiciona(relatorioDeVendaDeMercadorias);
    }

    public static void relatorioDeDevolucaoDeClientes(BundleUtil bundle) throws ConstraintViolationException, DadoInvalidoException {
        //Relatório de Notas de Devolução de Clientes
        //Cria o Modelo
        ModeloDeRelatorio relatorioDeNotasDeDevolucaoDeClientes = new ModeloDeRelatorioBuilder().comNome(new BundleUtil().getLabel("Relatorio_de_Devolucao_de_Clientes")).comTipoRelatorio(TipoRelatorio.NOTAS_EMITIDAS).construir();

        //Cria os Filtros
        Coluna operacaoTipoOperacao = new Coluna(bundle.getLabel("TipoOperacao"), "Operacao", "operacao", "tipoOperacao", null, null, Operacao.class, TipoOperacao.class, null, null, 20, null);
        FiltroDeRelatorio filtroOperacaoTipoOperacao = new FiltroDeRelatorio(null, operacaoTipoOperacao, TipoDeBusca.IGUAL_A);
        filtroOperacaoTipoOperacao.add(TipoOperacao.DEVOLUCAO_CLIENTE);
        relatorioDeNotasDeDevolucaoDeClientes.addFiltro(filtroOperacaoTipoOperacao);

        Coluna estado = new Coluna(bundle.getLabel("Estado"), "Nota", "estado", null, null, null, Nota.class, EstadoDeNota.class, null, null, 20, null);
        FiltroDeRelatorio filtroEstado = new FiltroDeRelatorio(null, estado, TipoDeBusca.DIFERENTE_DE);
        filtroEstado.add(EstadoDeNota.CANCELADO);
        relatorioDeNotasDeDevolucaoDeClientes.addFiltro(filtroEstado);

        //Cria as Colunas Exibidas
        Coluna id = new Coluna(bundle.getLabel("Id"), "Nota", "id", null, null, null, Nota.class, Long.class, null, null, 20, null);
        Coluna operacaoNome = new Coluna(bundle.getLabel("Nome"), "Operacao", "operacao", "nome", null, null, Operacao.class, String.class, null, null, 20, null);
        Coluna pessoaNome = new Coluna(bundle.getLabel("Nome"), "Pessoa", "pessoa", "nome", null, null, Pessoa.class, String.class, null, null, 20, null);
        Coluna emissao = new Coluna(bundle.getLabel("Emissao"), "Nota", "emissao", null, null, null, Nota.class, Date.class, null, null, 20, null);
        Coluna totalNota = new Coluna(bundle.getLabel("TotalNota"), "Nota", "totalNota", null, null, null, Nota.class, BigDecimal.class, null, Totalizador.SUM, 20, null);

        //Adiciona colunas exibidas no modelo
        relatorioDeNotasDeDevolucaoDeClientes.addColunaExibida(id);
        relatorioDeNotasDeDevolucaoDeClientes.addColunaExibida(operacaoNome);
        relatorioDeNotasDeDevolucaoDeClientes.addColunaExibida(pessoaNome);
        relatorioDeNotasDeDevolucaoDeClientes.addColunaExibida(emissao);
        relatorioDeNotasDeDevolucaoDeClientes.addColunaExibida(totalNota);

        //Adiciona no Banco
        new AdicionaDAOConsole<>().adiciona(relatorioDeNotasDeDevolucaoDeClientes);
    }

    private static void relatorioDeMercadoriasVendidas() throws ConstraintViolationException, DadoInvalidoException {
        //Relatório de Mercadorias Vendidas
        //Cria o Modelo
        BundleUtil bundle = new BundleUtil();
        ModeloDeRelatorio relatorioDeMercadoriasVendidas = new ModeloDeRelatorioBuilder().comNome(bundle.getLabel("Relatorio_de_Mercadorias_Vendidas")).comTipoRelatorio(TipoRelatorio.ITEM_EMITIDO).construir();

        //Cria os Filtros
        Coluna notaOperacaoTipoOperacao = new Coluna(bundle.getLabel("TipoOperacao"), "Operacao", "nota", "operacao", "tipoOperacao", null, Operacao.class, TipoOperacao.class, null, null, 20, null);
        FiltroDeRelatorio filtroNotaOperacaoTipoOperacao = new FiltroDeRelatorio(null, notaOperacaoTipoOperacao, TipoDeBusca.IGUAL_A);
        filtroNotaOperacaoTipoOperacao.add(TipoOperacao.VENDA);
        filtroNotaOperacaoTipoOperacao.add(TipoOperacao.VENDA_ENTREGA_FUTURA);
        filtroNotaOperacaoTipoOperacao.add(TipoOperacao.VENDA_IMOBILIZADO);
        relatorioDeMercadoriasVendidas.addFiltro(filtroNotaOperacaoTipoOperacao);

        Coluna notaEstado = new Coluna(bundle.getLabel("Estado"), "Nota", "nota", "estado", null, null, Nota.class, EstadoDeNota.class, null, null, 20, null);
        FiltroDeRelatorio filtroNotaEstado = new FiltroDeRelatorio(null, notaEstado, TipoDeBusca.DIFERENTE_DE);
        filtroNotaEstado.add(EstadoDeNota.CANCELADO);
        relatorioDeMercadoriasVendidas.addFiltro(filtroNotaEstado);

        //Cria as Colunas Exibidas
        Coluna id = new Coluna(bundle.getLabel("Id"), "Item", "id", null, null, null, Item.class, Long.class, null, null, 20, null);
        Coluna itemNome = new Coluna(bundle.getLabel("Nome"), "Item", "item", "nome", null, null, Item.class, String.class, null, null, 20, null);
        Coluna itemMarcaNome = new Coluna(bundle.getLabel("Nome"), "Marca", "item", "marca", "nome", null, Marca.class, String.class, null, null, 20, null);
        Coluna quantidade = new Coluna(bundle.getLabel("Quantidade"), "ItemDeNota", "quantidade", null, null, null, ItemDeNota.class, BigDecimal.class, null, null, 20, null);
        Coluna unitario = new Coluna(bundle.getLabel("Unitario"), "ItemDeNota", "unitario", null, null, null, ItemDeNota.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, null, 20, null);
        Coluna total = new Coluna(bundle.getLabel("Total"), "ItemDeNota", "total", null, null, null, ItemDeNota.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.SUM, 20, null);

        //Adiciona colunas exibidas no modelo
        relatorioDeMercadoriasVendidas.addColunaExibida(id);
        relatorioDeMercadoriasVendidas.addColunaExibida(itemNome);
        relatorioDeMercadoriasVendidas.addColunaExibida(itemMarcaNome);
        relatorioDeMercadoriasVendidas.addColunaExibida(quantidade);
        relatorioDeMercadoriasVendidas.addColunaExibida(unitario);
        relatorioDeMercadoriasVendidas.addColunaExibida(total);

        //Adiciona no Banco
        new AdicionaDAOConsole<>().adiciona(relatorioDeMercadoriasVendidas);
    }

    public static void relatorioDeChequesRecebidosAbertos() throws ConstraintViolationException, DadoInvalidoException {
        //Relatório de Cheques Recebidos Abertos
        //Cria o Modelo
        BundleUtil bundle = new BundleUtil();
        ModeloDeRelatorio relatorioDeChequesRecebidosAbertos = new ModeloDeRelatorioBuilder().comNome(bundle.getLabel("Relatorio_de_Cheques_Recebidos_Abertos")).comTipoRelatorio(TipoRelatorio.CHEQUES).construir();

        //Cria os Filtros
        Coluna estado = new Coluna(bundle.getLabel("Estado"), "Cheque", "estado", null, null, null, Cheque.class, EstadoDeCheque.class, null, null, 20, null);
        FiltroDeRelatorio filtroEstado = new FiltroDeRelatorio(null, estado, TipoDeBusca.IGUAL_A);
        filtroEstado.add(EstadoDeCheque.ABERTO);
        relatorioDeChequesRecebidosAbertos.addFiltro(filtroEstado);

        Coluna tipoLancamento = new Coluna(bundle.getLabel("TipoLancamento"), "Cheque", "tipoLancamento", null, null, null, Cheque.class, TipoLancamento.class, null, null, 20, null);
        FiltroDeRelatorio filtroTipoLancamento = new FiltroDeRelatorio(null, tipoLancamento, TipoDeBusca.IGUAL_A);
        filtroTipoLancamento.add(TipoLancamento.RECEBIDA);
        relatorioDeChequesRecebidosAbertos.addFiltro(filtroTipoLancamento);

        //Cria as Colunas Exibidas
        Coluna id = new Coluna(bundle.getLabel("Id"), "Cobranca", "id", null, null, null, CobrancaVariavel.class, Long.class, null, null, 20, null);
        Coluna cotacaoContaMoedaNome = new Coluna(bundle.getLabel("Nome"), "Cotacao", "cotacao", "conta", "moeda", "nome", Cotacao.class, String.class, null, null, 20, null);
        Coluna valor = new Coluna(bundle.getLabel("Valor"), "Cobranca", "valor", null, null, null, CobrancaVariavel.class, Long.class, null, Totalizador.SUM, 20, null);
        Coluna emitente = new Coluna(bundle.getLabel("Emitente"), "Cheque", "emitente", null, null, null, Cheque.class, String.class, null, null, 20, null);
        Coluna emissao = new Coluna(bundle.getLabel("Emissao"), "Cobranca", "emissao", null, null, null, CobrancaVariavel.class, Date.class, null, null, 20, null);

        //Adiciona colunas exibidas no modelo
        relatorioDeChequesRecebidosAbertos.addColunaExibida(id);
        relatorioDeChequesRecebidosAbertos.addColunaExibida(cotacaoContaMoedaNome);
        relatorioDeChequesRecebidosAbertos.addColunaExibida(valor);
        relatorioDeChequesRecebidosAbertos.addColunaExibida(emitente);
        relatorioDeChequesRecebidosAbertos.addColunaExibida(emissao);

        //Adiciona no Banco
        new AdicionaDAOConsole<>().adiciona(relatorioDeChequesRecebidosAbertos);
    }

    public static void relatorioDeChequesRecebidosDescontados() throws ConstraintViolationException, DadoInvalidoException {
        //Relatório de Cheques Descontados
        //Cria o Modelo
        BundleUtil bundle = new BundleUtil();
        ModeloDeRelatorio relatorioDeChequesDescontados = new ModeloDeRelatorioBuilder().comNome(bundle.getLabel("Relatorio_de_Cheques_Recebidos_Descontados")).comTipoRelatorio(TipoRelatorio.CHEQUES).construir();

        //Cria os Filtros
        Coluna estado = new Coluna(bundle.getLabel("Estado"), "Cheque", "estado", null, null, null, Cheque.class, EstadoDeCheque.class, null, null, 20, null);
        FiltroDeRelatorio filtroEstado = new FiltroDeRelatorio(null, estado, TipoDeBusca.IGUAL_A);
        filtroEstado.add(EstadoDeCheque.DESCONTADO);
        relatorioDeChequesDescontados.addFiltro(filtroEstado);

        Coluna tipoLancamento = new Coluna(bundle.getLabel("TipoLancamento"), "Cheque", "tipoLancamento", null, null, null, Cheque.class, TipoLancamento.class, null, null, 20, null);
        FiltroDeRelatorio filtroTipoLancamento = new FiltroDeRelatorio(null, tipoLancamento, TipoDeBusca.IGUAL_A);
        filtroTipoLancamento.add(TipoLancamento.RECEBIDA);
        relatorioDeChequesDescontados.addFiltro(filtroTipoLancamento);

        //Cria as Colunas Exibidas
        Coluna id = new Coluna(bundle.getLabel("Id"), "Cobranca", "id", null, null, null, CobrancaVariavel.class, Long.class, null, null, 20, null);
        Coluna cotacaoContaMoedaNome = new Coluna(bundle.getLabel("Nome"), "Cotacao", "cotacao", "conta", "moeda", "nome", Cotacao.class, String.class, null, null, 20, null);
        Coluna valor = new Coluna(bundle.getLabel("Valor"), "Cobranca", "valor", null, null, null, CobrancaVariavel.class, Long.class, null, Totalizador.SUM, 20, null);
        Coluna emitente = new Coluna(bundle.getLabel("Emitente"), "Cheque", "emitente", null, null, null, Cheque.class, String.class, null, null, 20, null);
        Coluna emissao = new Coluna(bundle.getLabel("Emissao"), "Cobranca", "emissao", null, null, null, CobrancaVariavel.class, Date.class, null, null, 20, null);

        //Adiciona colunas exibidas no modelo
        relatorioDeChequesDescontados.addColunaExibida(id);
        relatorioDeChequesDescontados.addColunaExibida(cotacaoContaMoedaNome);
        relatorioDeChequesDescontados.addColunaExibida(valor);
        relatorioDeChequesDescontados.addColunaExibida(emitente);
        relatorioDeChequesDescontados.addColunaExibida(emissao);

        //Adiciona no Banco
        new AdicionaDAOConsole<>().adiciona(relatorioDeChequesDescontados);
    }

    public static void relatorioDeChequesRecebidosCancelados() throws ConstraintViolationException, DadoInvalidoException {
        //Relatório de Cheques Cancelados
        //Cria o Modelo
        BundleUtil bundle = new BundleUtil();
        ModeloDeRelatorio relatorioDeChequesCancelados = new ModeloDeRelatorioBuilder().comNome(bundle.getLabel("Relatorio_de_Cheques_Recebidos_Cancelados")).comTipoRelatorio(TipoRelatorio.CHEQUES).construir();

        //Cria os Filtros
        Coluna estado = new Coluna(bundle.getLabel("Estado"), "Cheque", "estado", null, null, null, Cheque.class, EstadoDeCheque.class, null, null, 20, null);
        FiltroDeRelatorio filtroEstado = new FiltroDeRelatorio(null, estado, TipoDeBusca.IGUAL_A);
        filtroEstado.add(EstadoDeCheque.CANCELADO);
        relatorioDeChequesCancelados.addFiltro(filtroEstado);

        Coluna tipoLancamento = new Coluna(bundle.getLabel("TipoLancamento"), "Cheque", "tipoLancamento", null, null, null, Cheque.class, TipoLancamento.class, null, null, 20, null);
        FiltroDeRelatorio filtroTipoLancamento = new FiltroDeRelatorio(null, tipoLancamento, TipoDeBusca.IGUAL_A);
        filtroTipoLancamento.add(TipoLancamento.RECEBIDA);
        relatorioDeChequesCancelados.addFiltro(filtroTipoLancamento);

        //Cria as Colunas Exibidas
        Coluna id = new Coluna(bundle.getLabel("Id"), "Cobranca", "id", null, null, null, CobrancaVariavel.class, Long.class, null, null, 20, null);
        Coluna cotacaoContaMoedaNome = new Coluna(bundle.getLabel("Nome"), "Cotacao", "cotacao", "conta", "moeda", "nome", Cotacao.class, String.class, null, null, 20, null);
        Coluna valor = new Coluna(bundle.getLabel("Valor"), "Cobranca", "valor", null, null, null, CobrancaVariavel.class, Long.class, null, Totalizador.SUM, 20, null);
        Coluna emitente = new Coluna(bundle.getLabel("Emitente"), "Cheque", "emitente", null, null, null, Cheque.class, String.class, null, null, 20, null);
        Coluna emissao = new Coluna(bundle.getLabel("Emissao"), "Cobranca", "emissao", null, null, null, CobrancaVariavel.class, Date.class, null, null, 20, null);

        //Adiciona colunas exibidas no modelo
        relatorioDeChequesCancelados.addColunaExibida(id);
        relatorioDeChequesCancelados.addColunaExibida(cotacaoContaMoedaNome);
        relatorioDeChequesCancelados.addColunaExibida(valor);
        relatorioDeChequesCancelados.addColunaExibida(emitente);
        relatorioDeChequesCancelados.addColunaExibida(emissao);

        //Adiciona no Banco
        new AdicionaDAOConsole<>().adiciona(relatorioDeChequesCancelados);
    }

    public static void relatorioDeChequesRecebidosDevolvidos() throws ConstraintViolationException, DadoInvalidoException {
        //Relatório de Cheques Devolvidos
        //Cria o Modelo
        BundleUtil bundle = new BundleUtil();
        ModeloDeRelatorio relatorioDeChequesDevolvidos = new ModeloDeRelatorioBuilder().comNome(bundle.getLabel("Relatorio_de_Cheques_Recebidos_Devolvidos")).comTipoRelatorio(TipoRelatorio.CHEQUES).construir();

        //Cria os Filtros
        Coluna estado = new Coluna(bundle.getLabel("Estado"), "Cheque", "estado", null, null, null, Cheque.class, EstadoDeCheque.class, null, null, 20, null);
        FiltroDeRelatorio filtroEstado = new FiltroDeRelatorio(null, estado, TipoDeBusca.IGUAL_A);
        filtroEstado.add(EstadoDeCheque.DEVOLVIDO);
        relatorioDeChequesDevolvidos.addFiltro(filtroEstado);

        Coluna tipoLancamento = new Coluna(bundle.getLabel("TipoLancamento"), "Cheque", "tipoLancamento", null, null, null, Cheque.class, TipoLancamento.class, null, null, 20, null);
        FiltroDeRelatorio filtroTipoLancamento = new FiltroDeRelatorio(null, tipoLancamento, TipoDeBusca.IGUAL_A);
        filtroTipoLancamento.add(TipoLancamento.RECEBIDA);
        relatorioDeChequesDevolvidos.addFiltro(filtroTipoLancamento);

        //Cria as Colunas Exibidas
        Coluna id = new Coluna(bundle.getLabel("Id"), "Cobranca", "id", null, null, null, CobrancaVariavel.class, Long.class, null, null, 20, null);
        Coluna cotacaoContaMoedaNome = new Coluna(bundle.getLabel("Nome"), "Cotacao", "cotacao", "conta", "moeda", "nome", Cotacao.class, String.class, null, null, 20, null);
        Coluna valor = new Coluna(bundle.getLabel("Valor"), "Cobranca", "valor", null, null, null, CobrancaVariavel.class, Long.class, null, Totalizador.SUM, 20, null);
        Coluna emitente = new Coluna(bundle.getLabel("Emitente"), "Cheque", "emitente", null, null, null, Cheque.class, String.class, null, null, 20, null);
        Coluna emissao = new Coluna(bundle.getLabel("Emissao"), "Cobranca", "emissao", null, null, null, CobrancaVariavel.class, Date.class, null, null, 20, null);

        //Adiciona colunas exibidas no modelo
        relatorioDeChequesDevolvidos.addColunaExibida(id);
        relatorioDeChequesDevolvidos.addColunaExibida(cotacaoContaMoedaNome);
        relatorioDeChequesDevolvidos.addColunaExibida(valor);
        relatorioDeChequesDevolvidos.addColunaExibida(emitente);
        relatorioDeChequesDevolvidos.addColunaExibida(emissao);

        //Adiciona no Banco
        new AdicionaDAOConsole<>().adiciona(relatorioDeChequesDevolvidos);
    }

    public static void relatorioDeChequesEmitidosAbertos() throws ConstraintViolationException, DadoInvalidoException {
        //Relatório de Cheques Recebidos Abertos
        //Cria o Modelo
        BundleUtil bundle = new BundleUtil();
        ModeloDeRelatorio relatorioDeChequesRecebidosAbertos = new ModeloDeRelatorioBuilder().comNome(bundle.getLabel("Relatorio_de_Cheques_Emitidos_Abertos")).comTipoRelatorio(TipoRelatorio.CHEQUES).construir();

        //Cria os Filtros
        Coluna estado = new Coluna(bundle.getLabel("Estado"), "Cheque", "estado", null, null, null, Cheque.class, EstadoDeCheque.class, null, null, 20, null);
        FiltroDeRelatorio filtroEstado = new FiltroDeRelatorio(null, estado, TipoDeBusca.IGUAL_A);
        filtroEstado.add(EstadoDeCheque.ABERTO);
        relatorioDeChequesRecebidosAbertos.addFiltro(filtroEstado);

        Coluna tipoLancamento = new Coluna(bundle.getLabel("TipoLancamento"), "Cheque", "tipoLancamento", null, null, null, Cheque.class, TipoLancamento.class, null, null, 20, null);
        FiltroDeRelatorio filtroTipoLancamento = new FiltroDeRelatorio(null, tipoLancamento, TipoDeBusca.IGUAL_A);
        filtroTipoLancamento.add(TipoLancamento.EMITIDA);
        relatorioDeChequesRecebidosAbertos.addFiltro(filtroTipoLancamento);

        //Cria as Colunas Exibidas
        Coluna id = new Coluna(bundle.getLabel("Id"), "Cobranca", "id", null, null, null, CobrancaVariavel.class, Long.class, null, null, 20, null);
        Coluna cotacaoContaMoedaNome = new Coluna(bundle.getLabel("Nome"), "Cotacao", "cotacao", "conta", "moeda", "nome", Cotacao.class, String.class, null, null, 20, null);
        Coluna valor = new Coluna(bundle.getLabel("Valor"), "Cobranca", "valor", null, null, null, CobrancaVariavel.class, Long.class, null, Totalizador.SUM, 20, null);
        Coluna emitente = new Coluna(bundle.getLabel("Emitente"), "Cheque", "emitente", null, null, null, Cheque.class, String.class, null, null, 20, null);
        Coluna emissao = new Coluna(bundle.getLabel("Emissao"), "Cobranca", "emissao", null, null, null, CobrancaVariavel.class, Date.class, null, null, 20, null);

        //Adiciona colunas exibidas no modelo
        relatorioDeChequesRecebidosAbertos.addColunaExibida(id);
        relatorioDeChequesRecebidosAbertos.addColunaExibida(cotacaoContaMoedaNome);
        relatorioDeChequesRecebidosAbertos.addColunaExibida(valor);
        relatorioDeChequesRecebidosAbertos.addColunaExibida(emitente);
        relatorioDeChequesRecebidosAbertos.addColunaExibida(emissao);

        //Adiciona no Banco
        new AdicionaDAOConsole<>().adiciona(relatorioDeChequesRecebidosAbertos);
    }

    public static void relatorioDeChequesEmitidosDescontados() throws ConstraintViolationException, DadoInvalidoException {
        //Relatório de Cheques Descontados
        //Cria o Modelo
        BundleUtil bundle = new BundleUtil();
        ModeloDeRelatorio relatorioDeChequesDescontados = new ModeloDeRelatorioBuilder().comNome(bundle.getLabel("Relatorio_de_Cheques_Emitidos_Descontados")).comTipoRelatorio(TipoRelatorio.CHEQUES).construir();

        //Cria os Filtros
        Coluna estado = new Coluna(bundle.getLabel("Estado"), "Cheque", "estado", null, null, null, Cheque.class, EstadoDeCheque.class, null, null, 20, null);
        FiltroDeRelatorio filtroEstado = new FiltroDeRelatorio(null, estado, TipoDeBusca.IGUAL_A);
        filtroEstado.add(EstadoDeCheque.DESCONTADO);
        relatorioDeChequesDescontados.addFiltro(filtroEstado);

        Coluna tipoLancamento = new Coluna(bundle.getLabel("TipoLancamento"), "Cheque", "tipoLancamento", null, null, null, Cheque.class, TipoLancamento.class, null, null, 20, null);
        FiltroDeRelatorio filtroTipoLancamento = new FiltroDeRelatorio(null, tipoLancamento, TipoDeBusca.IGUAL_A);
        filtroTipoLancamento.add(TipoLancamento.EMITIDA);
        relatorioDeChequesDescontados.addFiltro(filtroTipoLancamento);

        //Cria as Colunas Exibidas
        Coluna id = new Coluna(bundle.getLabel("Id"), "Cobranca", "id", null, null, null, CobrancaVariavel.class, Long.class, null, null, 20, null);
        Coluna cotacaoContaMoedaNome = new Coluna(bundle.getLabel("Nome"), "Cotacao", "cotacao", "conta", "moeda", "nome", Cotacao.class, String.class, null, null, 20, null);
        Coluna valor = new Coluna(bundle.getLabel("Valor"), "Cobranca", "valor", null, null, null, CobrancaVariavel.class, Long.class, null, Totalizador.SUM, 20, null);
        Coluna emitente = new Coluna(bundle.getLabel("Emitente"), "Cheque", "emitente", null, null, null, Cheque.class, String.class, null, null, 20, null);
        Coluna emissao = new Coluna(bundle.getLabel("Emissao"), "Cobranca", "emissao", null, null, null, CobrancaVariavel.class, Date.class, null, null, 20, null);

        //Adiciona colunas exibidas no modelo
        relatorioDeChequesDescontados.addColunaExibida(id);
        relatorioDeChequesDescontados.addColunaExibida(cotacaoContaMoedaNome);
        relatorioDeChequesDescontados.addColunaExibida(valor);
        relatorioDeChequesDescontados.addColunaExibida(emitente);
        relatorioDeChequesDescontados.addColunaExibida(emissao);

        //Adiciona no Banco
        new AdicionaDAOConsole<>().adiciona(relatorioDeChequesDescontados);
    }

    public static void relatorioDeChequesEmitidosCancelados() throws ConstraintViolationException, DadoInvalidoException {
        //Relatório de Cheques Cancelados
        //Cria o Modelo
        BundleUtil bundle = new BundleUtil();
        ModeloDeRelatorio relatorioDeChequesCancelados = new ModeloDeRelatorioBuilder().comNome(bundle.getLabel("Relatorio_de_Cheques_Emitidos_Cancelados")).comTipoRelatorio(TipoRelatorio.CHEQUES).construir();

        //Cria os Filtros
        Coluna estado = new Coluna(bundle.getLabel("Estado"), "Cheque", "estado", null, null, null, Cheque.class, EstadoDeCheque.class, null, null, 20, null);
        FiltroDeRelatorio filtroEstado = new FiltroDeRelatorio(null, estado, TipoDeBusca.IGUAL_A);
        filtroEstado.add(EstadoDeCheque.CANCELADO);
        relatorioDeChequesCancelados.addFiltro(filtroEstado);

        Coluna tipoLancamento = new Coluna(bundle.getLabel("TipoLancamento"), "Cheque", "tipoLancamento", null, null, null, Cheque.class, TipoLancamento.class, null, null, 20, null);
        FiltroDeRelatorio filtroTipoLancamento = new FiltroDeRelatorio(null, tipoLancamento, TipoDeBusca.IGUAL_A);
        filtroTipoLancamento.add(TipoLancamento.EMITIDA);
        relatorioDeChequesCancelados.addFiltro(filtroTipoLancamento);

        //Cria as Colunas Exibidas
        Coluna id = new Coluna(bundle.getLabel("Id"), "Cobranca", "id", null, null, null, CobrancaVariavel.class, Long.class, null, null, 20, null);
        Coluna cotacaoContaMoedaNome = new Coluna(bundle.getLabel("Nome"), "Cotacao", "cotacao", "conta", "moeda", "nome", Cotacao.class, String.class, null, null, 20, null);
        Coluna valor = new Coluna(bundle.getLabel("Valor"), "Cobranca", "valor", null, null, null, CobrancaVariavel.class, Long.class, null, Totalizador.SUM, 20, null);
        Coluna emitente = new Coluna(bundle.getLabel("Emitente"), "Cheque", "emitente", null, null, null, Cheque.class, String.class, null, null, 20, null);
        Coluna emissao = new Coluna(bundle.getLabel("Emissao"), "Cobranca", "emissao", null, null, null, CobrancaVariavel.class, Date.class, null, null, 20, null);

        //Adiciona colunas exibidas no modelo
        relatorioDeChequesCancelados.addColunaExibida(id);
        relatorioDeChequesCancelados.addColunaExibida(cotacaoContaMoedaNome);
        relatorioDeChequesCancelados.addColunaExibida(valor);
        relatorioDeChequesCancelados.addColunaExibida(emitente);
        relatorioDeChequesCancelados.addColunaExibida(emissao);

        //Adiciona no Banco
        new AdicionaDAOConsole<>().adiciona(relatorioDeChequesCancelados);
    }

    public static void relatorioDeChequesEmitidosDevolvidos() throws ConstraintViolationException, DadoInvalidoException {
        //Relatório de Cheques Devolvidos
        //Cria o Modelo
        BundleUtil bundle = new BundleUtil();
        ModeloDeRelatorio relatorioDeChequesDevolvidos = new ModeloDeRelatorioBuilder().comNome(bundle.getLabel("Relatorio_de_Cheques_Emitidos_Devolvidos")).comTipoRelatorio(TipoRelatorio.CHEQUES).construir();

        //Cria os Filtros
        Coluna estado = new Coluna(bundle.getLabel("Estado"), "Cheque", "estado", null, null, null, Cheque.class, EstadoDeCheque.class, null, null, 20, null);
        FiltroDeRelatorio filtroEstado = new FiltroDeRelatorio(null, estado, TipoDeBusca.IGUAL_A);
        filtroEstado.add(EstadoDeCheque.DEVOLVIDO);
        relatorioDeChequesDevolvidos.addFiltro(filtroEstado);

        Coluna tipoLancamento = new Coluna(bundle.getLabel("TipoLancamento"), "Cheque", "tipoLancamento", null, null, null, Cheque.class, TipoLancamento.class, null, null, 20, null);
        FiltroDeRelatorio filtroTipoLancamento = new FiltroDeRelatorio(null, tipoLancamento, TipoDeBusca.IGUAL_A);
        filtroTipoLancamento.add(TipoLancamento.EMITIDA);
        relatorioDeChequesDevolvidos.addFiltro(filtroTipoLancamento);

        //Cria as Colunas Exibidas
        Coluna id = new Coluna(bundle.getLabel("Id"), "Cobranca", "id", null, null, null, CobrancaVariavel.class, Long.class, null, null, 20, null);
        Coluna cotacaoContaMoedaNome = new Coluna(bundle.getLabel("Nome"), "Cotacao", "cotacao", "conta", "moeda", "nome", Cotacao.class, String.class, null, null, 20, null);
        Coluna valor = new Coluna(bundle.getLabel("Valor"), "Cobranca", "valor", null, null, null, CobrancaVariavel.class, Long.class, null, Totalizador.SUM, 20, null);
        Coluna emitente = new Coluna(bundle.getLabel("Emitente"), "Cheque", "emitente", null, null, null, Cheque.class, String.class, null, null, 20, null);
        Coluna emissao = new Coluna(bundle.getLabel("Emissao"), "Cobranca", "emissao", null, null, null, CobrancaVariavel.class, Date.class, null, null, 20, null);

        //Adiciona colunas exibidas no modelo
        relatorioDeChequesDevolvidos.addColunaExibida(id);
        relatorioDeChequesDevolvidos.addColunaExibida(cotacaoContaMoedaNome);
        relatorioDeChequesDevolvidos.addColunaExibida(valor);
        relatorioDeChequesDevolvidos.addColunaExibida(emitente);
        relatorioDeChequesDevolvidos.addColunaExibida(emissao);

        //Adiciona no Banco
        new AdicionaDAOConsole<>().adiciona(relatorioDeChequesDevolvidos);

    }

}
