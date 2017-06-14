
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.EstoqueDAO;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.war.service.ConfiguracaoEstoqueService;
import java.util.Date;
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

    public static void main(String[] args) throws DadoInvalidoException {

        ConfiguracaoEstoqueService serv = new ConfiguracaoEstoqueService();
        ConfiguracaoEstoque conf = serv.buscar();
        Item item = new ArmazemDeRegistros<Item>(Item.class).find(new Long(1));

        EstoqueDAO dao = new EstoqueDAO().porItem(item).porEmissao(new Date()).porNaoCancelado().porContaDeEstoque(conf.getContaDeEstoqueEmpresa())
                .porEstoqueAlterado();

        System.out.println("Consulta" + dao.getConsulta());
        
        dao.getParametros().forEach((k,v) -> {
        System.out.println(k + " - " + v);} );
        
        List<Estoque> list = dao.listaResultados();
        
        list.forEach(System.out::println);
        
        System.out.println("Concluiu");
    }

}
