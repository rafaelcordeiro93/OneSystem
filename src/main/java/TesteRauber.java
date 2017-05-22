
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Comanda;
import br.com.onesystem.domain.ItemDeComanda;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.valueobjects.TipoOperacao;
import br.com.onesystem.war.service.EstoqueService;
import java.util.List;

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

    public static void main(String[] args) throws DadoInvalidoException, NoSuchFieldException {

        ItemDeComanda item = new ArmazemDeRegistros<ItemDeComanda>(ItemDeComanda.class).find(new Long(6));
        Comanda comanda = new ArmazemDeRegistros<Comanda>(Comanda.class).find(new Long(6));

        List<SaldoDeEstoque> lista = new EstoqueService().buscaListaDeSaldoDe(item, comanda, TipoOperacao.VENDA);

        lista.forEach(System.out::println);

        System.out.println("Concluiu");

    }

}
