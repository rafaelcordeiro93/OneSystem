
import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.ConfiguracaoDAO;
import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Janela;
import br.com.onesystem.domain.Modulo;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.valueobjects.OperacaoFisica;
import br.com.onesystem.war.service.EstoqueService;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Teste {

    public static void main(String[] args) throws DadoInvalidoException {

          AjusteDeEstoque ajusteDeEstoq = new AjusteDeEstoque(null, "adsadasdsdasd", new Item(new Long(1)), BigDecimal.ONE, 
                new Deposito(new Long(1)), new Date(), OperacaoFisica.ENTRADA, null);
           Estoque estoque = new Estoque(null, ajusteDeEstoq.getItem(), ajusteDeEstoq.getQuantidade(), ajusteDeEstoq.getDeposito(), ajusteDeEstoq.getOperacao());
           
           AjusteDeEstoque ajuste = new AjusteDeEstoque(null, "adsadasdsdasd", new Item(new Long(1)), BigDecimal.ONE, 
                new Deposito(new Long(1)), new Date(), OperacaoFisica.ENTRADA, estoque);
           
           new AdicionaDAO<AjusteDeEstoque>().adiciona(ajuste);
     
    }
}
