
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.MargemDAO;
import br.com.onesystem.domain.Margem;
import br.com.onesystem.domain.builder.GrupoDeMargemBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.DateUtil;
import com.ibm.icu.util.Calendar;
import java.util.Date;
import javax.persistence.NoResultException;

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

        try {
            Margem m = new ArmazemDeRegistros<Margem>(Margem.class).find(new GrupoDeMargemBuilder().comID(new Long(1)).construir());

            System.out.println("Margem: " + m);
        } catch (NoResultException nre) {
            System.out.println("teste sadfsdfa");
        }

    }

}
