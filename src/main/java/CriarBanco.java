
import br.com.onesystem.dao.SaldoDeEstoqueDAO;
import br.com.onesystem.domain.SaldoDeEstoque;
import br.com.onesystem.reportTemplate.SaldoEmDepositoTemplate;
import br.com.onesystem.util.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class CriarBanco {

    public static void main(String[] args) {
//
        try {

            List<SaldoEmDepositoTemplate> listaDeResultadosSoma = new SaldoDeEstoqueDAO().buscaSaldoDeCadaDeposito().groupByDepositoItem().listaDeDepositosSoma();
            listaDeResultadosSoma.forEach(System.out::println);

            System.out.println("Fim");

            System.exit(0);
        } catch (Exception die) {
            System.out.println(die.getMessage());
        }
    }

}
