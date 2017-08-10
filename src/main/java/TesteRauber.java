
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
import br.com.onesystem.valueobjects.SituacaoDeCobranca;
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

        ModeloDeRelatorio relatorioDeContasAReceber = new ModeloDeRelatorioBuilder()
                .comNome(new BundleUtil().getLabel("Relatorio_De_Contas_A_Receber"))
                .comTipoRelatorio(TipoRelatorio.CONTAS)
                .construir();

        //Filtros
        Coluna colOperacaoFinanceiracCAR = new Coluna(msg.getLabel("Operacao_Financeira"), "Cobranca", "operacaoFinanceira", Cobranca.class, OperacaoFinanceira.class);
        FiltroDeRelatorio filtroOFCAR = new FiltroDeRelatorio(null, colOperacaoFinanceiracCAR, TipoDeBusca.IGUAL_A);
        filtroOFCAR.add(OperacaoFinanceira.ENTRADA);

        Coluna colSituacaoDeCobrancaCAR = new Coluna(msg.getLabel("Situacao_de_Cobranca"), "Cobranca", "situacaoDeCobranca", Cobranca.class, SituacaoDeCobranca.class);
        FiltroDeRelatorio filtroSDCCAR = new FiltroDeRelatorio(null, colSituacaoDeCobrancaCAR, TipoDeBusca.IGUAL_A);
        filtroSDCCAR.add(SituacaoDeCobranca.ABERTO);

        relatorioDeContasAReceber.addFiltro(filtroOFCAR);
        relatorioDeContasAReceber.addFiltro(filtroSDCCAR);

        //Colunas Exibidas
        Coluna pessoaCAR = new Coluna(msg.getLabel("Nome") + "(" + msg.getLabel("Pessoa") + ")", msg.getLabel("Pessoa"), "pessoa", "nome", Pessoa.class, String.class);
        pessoaCAR.setTamanho(30);

        relatorioDeContasAReceber.addColunaExibida(new Coluna(msg.getLabel("Id"), msg.getLabel("Cobranca"), "id", Cobranca.class, Long.class));
        relatorioDeContasAReceber.addColunaExibida(pessoaCAR);
        relatorioDeContasAReceber.addColunaExibida(new Coluna(msg.getLabel("Emissao"), msg.getLabel("Cobranca"), "emissao", Cobranca.class, Date.class));
        relatorioDeContasAReceber.addColunaExibida(new Coluna(msg.getLabel("Vencimento"), msg.getLabel("Cobranca"), "vencimento", Cobranca.class, Date.class));
        relatorioDeContasAReceber.addColunaExibida(new Coluna(msg.getLabel("Valor"), msg.getLabel("Cobranca"), "valor", Cobranca.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.SUM));

        modeloDeRelatorioDAO.adiciona(relatorioDeContasAReceber);


    }
}
