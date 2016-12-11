
import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Despesa;
import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.IVA;
import br.com.onesystem.domain.Janela;
import br.com.onesystem.domain.Modulo;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.PessoaFisica;
import br.com.onesystem.domain.Privilegio;
import br.com.onesystem.domain.Receita;
import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.ClassificacaoFinanceira;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import br.com.onesystem.valueobjects.TipoPessoa;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class DadosIniciais {

    public static void main(String[] args) throws DadoInvalidoException {

        Pessoa pessoa = new PessoaFisica(null, null, null, "Rafael", TipoPessoa.PESSOA_FISICA, null, true, null, null, true, true, true, true, null, new Double(0), null, null, null, null, null, null, null);
        new AdicionaDAO<Pessoa>().adiciona(pessoa);

        GrupoDePrivilegio grupoDePrivilegio = new GrupoDePrivilegio(null, "Administrador");
        new AdicionaDAO<GrupoDePrivilegio>().adiciona(grupoDePrivilegio);

        // -- Adiciona Módulos
        AdicionaDAO<Modulo> daoModulo = new AdicionaDAO<>();

        Modulo m = new Modulo(null, "Arquivo");
        Modulo m1 = new Modulo(null, "Financeiro");
        Modulo m2 = new Modulo(null, "Câmbio");
        Modulo m3 = new Modulo(null, "Relatórios");
        Modulo m4 = new Modulo(null, "Preferências");
        Modulo m5 = new Modulo(null, "Login");
        Modulo m6 = new Modulo(null, "Estoque");

        daoModulo.adiciona(m);
        daoModulo.adiciona(m1);
        daoModulo.adiciona(m2);
        daoModulo.adiciona(m3);
        daoModulo.adiciona(m4);
        daoModulo.adiciona(m5);
        daoModulo.adiciona(m6);

        // -- Adiciona Módulos
        AdicionaDAO<Janela> daoJanela = new AdicionaDAO<>();

        Janela j = new Janela(null, "Cidade", "cidade.xhtml", m);
        Janela j1 = new Janela(null, "Pessoa", "pessoa.xhtml", m);
        Janela j2 = new Janela(null, "Receber Valores", "recebimento.xhtml", m1);
        Janela j3 = new Janela(null, "Pagar Valores", "pagamento.xhtml", m1);
        Janela j4 = new Janela(null, "Extrato Conta", "extratoConta.xhtml", m1);
        Janela j5 = new Janela(null, "Banco", "banco.xhtml", m1);
        Janela j6 = new Janela(null, "Moeda", "moeda.xhtml", m1);
        Janela j7 = new Janela(null, "Conta", "conta.xhtml", m1);
        Janela j8 = new Janela(null, "Tipo Receita", "tipoReceita.xhtml", m1);
        Janela j9 = new Janela(null, "Tipo Despesa", "tipoDespesa.xhtml", m1);
        Janela j10 = new Janela(null, "Tranferência de Lucro", "transferenciaDespesaProvisionada.xhtml", m1);
        Janela j11 = new Janela(null, "Grupo Financeiro", "grupoFinanceiro.xhtml", m1);
        Janela j12 = new Janela(null, "Despesa Provisionada", "despesaProvisionada.xhtml", m1);
        Janela j13 = new Janela(null, "Receita Provisionada", "receitaProvisionada.xhtml", m1);
        Janela j14 = new Janela(null, "Recepção", "recepcao.xhtml", m2);
        Janela j15 = new Janela(null, "Contrato de Câmbio", "contratoDeCambio.xhtml", m2);
        Janela j16 = new Janela(null, "Câmbio", "cambio.xhtml", m2);
        Janela j17 = new Janela(null, "Relatório de Conta A Pagar", "relatorioDeContaAPagar.xhtml", m3);
        Janela j18 = new Janela(null, "Relatório de Conta A Receber", "relatorioDeContaAReceber.xhtml", m3);
        Janela j19 = new Janela(null, "Relatório de Saldo de Conta", "relatorioDeSaldoDeConta.xhtml", m3);
        Janela j20 = new Janela(null, "Relatório de Recepção", "relatorioDeRecepcao.xhtml", m3);
        Janela j21 = new Janela(null, "Relatório de Contrato de Câmbio", "relatorioDeContratoDeCambio.xhtml", m3);
        Janela j22 = new Janela(null, "Relatório de Câmbio", "relatorioDeCambio.xhtml", m3);
        Janela j23 = new Janela(null, "Relatório de Saldo de Divisão de Lucro", "relatorioDeSaldoDeDivisaoDeLucro.xhtml", m3);
        Janela j24 = new Janela(null, "Usuário", "usuario.xhtml", m4);
        Janela j25 = new Janela(null, "Configuração", "configuracao.xhtml", m4);
        Janela j26 = new Janela(null, "Contrato De Câmbio", "contratoDeCambio.xhtml", m4);
        Janela j27 = new Janela(null, "Privilégio", "privilegio.xhtml", m4);
        Janela j28 = new Janela(null, "Login", "login.xhtml", m5);
        Janela j29 = new Janela(null, "Baixa", "baixa.xhtml", m1);
        Janela j30 = new Janela(null, "Ajuste de Estoque", "ajusteDeEstoque.xhtml", m6);
        Janela j31 = new Janela(null, "Conhecimento de Frete", "conhecimentoDeFrete.xhtml", m1);

        daoJanela.adiciona(j);
        daoJanela.adiciona(j1);
        daoJanela.adiciona(j2);
        daoJanela.adiciona(j3);
        daoJanela.adiciona(j4);
        daoJanela.adiciona(j5);
        daoJanela.adiciona(j6);
        daoJanela.adiciona(j7);
        daoJanela.adiciona(j8);
        daoJanela.adiciona(j9);
        daoJanela.adiciona(j10);
        daoJanela.adiciona(j11);
        daoJanela.adiciona(j12);
        daoJanela.adiciona(j13);
        daoJanela.adiciona(j14);
        daoJanela.adiciona(j15);
        daoJanela.adiciona(j16);
        daoJanela.adiciona(j17);
        daoJanela.adiciona(j18);
        daoJanela.adiciona(j19);
        daoJanela.adiciona(j20);
        daoJanela.adiciona(j21);
        daoJanela.adiciona(j22);
        daoJanela.adiciona(j23);
        daoJanela.adiciona(j24);
        daoJanela.adiciona(j25);
        daoJanela.adiciona(j26);
        daoJanela.adiciona(j27);
        daoJanela.adiciona(j28);
        daoJanela.adiciona(j29);
        daoJanela.adiciona(j30);

        // -- Adiciona Privilégios
        AdicionaDAO<Privilegio> daoPrivilegio = new AdicionaDAO<>();
        List<Privilegio> listaPrivilegios = Arrays.asList(
                new Privilegio(null, j, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j1, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j2, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j3, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j4, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j5, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j6, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j7, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j8, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j9, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j10, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j11, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j12, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j13, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j14, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j15, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j16, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j17, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j18, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j19, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j20, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j21, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j22, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j23, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j24, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j25, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j26, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j27, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j28, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j29, true, true, true, true, grupoDePrivilegio),
                new Privilegio(null, j30, true, true, true, true, grupoDePrivilegio));

        for (Privilegio p : listaPrivilegios) {
            daoPrivilegio.adiciona(p);
        }

//        Usuario usuario = new Usuario(null, pessoa, "rafa@gmail.com", "e10adc3949ba59abbe56e057f20f883e", grupoDePrivilegio, true);
//        new AdicionaDAO<Usuario>().adiciona(usuario);
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

        // -- Adiciona Despesa
        AdicionaDAO<Despesa> daoDespesa = new AdicionaDAO<>();

        daoDespesa.adiciona(new Despesa(null, "Ajuste de Saldo Inicial", aje));
        daoDespesa.adiciona(new Despesa(null, "PIS Sobre Faturamento", imp));
        daoDespesa.adiciona(new Despesa(null, "COFINS Sobre Faturamento", imp));
        daoDespesa.adiciona(new Despesa(null, "ICMS Sobre Mercadorias", imp));
        daoDespesa.adiciona(new Despesa(null, "ISS Sobre Servicos", imp));
        daoDespesa.adiciona(new Despesa(null, "DARF - Simples Sobre Faturamento", dept));
        daoDespesa.adiciona(new Despesa(null, "(-) Devolucoes de Vendas", dev));
        daoDespesa.adiciona(new Despesa(null, "Custo da Mercadoria Vendida", tot));
        daoDespesa.adiciona(new Despesa(null, "Custo dos Servicos Prestados", tot));
        daoDespesa.adiciona(new Despesa(null, "Comissoes Sobre Venda", tot));
        daoDespesa.adiciona(new Despesa(null, "Propaganda e Publicidade", depv));
        daoDespesa.adiciona(new Despesa(null, "Doacoes, Brindes e Bonificacoes", depv));
        daoDespesa.adiciona(new Despesa(null, "Desp. De Frete de Entregas", depv));
        daoDespesa.adiciona(new Despesa(null, "Salarios", depp));
        daoDespesa.adiciona(new Despesa(null, "Outras Comissoes", depp));
        daoDespesa.adiciona(new Despesa(null, "Gratificacoes", depp));
        daoDespesa.adiciona(new Despesa(null, "Estagiarios", depp));
        daoDespesa.adiciona(new Despesa(null, "13º Salario", depp));
        daoDespesa.adiciona(new Despesa(null, "Ferias", depp));
        daoDespesa.adiciona(new Despesa(null, "Vale Transporte", depp));
        daoDespesa.adiciona(new Despesa(null, "Assistencia a Empregados", depp));
        daoDespesa.adiciona(new Despesa(null, "Aviso Previo e Indeniz. Trabalhista", depp));
        daoDespesa.adiciona(new Despesa(null, "Treinamentos", depp));
        daoDespesa.adiciona(new Despesa(null, "FGTS", depp));
        daoDespesa.adiciona(new Despesa(null, "INSS", depp));
        daoDespesa.adiciona(new Despesa(null, "Pro-labore", depa));
        daoDespesa.adiciona(new Despesa(null, "Agua", depa));
        daoDespesa.adiciona(new Despesa(null, "Telefone", depa));
        daoDespesa.adiciona(new Despesa(null, "Energia Eletrica", depa));
        daoDespesa.adiciona(new Despesa(null, "Aluguel Comercial", depa));
        daoDespesa.adiciona(new Despesa(null, "Aluguel de Sistemas", depa));
        daoDespesa.adiciona(new Despesa(null, "Honorarios Profissionais", depa));
        daoDespesa.adiciona(new Despesa(null, "Combustiveis e Lubrificantes", depa));
        daoDespesa.adiciona(new Despesa(null, "Contribuicoes a Assoc. e Entidades", depa));
        daoDespesa.adiciona(new Despesa(null, "Contribuicoes Sindicais", depa));
        daoDespesa.adiciona(new Despesa(null, "Taxas Diversas", depa));
        daoDespesa.adiciona(new Despesa(null, "Pedagio", depa));
        daoDespesa.adiciona(new Despesa(null, "Cartorio", depa));
        daoDespesa.adiciona(new Despesa(null, "Correios", depa));
        daoDespesa.adiciona(new Despesa(null, "Dispendio c/ Alimentacao", depa));
        daoDespesa.adiciona(new Despesa(null, "Feiras, Congressos e Cursos", depa));
        daoDespesa.adiciona(new Despesa(null, "Passagens, Viagens e Estadias", depa));
        daoDespesa.adiciona(new Despesa(null, "Fotocopias e Impressoes", depa));
        daoDespesa.adiciona(new Despesa(null, "Fretes de Compras", tot));
        daoDespesa.adiciona(new Despesa(null, "IOF IMP. OPER. FINANCEIRA", depa));
        daoDespesa.adiciona(new Despesa(null, "Depreciacoes", depa));
        daoDespesa.adiciona(new Despesa(null, "Manutencao e Conservacao do Predio", depa));
        daoDespesa.adiciona(new Despesa(null, "Manutencao de Veiculos", depa));
        daoDespesa.adiciona(new Despesa(null, "Material de Expediente, Limpeza e Consumo", depa));
        daoDespesa.adiciona(new Despesa(null, "Servicos de Terc. Pessoa Fisica", depa));
        daoDespesa.adiciona(new Despesa(null, "Confraternizacoes", depa));
        daoDespesa.adiciona(new Despesa(null, "Outras Administrativas", depa));
        daoDespesa.adiciona(new Despesa(null, "MEDICINA DO TRABALHO", depp));
        daoDespesa.adiciona(new Despesa(null, "IRPJ", dept));
        daoDespesa.adiciona(new Despesa(null, "Contribuicao Social S/ Lucro Liquido", dept));
        daoDespesa.adiciona(new Despesa(null, "Outros Impostos Estaduais", dept));
        daoDespesa.adiciona(new Despesa(null, "Outros Impostos Municipais", dept));
        daoDespesa.adiciona(new Despesa(null, "Impostos e Taxas Diversas", dept));
        daoDespesa.adiciona(new Despesa(null, "Descontos Concedidos", tot));
        daoDespesa.adiciona(new Despesa(null, "Juros Pagos", tot));
        daoDespesa.adiciona(new Despesa(null, "Multas Pagas", depf));
        daoDespesa.adiciona(new Despesa(null, "Outras Financeiras", depf));
        daoDespesa.adiciona(new Despesa(null, "Tarifas Bancarias", tot));
        daoDespesa.adiciona(new Despesa(null, "Correcao Monetaria Paga", depf));
        daoDespesa.adiciona(new Despesa(null, "Encargos e Moras Fiscais", depf));
        daoDespesa.adiciona(new Despesa(null, "Despesas com Cobrancas", tot));
        daoDespesa.adiciona(new Despesa(null, "Taxa de Adm Cartao de Credito", tot));
        daoDespesa.adiciona(new Despesa(null, "Despesas Indedutiveis", depo));
        daoDespesa.adiciona(new Despesa(null, "Infracoes Fiscais", depo));
        daoDespesa.adiciona(new Despesa(null, "Prejuizos na Venda de Imobilizado", depo));
        daoDespesa.adiciona(new Despesa(null, "Sinistros", depo));
        daoDespesa.adiciona(new Despesa(null, "Perdas com Estoque", depo));
        daoDespesa.adiciona(new Despesa(null, "Outras nao Operacionais", depo));
        daoDespesa.adiciona(new Despesa(null, "Compras Normais", ope));
        daoDespesa.adiciona(new Despesa(null, "Compras em Consignacao", ope));
        daoDespesa.adiciona(new Despesa(null, "Compras de Ativo Imobilizado", opi));
        daoDespesa.adiciona(new Despesa(null, "Compras p/ Recebimento Futuro", ope));

        // -- Adiciona Receitas
        AdicionaDAO<Receita> daoReceita = new AdicionaDAO<>();

        daoReceita.adiciona(new Receita(null, "Ajuste de Saldo Inicial", aje));
        daoReceita.adiciona(new Receita(null, "Venda a Vista", imp));
        daoReceita.adiciona(new Receita(null, "Venda a Prazo", imp));

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
        Moeda real = new Moeda(null, "Real", "R$");
        Moeda dolar = new Moeda(null, "Dólar", "$");
        Moeda guarani = new Moeda(null, "Guarani", "Gs");

        AdicionaDAO<Moeda> daoMoeda = new AdicionaDAO<Moeda>();
        daoMoeda.adiciona(real);
        daoMoeda.adiciona(dolar);
        daoMoeda.adiciona(guarani);

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
    }

}
