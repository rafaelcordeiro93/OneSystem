
import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.IVA;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Janela;
import br.com.onesystem.domain.Modulo;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.PessoaFisica;
import br.com.onesystem.domain.Privilegio;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.ClassificacaoFinanceira;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import br.com.onesystem.valueobjects.TipoBandeira;
import br.com.onesystem.valueobjects.TipoCorMenu;
import br.com.onesystem.valueobjects.TipoDeCalculoDeCusto;
import br.com.onesystem.valueobjects.TipoDeFormacaoDePreco;
import br.com.onesystem.valueobjects.TipoItem;
import br.com.onesystem.valueobjects.TipoPessoa;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class DadosIniciais {

    public static void main(String[] args) throws DadoInvalidoException {
        Pessoa rauber = new PessoaFisica(null, null, null, "Rauber", TipoPessoa.PESSOA_FISICA, null, true, null, null, true, true, true, true, null, new Double(0), null, null, null, null, null, "rauber@rrminds.com", null);
        Pessoa cordeiro = new PessoaFisica(null, null, null, "Cordeiro", TipoPessoa.PESSOA_FISICA, null, true, null, null, true, true, true, true, null, new Double(0), null, null, null, null, null, "cordeiro@rrminds.com", null);
        new AdicionaDAO<Pessoa>().adiciona(cordeiro);
        new AdicionaDAO<Pessoa>().adiciona(rauber);

        GrupoDePrivilegio grupoDePrivilegio = new GrupoDePrivilegio(null, "Administrador");
        new AdicionaDAO<GrupoDePrivilegio>().adiciona(grupoDePrivilegio);

        // -- Adiciona Módulos
        AdicionaDAO<Modulo> daoModulo = new AdicionaDAO<>();

        Modulo arq = new Modulo(null, "Arquivo");
        Modulo fin = new Modulo(null, "Financeiro");
        Modulo cam = new Modulo(null, "Câmbio");
        Modulo rela = new Modulo(null, "Relatórios");
        Modulo pref = new Modulo(null, "Preferências");
        Modulo login = new Modulo(null, "Login");
        Modulo admin = new Modulo(null, "Administrativo");
        Modulo estoque = new Modulo(null, "Estoque");
        Modulo notas = new Modulo(null, "Notas");

        daoModulo.adiciona(arq);
        daoModulo.adiciona(fin);
        daoModulo.adiciona(cam);
        daoModulo.adiciona(rela);
        daoModulo.adiciona(admin);
        daoModulo.adiciona(pref);
        daoModulo.adiciona(login);
        daoModulo.adiciona(estoque);
        daoModulo.adiciona(notas);

        // -- Adiciona Módulos
        AdicionaDAO<Janela> daoJanela = new AdicionaDAO<>();

        //Modulo de Arquivo
        Janela dashboard = new Janela(null, "Dashbord", "/dashboard.xhtml", arq);
        Janela cidade = new Janela(null, "Cidade", "/cidade.xhtml", arq);
        Janela jpessoa = new Janela(null, "Pessoa", "/pessoa.xhtml", arq);
        Janela jpessoaimport = new Janela(null, "Importador de Pessoas", "/importarPessoa.xhtml", arq);

        daoJanela.adiciona(dashboard);
        daoJanela.adiciona(cidade);
        daoJanela.adiciona(jpessoa);
        daoJanela.adiciona(jpessoaimport);

        //Modulo de Notas
        Janela notaSaida = new Janela(null, "Nota de Saida", "/notaEmitida.xhtml", arq);
        Janela consultaNotaSaida = new Janela(null, "Consulta Nota de Saida", "/consultaNotaEmitida.xhtml", arq);
        Janela orcamento = new Janela(null, "Orçamento", "/orcamento.xhtml", arq);
        Janela consultaOrcamento = new Janela(null, "Consulta Orçamento", "/consultaOrcamento.xhtml", arq);
        Janela comanda = new Janela(null, "Comanda", "/comanda.xhtml", arq);

        daoJanela.adiciona(notaSaida);
        daoJanela.adiciona(consultaNotaSaida);
        daoJanela.adiciona(orcamento);
        daoJanela.adiciona(consultaOrcamento);
        daoJanela.adiciona(comanda);

        //Modulo de Estoque
        Janela jitem = new Janela(null, "Item", "/item.xhtml", estoque);
        Janela margem = new Janela(null, "Margem", "/margem.xhtml", estoque);
        Janela conhecimentoFrete = new Janela(null, "Conhecimento de Frete", "/conhecimentoDeFrete.xhtml", estoque);
        Janela ajusteEstoque = new Janela(null, "Ajuste de Estoque", "/ajusteDeEstoque.xhtml", estoque);
        Janela contaEstoque = new Janela(null, "Conta de Estoque", "/contaDeEstoque.xhtml", estoque);
        Janela unidadeMedida = new Janela(null, "Unidade de Medida", "/unidadeMedidaItem.xhtml", estoque);
        Janela jiva = new Janela(null, "IVA", "/iva.xhtml", estoque);
        Janela marca = new Janela(null, "Marca", "/marca.xhtml", estoque);
        Janela depositos = new Janela(null, "Depositos", "/deposito.xhtml", estoque);
        Janela listaPreco = new Janela(null, "Lista de Preco", "/listaDePreco.xhtml", estoque);
        Janela comissao = new Janela(null, "Comissao", "/comissao.xhtml", estoque);
        Janela grupo = new Janela(null, "Grupo", "/grupo.xhtml", estoque);
        Janela jgrupoFiscal = new Janela(null, "Grupo Fiscal", "/grupoFiscal.xhtml", estoque);
        Janela notaRecebida = new Janela(null, "Nota Recebida", "/notaRecebida.xhtml", estoque);
        Janela consultaNotaRecebida = new Janela(null, "Consulta Nota Recebida", "/consultaNotaRecebida.xhtml", estoque);

        daoJanela.adiciona(jitem);
        daoJanela.adiciona(margem);
        daoJanela.adiciona(conhecimentoFrete);
        daoJanela.adiciona(ajusteEstoque);
        daoJanela.adiciona(contaEstoque);
        daoJanela.adiciona(unidadeMedida);
        daoJanela.adiciona(jiva);
        daoJanela.adiciona(marca);
        daoJanela.adiciona(depositos);
        daoJanela.adiciona(listaPreco);
        daoJanela.adiciona(comissao);
        daoJanela.adiciona(grupo);
        daoJanela.adiciona(jgrupoFiscal);
        daoJanela.adiciona(notaRecebida);
        daoJanela.adiciona(consultaNotaRecebida);

        //Modulo Financeiro
        Janela receberValores = new Janela(null, "Receber Valores", "/recebimento.xhtml", fin);
        Janela pagarValores = new Janela(null, "Pagar Valores", "/pagamento.xhtml", fin);
        Janela extratoConta = new Janela(null, "Extrato Conta", "/extratoConta.xhtml", fin);
        Janela cotacoes = new Janela(null, "Cotacao", "/cotacao.xhtml", fin);
        Janela baixa = new Janela(null, "Baixa", "/baixa.xhtml", fin);
        Janela formarecebimento = new Janela(null, "Formas de Recebimento", "/formaDeRecebimento.xhtml", fin);
        Janela jbanco = new Janela(null, "Banco", "/banco.xhtml", fin);
        Janela moeda = new Janela(null, "Moeda", "/moeda.xhtml", fin);
        Janela jconta = new Janela(null, "Conta", "/conta.xhtml", fin);
        Janela tipoReceita = new Janela(null, "Tipo Receita", "/tipoReceita.xhtml", fin);
        Janela tipoDespesa = new Janela(null, "Tipo Despesa", "/tipoDespesa.xhtml", fin);
        Janela transfLucro = new Janela(null, "Tranferência de Lucro", "/transferenciaDespesaProvisionada.xhtml", fin);
        Janela grupoFinance = new Janela(null, "Grupo Financeiro", "/grupoFinanceiro.xhtml", fin);
        Janela despProvisi = new Janela(null, "Despesa Provisionada", "/despesaProvisionada.xhtml", fin);
        Janela receitProvisi = new Janela(null, "Receita Provisionada", "/receitaProvisionada.xhtml", fin);
        Janela cartao = new Janela(null, "Cartao", "/cartao.xhtml", fin);
        Janela boletoDeCartao = new Janela(null, "Boleto de Cartao", "/boletoDeCartao.xhtml", fin);
        Janela cheque = new Janela(null, "Cheque", "/cheque.xhtml", fin);

        daoJanela.adiciona(receberValores);
        daoJanela.adiciona(pagarValores);
        daoJanela.adiciona(extratoConta);
        daoJanela.adiciona(cotacoes);
        daoJanela.adiciona(baixa);
        daoJanela.adiciona(formarecebimento);
        daoJanela.adiciona(jbanco);
        daoJanela.adiciona(moeda);
        daoJanela.adiciona(jconta);
        daoJanela.adiciona(tipoDespesa);
        daoJanela.adiciona(tipoReceita);
        daoJanela.adiciona(transfLucro);
        daoJanela.adiciona(grupoFinance);
        daoJanela.adiciona(despProvisi);
        daoJanela.adiciona(receitProvisi);
        daoJanela.adiciona(cartao);
        daoJanela.adiciona(boletoDeCartao);
        daoJanela.adiciona(cheque);

        //Modulo de Cambio
        Janela recepcao = new Janela(null, "Recepção", "/recepcao.xhtml", cam);
        Janela contraCambio = new Janela(null, "Contrato de Câmbio", "/contratoDeCambio.xhtml", cam);
        Janela cambio = new Janela(null, "Câmbio", "/cambio.xhtml", cam);

        daoJanela.adiciona(recepcao);
        daoJanela.adiciona(contraCambio);
        daoJanela.adiciona(cambio);

        //Modulo de Relatorios
        Janela relContaPagar = new Janela(null, "Relatório de Conta A Pagar", "/relatorioDeContaAPagar.xhtml", rela);
        Janela relContaReceber = new Janela(null, "Relatório de Conta A Receber", "/relatorioDeContaAReceber.xhtml", rela);
        Janela relSaldo = new Janela(null, "Relatório de Saldo de Conta", "/relatorioDeSaldoDeConta.xhtml", rela);
        Janela relRecepcao = new Janela(null, "Relatório de Recepção", "/relatorioDeRecepcao.xhtml", rela);
        Janela relContrato = new Janela(null, "Relatório de Contrato de Câmbio", "/relatorioDeContratoDeCambio.xhtml", rela);
        Janela relCambio = new Janela(null, "Relatório de Câmbio", "/relatorioDeCambio.xhtml", rela);
        Janela relSaldoDivisao = new Janela(null, "Relatório de Saldo de Divisão de Lucro", "/relatorioDeSaldoDeDivisaoDeLucro.xhtml", rela);
        Janela relBalancoFisico = new Janela(null, "Relatório de Balanco Fisico", "/relatorioDeBalancoFisico.xhtml", rela);

        daoJanela.adiciona(relCambio);
        daoJanela.adiciona(relContaPagar);
        daoJanela.adiciona(relContaReceber);
        daoJanela.adiciona(relContrato);
        daoJanela.adiciona(relRecepcao);
        daoJanela.adiciona(relSaldo);
        daoJanela.adiciona(relSaldoDivisao);
        daoJanela.adiciona(relBalancoFisico);

        //Modulo Administrativo
        Janela usuario = new Janela(null, "Usuário", "/usuario.xhtml", admin);
        Janela jconfiguracao = new Janela(null, "Configuração", "/configuracao.xhtml", admin);
        Janela operacoes = new Janela(null, "Operacoes", "/operacoes.xhtml", admin);
        Janela jgrupoPrivilegio = new Janela(null, "Grupo de Privilegios", "/grupoPrivilegio.xhtml", admin);
        Janela privilegio = new Janela(null, "Privilégio", "/privilegio.xhtml", admin);
        Janela jconfigNecessario = new Janela(null, "Configuracao Necessaria", "/configuracaoNecessaria.xhtml", admin);

        daoJanela.adiciona(usuario);
        daoJanela.adiciona(jconfiguracao);
        daoJanela.adiciona(operacoes);
        daoJanela.adiciona(jgrupoPrivilegio);
        daoJanela.adiciona(privilegio);
        daoJanela.adiciona(jconfigNecessario);

        //Modulo de Preferencias
        Janela perfilUsuario = new Janela(null, "Perfil", "/perfilUsuario.xhtml", pref);
        daoJanela.adiciona(perfilUsuario);

        //Modulo de Login
        Janela jlogin = new Janela(null, "Login", "/login.xhtml", login);
        daoJanela.adiciona(jlogin);

        // -- Adiciona Privilégios
        AdicionaDAO<Privilegio> daoPrivilegio = new AdicionaDAO<>();
        List<Privilegio> listaPrivilegios = Arrays.asList(
                new Privilegio(null, dashboard, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, jpessoa, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, cidade, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, jpessoaimport, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, notaSaida, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, consultaNotaSaida, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, orcamento, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, consultaOrcamento, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, comanda, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, jitem, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, notaRecebida, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, consultaNotaRecebida, true, true, true, true, grupoDePrivilegio),
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
                new Privilegio(null, pagarValores, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, extratoConta, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, cotacoes, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, baixa, true, true, true, true, grupoDePrivilegio),
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
                new Privilegio(null, recepcao, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, contraCambio, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, cambio, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relCambio, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relContaPagar, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relContaReceber, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relContrato, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relRecepcao, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relSaldo, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relSaldoDivisao, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, relBalancoFisico, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, usuario, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, jconfiguracao, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, jconfigNecessario, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, operacoes, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, jgrupoPrivilegio, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, privilegio, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, perfilUsuario, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, jlogin, true, true, true, true, grupoDePrivilegio)
        );

        for (Privilegio p : listaPrivilegios) {
            daoPrivilegio.adiciona(p);
        }
        Usuario usercordeiro = new Usuario(null, cordeiro, "81dc9bdb52d04dc20036dbd8313ed055", grupoDePrivilegio, true, "steel", "blue", true, TipoCorMenu.CINZA, false);
        Usuario userrauber = new Usuario(null, rauber, "81dc9bdb52d04dc20036dbd8313ed055", grupoDePrivilegio, true, "steel", "blue", true, TipoCorMenu.CINZA, false);
        new AdicionaDAO<Usuario>().adiciona(usercordeiro);
        new AdicionaDAO<Usuario>().adiciona(userrauber);
        // -- Adiciona Grupo Financeiro
        AdicionaDAO<GrupoFinanceiro> daoGrupoFinanceiro = new AdicionaDAO<>();

        GrupoFinanceiro aje = new GrupoFinanceiro(null, "Ajuste de Saldo Inicial", NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.OUTRAS);
        GrupoFinanceiro dev = new GrupoFinanceiro(null, "(-) Devoluções de Vendas", NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.DEDUCOES_DE_RECEITA_BRUTA);
        GrupoFinanceiro imp = new GrupoFinanceiro(null, "Impostos Sobre Faturamento", NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.DEDUCOES_DE_RECEITA_BRUTA);
        GrupoFinanceiro tot = new GrupoFinanceiro(null, "Total dos Custos Variáveis", NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.CUSTOS_OPERACIONAIS);
        GrupoFinanceiro depv = new GrupoFinanceiro(null, "Despesas Com Vendas", NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.OPERACIONAIS);
        GrupoFinanceiro depp = new GrupoFinanceiro(null, "Despesas Com Pessoal", NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.OPERACIONAIS);
        GrupoFinanceiro depa = new GrupoFinanceiro(null, "Despesas Administrativas", NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.OPERACIONAIS);
        GrupoFinanceiro dept = new GrupoFinanceiro(null, "Despesas Tributarias", NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.OPERACIONAIS);
        GrupoFinanceiro depf = new GrupoFinanceiro(null, "Despesas Financeiras", NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.CUSTOS_OPERACIONAIS);
        GrupoFinanceiro depo = new GrupoFinanceiro(null, "Despesas Não Operacionais", NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.NAO_OPERACIONAIS);
        GrupoFinanceiro ope = new GrupoFinanceiro(null, "Operações Com Estoque", NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.OUTRAS);
        GrupoFinanceiro opi = new GrupoFinanceiro(null, "Operações Com Imobilizado", NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.NAO_OPERACIONAIS);
        GrupoFinanceiro oo = new GrupoFinanceiro(null, "Outras Operações", NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.NAO_OPERACIONAIS);
        GrupoFinanceiro cp = new GrupoFinanceiro(null, "Custos de Produção", NaturezaFinanceira.DESPESA, ClassificacaoFinanceira.CUSTOS_OPERACIONAIS);

        daoGrupoFinanceiro.adiciona(aje);
        daoGrupoFinanceiro.adiciona(dev);
        daoGrupoFinanceiro.adiciona(imp);
        daoGrupoFinanceiro.adiciona(tot);
        daoGrupoFinanceiro.adiciona(depv);
        daoGrupoFinanceiro.adiciona(depp);
        daoGrupoFinanceiro.adiciona(depa);
        daoGrupoFinanceiro.adiciona(dept);
        daoGrupoFinanceiro.adiciona(depf);
        daoGrupoFinanceiro.adiciona(depo);
        daoGrupoFinanceiro.adiciona(ope);
        daoGrupoFinanceiro.adiciona(opi);
        daoGrupoFinanceiro.adiciona(oo);
        daoGrupoFinanceiro.adiciona(cp);

        // -- Adiciona TipoDespesa
        AdicionaDAO<TipoDespesa> daoDespesa = new AdicionaDAO<>();

        daoDespesa.adiciona(new TipoDespesa(null, "Ajuste de Saldo Inicial", aje));
        daoDespesa.adiciona(new TipoDespesa(null, "PIS Sobre Faturamento", imp));
        daoDespesa.adiciona(new TipoDespesa(null, "COFINS Sobre Faturamento", imp));
        daoDespesa.adiciona(new TipoDespesa(null, "ICMS Sobre Mercadorias", imp));
        daoDespesa.adiciona(new TipoDespesa(null, "ISS Sobre Servicos", imp));
        daoDespesa.adiciona(new TipoDespesa(null, "DARF - Simples Sobre Faturamento", dept));
        daoDespesa.adiciona(new TipoDespesa(null, "(-) Devolucoes de Vendas", dev));
        daoDespesa.adiciona(new TipoDespesa(null, "Custo da Mercadoria Vendida", tot));
        daoDespesa.adiciona(new TipoDespesa(null, "Custo dos Servicos Prestados", tot));
        daoDespesa.adiciona(new TipoDespesa(null, "Comissoes Sobre Venda", tot));
        daoDespesa.adiciona(new TipoDespesa(null, "Propaganda e Publicidade", depv));
        daoDespesa.adiciona(new TipoDespesa(null, "Doacoes, Brindes e Bonificacoes", depv));
        daoDespesa.adiciona(new TipoDespesa(null, "Desp. De Frete de Entregas", depv));
        daoDespesa.adiciona(new TipoDespesa(null, "Salarios", depp));
        daoDespesa.adiciona(new TipoDespesa(null, "Outras Comissoes", depp));
        daoDespesa.adiciona(new TipoDespesa(null, "Gratificacoes", depp));
        daoDespesa.adiciona(new TipoDespesa(null, "Estagiarios", depp));
        daoDespesa.adiciona(new TipoDespesa(null, "13º Salario", depp));
        daoDespesa.adiciona(new TipoDespesa(null, "Ferias", depp));
        daoDespesa.adiciona(new TipoDespesa(null, "Vale Transporte", depp));
        daoDespesa.adiciona(new TipoDespesa(null, "Assistencia a Empregados", depp));
        daoDespesa.adiciona(new TipoDespesa(null, "Aviso Previo e Indeniz. Trabalhista", depp));
        daoDespesa.adiciona(new TipoDespesa(null, "Treinamentos", depp));
        daoDespesa.adiciona(new TipoDespesa(null, "FGTS", depp));
        daoDespesa.adiciona(new TipoDespesa(null, "INSS", depp));
        daoDespesa.adiciona(new TipoDespesa(null, "Pro-labore", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Agua", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Telefone", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Energia Eletrica", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Aluguel Comercial", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Aluguel de Sistemas", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Honorarios Profissionais", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Combustiveis e Lubrificantes", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Contribuicoes a Assoc. e Entidades", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Contribuicoes Sindicais", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Taxas Diversas", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Pedagio", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Cartorio", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Correios", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Dispendio c/ Alimentacao", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Feiras, Congressos e Cursos", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Passagens, Viagens e Estadias", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Fotocopias e Impressoes", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Fretes de Compras", tot));
        daoDespesa.adiciona(new TipoDespesa(null, "IOF IMP. OPER. FINANCEIRA", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Depreciacoes", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Manutencao e Conservacao do Predio", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Manutencao de Veiculos", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Material de Expediente, Limpeza e Consumo", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Servicos de Terc. Pessoa Fisica", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Confraternizacoes", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "Outras Administrativas", depa));
        daoDespesa.adiciona(new TipoDespesa(null, "MEDICINA DO TRABALHO", depp));
        daoDespesa.adiciona(new TipoDespesa(null, "IRPJ", dept));
        daoDespesa.adiciona(new TipoDespesa(null, "Contribuicao Social S/ Lucro Liquido", dept));
        daoDespesa.adiciona(new TipoDespesa(null, "Outros Impostos Estaduais", dept));
        daoDespesa.adiciona(new TipoDespesa(null, "Outros Impostos Municipais", dept));
        daoDespesa.adiciona(new TipoDespesa(null, "Impostos e Taxas Diversas", dept));
        daoDespesa.adiciona(new TipoDespesa(null, "Descontos Concedidos", tot));
        daoDespesa.adiciona(new TipoDespesa(null, "Juros Pagos", tot));
        daoDespesa.adiciona(new TipoDespesa(null, "Multas Pagas", depf));
        daoDespesa.adiciona(new TipoDespesa(null, "Outras Financeiras", depf));
        daoDespesa.adiciona(new TipoDespesa(null, "Tarifas Bancarias", tot));
        daoDespesa.adiciona(new TipoDespesa(null, "Correcao Monetaria Paga", depf));
        daoDespesa.adiciona(new TipoDespesa(null, "Encargos e Moras Fiscais", depf));
        daoDespesa.adiciona(new TipoDespesa(null, "Despesas com Cobrancas", tot));
        daoDespesa.adiciona(new TipoDespesa(null, "Taxa de Adm Cartao de Credito", tot));
        daoDespesa.adiciona(new TipoDespesa(null, "Despesas Indedutiveis", depo));
        daoDespesa.adiciona(new TipoDespesa(null, "Infracoes Fiscais", depo));
        daoDespesa.adiciona(new TipoDespesa(null, "Prejuizos na Venda de Imobilizado", depo));
        daoDespesa.adiciona(new TipoDespesa(null, "Sinistros", depo));
        daoDespesa.adiciona(new TipoDespesa(null, "Perdas com Estoque", depo));
        daoDespesa.adiciona(new TipoDespesa(null, "Outras nao Operacionais", depo));
        daoDespesa.adiciona(new TipoDespesa(null, "Compras Normais", ope));
        daoDespesa.adiciona(new TipoDespesa(null, "Compras em Consignacao", ope));
        daoDespesa.adiciona(new TipoDespesa(null, "Compras de Ativo Imobilizado", opi));
        daoDespesa.adiciona(new TipoDespesa(null, "Compras p/ Recebimento Futuro", ope));

        // -- Adiciona Receitas
        AdicionaDAO<TipoReceita> daoReceita = new AdicionaDAO<>();

        daoReceita.adiciona(new TipoReceita(null, "Ajuste de Saldo Inicial", aje));
        daoReceita.adiciona(new TipoReceita(null, "Venda a Vista", imp));
        daoReceita.adiciona(new TipoReceita(null, "Venda a Prazo", imp));

        // -- Adiciona Bancos
        AdicionaDAO<Banco> daoBanco = new AdicionaDAO<>();

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
        Moeda real = new Moeda(null, "Real", "R$", TipoBandeira.BRASIL);
        Moeda dolar = new Moeda(null, "Dólar", "$", TipoBandeira.ESTADOS_UNIDOS);
        Moeda guarani = new Moeda(null, "Guarani", "Gs", TipoBandeira.PARAGUAI);

        AdicionaDAO<Moeda> daoMoeda = new AdicionaDAO<Moeda>();
        daoMoeda.adiciona(real);
        daoMoeda.adiciona(dolar);
        daoMoeda.adiciona(guarani);

        //Configuracao
        Configuracao configuracao = new Configuracao(null, null, dolar, TipoDeFormacaoDePreco.MARKUP, TipoDeCalculoDeCusto.ULTIMO_CUSTO);
        new AdicionaDAO<Configuracao>().adiciona(configuracao);

        // Conta
        // ---------------------------------------------------------------------
        Conta conta = new Conta(null, "C/C 1222", banco, real);
        new AdicionaDAO<Conta>().adiciona(conta);

        // Unidade De Medida Item
        // ---------------------------------------------------------------------
        UnidadeMedidaItem unidade = new UnidadeMedidaItem(null, "Unidade", "UN", 0);
        new AdicionaDAO<UnidadeMedidaItem>().adiciona(unidade);

        // IVA
        // ---------------------------------------------------------------------
        IVA iva = new IVA(null, new BigDecimal(10), "IVA 10%");
        new AdicionaDAO<IVA>().adiciona(iva);

        // Grupo Fiscal
        // ---------------------------------------------------------------------
        GrupoFiscal grupoFiscal = new GrupoFiscal(null, "IVA 10%", iva);
        new AdicionaDAO<GrupoFiscal>().adiciona(grupoFiscal);

        // Deposito
        // ---------------------------------------------------------------------
        Deposito deposito = new Deposito(null, "Depósito Exemplo");
        new AdicionaDAO<Deposito>().adiciona(deposito);

        // Item
        Item item = new Item(null, null, "Exemplo", null, TipoItem.MERCADORIA, null, null, true,
                grupoFiscal, unidade, null, null, null, null, null, null);
        new AdicionaDAO<Item>().adiciona(item);

        System.out.println("Dados criados com sucesso.");
    }

}
