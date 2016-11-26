
import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.builder.BancoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rafael
 */
public class Testando {

    public static void main(String[] args) throws DadoInvalidoException {


        Banco bancod = new BancoBuilder().comCodigo("fasdf")
                .comNome("fafd")
                .comSite("fasdfds")
                .construir();
    }

}
