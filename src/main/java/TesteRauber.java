
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.MargemDAO;
import br.com.onesystem.domain.Margem;
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

    public static void main(String[] args) {

        try{
        MargemDAO dao =  new MargemDAO();
        
//        Margem m = dao.buscarMargemW().porId(new Long(45341)).resultado();
        
//        System.out.println("Margem: " + m);
        }catch(NoResultException nre){
            System.out.println("teste sadfsdfa");
        }
                
        
    }

}
