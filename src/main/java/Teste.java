
import br.com.onesystem.domain.Item;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.ImportadorRUC;
import br.com.onesystem.util.ImpressoraDeRelatorio;
import br.com.onesystem.util.ImpressoraDeRelatorioConsole;
import br.com.onesystem.war.service.ItemService;
import java.util.List;

public class Teste {
    
    public static void main(String[] args) throws DadoInvalidoException {
        
        ItemService service = new ItemService();
//        ImpressoraDeRelatorio imp = new ImpressoraDeRelatorio();
   
        
        List<Item> lista = service.buscarItems();
        for(Item item : lista){
            System.out.println(item.getId() +  " - " + item.getNome() + " - " + item.getSaldo() );
        }



//     imp.imprimir(lista, "Relatorio Estoque");
        
    }
}
