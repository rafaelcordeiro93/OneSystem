
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.EstoqueDAO;
import br.com.onesystem.dao.ItemDeNotaDAO;
import br.com.onesystem.dao.NotaEmitidaDAO;
import br.com.onesystem.domain.Comanda;
import br.com.onesystem.domain.Condicional;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeComanda;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.valueobjects.TipoOperacao;
import br.com.onesystem.war.service.ConfiguracaoEstoqueService;
import br.com.onesystem.war.service.EstoqueService;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;

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

        Item item = new ArmazemDeRegistros<Item>(Item.class).find(new Long(1));
        Condicional condicional = new ArmazemDeRegistros<Condicional>(Condicional.class).find(new Long(7));

        ConfiguracaoEstoqueService serv = new ConfiguracaoEstoqueService();
        ConfiguracaoEstoque conf = serv.buscar();

//        List<NotaEmitida> notas = new NotaEmitidaDAO().porCondicional(condicional).listaDeResultados(); 
        condicional.getNotasEmitidas().forEach(System.out::println);

        List<ItemDeNota> itensDeNotas = new ItemDeNotaDAO().buscarItens().porNotasEmitidas(condicional.getNotasEmitidas()).porItem(item)
                .porNaoCancelado().listaDeResultados();

        itensDeNotas.forEach(System.out::println);

        System.out.println("Concluiu: " + itensDeNotas.stream().map(ItemDeNota::getQuantidade).reduce(BigDecimal.ZERO, BigDecimal::add));

    }

}
