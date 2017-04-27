
import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemOrcado;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
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

    public static void main(String[] args) throws DadoInvalidoException {

        FormaDeRecebimento forma = new ArmazemDeRegistros<>(FormaDeRecebimento.class).find(new Long(1));
        Pessoa pessoa = new ArmazemDeRegistros<>(Pessoa.class).find(new Long(1));
        ListaDePreco listaDePreco = new ArmazemDeRegistros<>(ListaDePreco.class).find(new Long(1));
        Item item = new ArmazemDeRegistros<>(Item.class).find(new Long(1));
        ItemOrcado itemOrcado = new ItemOrcado(null, item, BigDecimal.TEN, BigDecimal.ONE);
        List<ItemOrcado> itens = Arrays.asList(itemOrcado);
        
        Orcamento o1 = new Orcamento();
  
        new AdicionaDAO<>().adiciona(o1);
     
    }

}
