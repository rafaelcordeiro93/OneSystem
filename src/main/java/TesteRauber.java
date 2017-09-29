
import br.com.onesystem.domain.Nota;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.war.view.selecao.SelecaoCartaoView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
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

    public static void main(String[] args) throws EDadoInvalidoException, DadoInvalidoException, JRException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        System.out.println(SelecaoCartaoView.class.getSimpleName());

    }
}
