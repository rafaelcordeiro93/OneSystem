
import br.com.onesystem.dao.PrecoDeItemDAO;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.PrecoDeItem;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
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

    public static void main(String[] args) throws EDadoInvalidoException, DadoInvalidoException {
        List<PrecoDeItem> precos = new PrecoDeItemDAO().porItem(new Item(new Long(1)))
                .naMaiorEmissao(new Date()).eNaoExpirado().listaDeResultados();
        precos.forEach(System.out::println);
    }
}
