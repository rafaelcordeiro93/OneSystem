
import br.com.onesystem.domain.Item;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.reportTemplate.BalancoFisico;
import br.com.onesystem.util.ImportadorRUC;
import br.com.onesystem.util.ImpressoraDeRelatorio;
import br.com.onesystem.util.ImpressoraDeRelatorioConsole;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.ItemService;
import br.com.onesystem.war.service.MoedaService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.sf.jasperreports.engine.JRException;

public class Teste {
    
    public static void main(String[] args) throws DadoInvalidoException, JRException {
        
        ItemService service = new ItemService();
        ConfiguracaoService moeda = new ConfiguracaoService();
//        ImpressoraDeRelatorio imp = new ImpressoraDeRelatorio();
  List<BalancoFisico> balanco = new ArrayList<BalancoFisico>();
        List<Item> lista = service.buscarItems();
        for(Item item : lista){
          balanco.add(new BalancoFisico(item, moeda.buscar().getMoedaPadrao(),service.custoMedio(item), new Date()));
        }
        ImpressoraDeRelatorioConsole imp = new ImpressoraDeRelatorioConsole();
        
        imp.imprimir(balanco, "RelatorioDeBalancoFisico");
        imp.gerarPDF("RelatorioDeBalancoFisico");
        imp.visualizar();
        
        for(BalancoFisico balancoFisico : balanco){
            System.out.println(balancoFisico.getItem().getId() + " - " + balancoFisico.getItem().getNome() +  " - " + balancoFisico.getItem().getSaldo()+ " - " + balancoFisico.getCustoMedio() + " - " + balancoFisico.getCustoTotal());
        }
        


//     imp.imprimir(lista, "Relatorio Estoque");
        
    }
}
