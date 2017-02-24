
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
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

        String msg = "org.postgresql.util.PSQLException: ERROR: update or delete on table \"contadeestoque\" violates foreign key constraint \"fk_k6mib7gvwprj9rtqyffml5dct\" on table \"configuracaoestoque\"";

        System.out.println("msg : " + msg.lastIndexOf("on table"));
        System.out.println("msg 2: " + msg.length());
        
        System.out.println("subs: " + msg.substring(msg.lastIndexOf("on table")+8, msg.length()));
    }

}
