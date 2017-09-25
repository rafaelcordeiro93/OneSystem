
import br.com.onesystem.dao.ArmazemDeRegistrosConsole;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.reportTemplate.CaminhoDeClasse;
import br.com.onesystem.util.LeitoraDeCaminhoDeClassesJSON;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
    
    public static void main(String[] args) throws EDadoInvalidoException, DadoInvalidoException, JRException {

        List<CaminhoDeClasse> listaLayout = new LeitoraDeCaminhoDeClassesJSON().getCaminhos(Pessoa.class);
        
        listaLayout.forEach(System.out::println);
        
    }
}
