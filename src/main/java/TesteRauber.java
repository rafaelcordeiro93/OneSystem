
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Cobranca;
import java.io.IOException;
import java.lang.reflect.Method;
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

    public static void main(String[] args) throws Exception {

        String caminho = "cotacao.conta.moeda";
        
        Cobranca cobranca = new ArmazemDeRegistros<Cobranca>(Cobranca.class).find(new Long (1));

        Class c = Cobranca.class;
        Method m = c.getMethod("getCotacao", null);
        Object invoke = m.invoke(cobranca);
        

    }

}
