
import br.com.onesystem.domain.Banco;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.war.builder.BancoBV;

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

        BancoBV bv = new BancoBV();
        bv.setNome("testando");
        
        BuilderView b = (BuilderView) bv;
        Banco construir = (Banco) b.construir();
        System.out.println(construir.getNome());
    }

}
