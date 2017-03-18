
import br.com.onesystem.domain.Item;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.reportTemplate.BalancoFisico;
import br.com.onesystem.util.GenericLayout;
import br.com.onesystem.util.ImportadorRUC;
import br.com.onesystem.util.ImpressoraDeRelatorio;
import br.com.onesystem.util.ImpressoraDeRelatorioConsole;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.ItemService;
import br.com.onesystem.war.service.MoedaService;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;

public class Teste {

    public static void main(String[] args) throws DadoInvalidoException, JRException, FileNotFoundException {

      
        Gson gson= new Gson();
//        
//        
//        GenericLayout gn = new GenericLayout("coluna", 2, 3);
//        
//        String gson2 = gson.toJson(gn);
//        
//        System.out.println(gson2);
     
        BufferedReader bf = new BufferedReader(new FileReader("d:\\a.json"));
        GenericLayout g = gson.fromJson(bf, GenericLayout.class);
        System.out.println(g.toString());

//     imp.imprimir(lista, "Relatorio Estoque");
    }
}
