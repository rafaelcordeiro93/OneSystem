
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Item;
import br.com.onesystem.war.service.AjusteDeEstoqueService;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

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
        Item item = new Item(new Long(1));
        AjusteDeEstoque buscaUltimoAjuste = new AjusteDeEstoqueService().buscaUltimoAjuste(item);
        System.out.println(buscaUltimoAjuste);
    }
    
}
