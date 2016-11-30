
import br.com.onesystem.domain.Item;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.war.service.EstoqueService;
import java.util.List;

public class Teste {

    public static void main(String[] args) throws DadoInvalidoException {

        List<SaldoDeEstoque> listaDeResultados = new EstoqueService().buscaSaldoDeEstoque(new Item(new Long(1)));
        for (SaldoDeEstoque e : listaDeResultados) {
            System.out.println("E: " + e);
        }

    }
}
