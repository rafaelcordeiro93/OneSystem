
import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.CobrancaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.FiltroDeRelatorio;
import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.builder.ModeloDeRelatorioBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.TipoDeBusca;
import br.com.onesystem.valueobjects.TipoFormatacaoNumero;
import br.com.onesystem.valueobjects.TipoRelatorio;
import br.com.onesystem.valueobjects.Totalizador;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rafael Fernando Rauber
 */
public class TesteRauber {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, DadoInvalidoException {

        BundleUtil msg = new BundleUtil();
        AdicionaDAO<ModeloDeRelatorio> modeloDeRelatorioDAO = new AdicionaDAO<ModeloDeRelatorio>();

//      //Relatório de Contas a Pagar.
        //========================================================
        ModeloDeRelatorio relatorioDeContasAPagar = new ModeloDeRelatorioBuilder()
                .comNome(TipoRelatorio.CONTAS.getNome())
                .comTipoRelatorio(TipoRelatorio.CONTAS)
                .construir();
        
        //Filtros
        Coluna colOperacaoFinanceira = new Coluna(msg.getLabel("Operacao_Financeira"), "Cobranca", "operacaoFinanceira", Cobranca.class, OperacaoFinanceira.class);
        FiltroDeRelatorio filtro = new FiltroDeRelatorio(null, colOperacaoFinanceira, TipoDeBusca.IGUAL_A);
        filtro.add(OperacaoFinanceira.SAIDA);

        relatorioDeContasAPagar.addFiltro(filtro);

        //Colunas Exibidas
        Coluna pessoa = new Coluna(msg.getLabel("Nome") + "(" + msg.getLabel("Pessoa") + ")", msg.getLabel("Pessoa"), "pessoa", "nome", Pessoa.class, TipoRelatorio.class);
        pessoa.setTamanho(30);

        relatorioDeContasAPagar.addColunaExibida(new Coluna(msg.getLabel("Id"), msg.getLabel("Cobranca"), "id", Cobranca.class, Long.class));
        relatorioDeContasAPagar.addColunaExibida(pessoa);
        relatorioDeContasAPagar.addColunaExibida(new Coluna(msg.getLabel("Emissao"), msg.getLabel("Cobranca"), "emissao", Cobranca.class, Date.class));
        relatorioDeContasAPagar.addColunaExibida(new Coluna(msg.getLabel("Vencimento"), msg.getLabel("Cobranca"), "vencimento", Cobranca.class, Date.class));
        relatorioDeContasAPagar.addColunaExibida(new Coluna(msg.getLabel("Valor"), msg.getLabel("Cobranca"), "valor", Cobranca.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.SUM));

        modeloDeRelatorioDAO.adiciona(relatorioDeContasAPagar);

    }
}
