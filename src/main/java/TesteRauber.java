
import br.com.onesystem.dao.CreditoDAO;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.war.service.CreditoService;

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

    public static void main(String[] args) throws DadoInvalidoException, NoSuchFieldException {

        CreditoService s = new CreditoService();
        
        s.buscarSaldo(null);
        
    }

}
