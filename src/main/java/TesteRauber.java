
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.TipoFormaPagRec;
import java.math.BigDecimal;



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
        
        try {
            Titulo t = new Titulo(null, null, null, BigDecimal.ZERO, BigDecimal.ZERO, null, OperacaoFinanceira.SEM_ALTERACAO, TipoFormaPagRec.A_PRAZO, null, null, null, null, null, null, null, Boolean.TRUE);
        } catch (DadoInvalidoException ex) {
            ex.printConsole();
        }
        
    }

}
