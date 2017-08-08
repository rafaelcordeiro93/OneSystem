
import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.CobrancaDAO;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.FiltroDeRelatorio;
import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.TipoDeBusca;
import br.com.onesystem.valueobjects.TipoRelatorio;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
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

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {

//        String ent = "ENTRADA";
//            Class<?> clazz = Class.forName("br.com.onesystem.valueobjects.OperacaoFinanceira");
//        
//        Enum v = Enum.valueOf((Class<? extends Enum>)clazz, ent);
//        
//        System.out.println(v);
        Enum o = OperacaoFinanceira.ENTRADA;
        System.out.println(o.getClass().getMethod("getNome", null).invoke(o, null));
//        o.getDeclaringClass().getEnumConstants()

//        List<Cobranca> listaDeResultados = new CobrancaDAO().operacaoFinanceira(o).listaDeResultados();
//      
//        listaDeResultados.forEach(System.out::println);
//        
//        try {
//            Coluna c = new Coluna("colunsdfa", "tabfsela", "nomesd", Pessoa.class, String.class);
//            Coluna c2 = new Coluna("operacaoFinanceira", "tdabela", "sobreNome", Cobranca.class, o.getClass());
//
//            FiltroDeRelatorio f = new FiltroDeRelatorio(null, c2, o,TipoDeBusca.IGUAL_A);
//
//            ModeloDeRelatorio m;
//            m = new ModeloDeRelatorio(null, "Modelo", TipoRelatorio.PESSOAS);
//            m.adicionaColunaExibida(c);
//            m.adicionaFiltro(f);
//
//            System.out.println("M: " + m);        List<Cobranca> listaDeResultados = new CobrancaDAO().operacaoFinanceira(o).listaDeResultados();
//      
//        listaDeResultados.forEach(System.out::println);
//        
//        try {
//            Coluna c = new Coluna("colunsdfa", "tabfsela", "nomesd", Pessoa.class, String.class);
//            Coluna c2 = new Coluna("operacaoFinanceira", "tdabela", "sobreNome", Cobranca.class, o.getClass());
//
//            FiltroDeRelatorio f = new FiltroDeRelatorio(null, c2, o,TipoDeBusca.IGUAL_A);
//
//            ModeloDeRelatorio m;
//            m = new ModeloDeRelatorio(null, "Modelo", TipoRelatorio.PESSOAS);
//            m.adicionaColunaExibida(c);
//            m.adicionaFiltro(f);
//
//            System.out.println("M: " + m);
//
//            new AdicionaDAO<>().adiciona(m);
//
//            System.out.println("Adicionado: " + m);
//
//        } catch (DadoInvalidoException ex) {
//            ex.printConsole();
//        }
//    }
//
//            new AdicionaDAO<>().adiciona(m);
//
//            System.out.println("Adicionado: " + m);
//
//        } catch (DadoInvalidoException ex) {
//            ex.printConsole();
//        }
    }
}
