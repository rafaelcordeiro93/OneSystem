
import br.com.onesystem.util.DateUtil;
import com.ibm.icu.util.Calendar;
import java.util.Date;

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

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 31);
        System.out.println("" + c.getTime());
        long dias = new DateUtil().getDifererencaDeDiasEntreDatas(c.getTime(), new Date());
        
        System.out.println("Dias: " + dias);
        
    }

}
