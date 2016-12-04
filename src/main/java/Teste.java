
import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.ConfiguracaoDAO;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Janela;
import br.com.onesystem.domain.Modulo;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.war.service.EstoqueService;
import java.util.List;

public class Teste {

    public static void main(String[] args) throws DadoInvalidoException {

        AdicionaDAO<Janela> daoJanela = new AdicionaDAO<>();
        Modulo m4 = new Modulo(null, "Preferências");
        Janela j25 = new Janela(null, "Configuração", "configuracao.xhtml", m4);
        
        daoJanela.adiciona(j25);
    }
}
