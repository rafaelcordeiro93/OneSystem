
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Recebimento;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import net.sf.jasperreports.engine.JRException;

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

        Recebimento recebimento = new ArmazemDeRegistros<>(Recebimento.class).find(new Long(1));
        recebimento.getTipoDeCobranca().forEach(t -> System.out.println(t.getCobranca()));
        

    }
}
