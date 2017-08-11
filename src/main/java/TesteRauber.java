
import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.CobrancaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.FiltroDeRelatorio;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Marca;
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

            ModeloDeRelatorio relatorioDeItem = new ModeloDeRelatorioBuilder()
                .comNome(new BundleUtil().getLabel("Relatorio_de_Balanco_Fisico"))
                .comTipoRelatorio(TipoRelatorio.ITEM)
                .construir();

        //Colunas Exibidas
        String itemStr = msg.getLabel("Item");

        relatorioDeItem.addColunaExibida(new Coluna(msg.getLabel("Id"), itemStr, "id", Item.class, Long.class));
        relatorioDeItem.addColunaExibida(new Coluna(msg.getLabel("Nome"), itemStr, "nome", Item.class, String.class));
        relatorioDeItem.addColunaExibida(new Coluna(msg.getLabel("Saldo"), itemStr, "saldo", Item.class, BigDecimal.class));
        relatorioDeItem.addColunaExibida(new Coluna(msg.getLabel("Preco"), itemStr, "preco", Item.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.SUM));
        relatorioDeItem.addColunaExibida(new Coluna(msg.getLabel("Preco_Total"), itemStr, "precoTotal", Item.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.SUM));
        relatorioDeItem.addColunaExibida(new Coluna(msg.getLabel("Custo_Medio"), itemStr, "custoMedio", Item.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.AVERAGE));
        relatorioDeItem.addColunaExibida(new Coluna(msg.getLabel("Ultimo_Custo"), itemStr, "ultimoCusto", Item.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.SUM));
        relatorioDeItem.addColunaExibida(new Coluna(msg.getLabel("Custo_Total"), itemStr, "custoTotal", Item.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.SUM));
        relatorioDeItem.addColunaExibida(new Coluna(msg.getLabel("Nome") + "(" + msg.getLabel("Marca") + ")", itemStr, "nome", Marca.class, String.class));

        modeloDeRelatorioDAO.adiciona(relatorioDeItem);


    }
}
