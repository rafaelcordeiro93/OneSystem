
import br.com.onesystem.dao.ArmazemDeRegistrosConsole;
import br.com.onesystem.domain.CobrancaVariavel;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.sf.jasperreports.engine.JRException;
import org.reflections.Reflections;

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

    public static void main(String[] args) throws EDadoInvalidoException, DadoInvalidoException, JRException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        System.out.println(CobrancaVariavel.class.getSuperclass());
    }
}
