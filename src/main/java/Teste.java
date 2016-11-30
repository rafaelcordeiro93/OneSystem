
import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.EstoqueDAO;
import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.builder.AjusteDeEstoqueBuilder;
import br.com.onesystem.domain.builder.EstoqueBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.List;
import org.primefaces.event.SelectEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rafael
 */
public class Teste {

    public static void main(String[] args) throws DadoInvalidoException {
        
    // List<Item> listaTodosOsItens = new ArmazemDeRegistros<Item>(Item.class).listaTodosOsRegistros();
     //  List<Deposito> listaTodosOsDepositos = new ArmazemDeRegistros<Deposito>(Deposito.class).listaTodosOsRegistros();
        List<Estoque> listaTodosOsEstoque = new ArmazemDeRegistros<Estoque>(Estoque.class).listaTodosOsRegistros();
        Long  tt = new Long(1);
        Deposito deposito = new Deposito( tt , "dp1");
        
      //  System.out.println("Itens: " + listaTodosOsItens.size());
        //System.out.println("Deposito: " + listaTodosOsDepositos.size());
         //System.out.println("Estoque: " + listaTodosOsEstoque.size());
        
        //Estoque consulta =  new EstoqueDAO().buscarEstoques().wSaldo().resultadoUnico();
          // System.out.println("Estoque : " + listaTodosOsEstoque);
       //  System.out.println("Estoque listado: " + consulta);
    }
}
