
import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.builder.AjusteDeEstoqueBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.List;

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
        
       List<Item> listaTodosOsItens = new ArmazemDeRegistros<Item>(Item.class).listaTodosOsRegistros();
       List<Deposito> listaTodosOsDepositos = new ArmazemDeRegistros<Deposito>(Deposito.class).listaTodosOsRegistros();
       
        System.out.println("Itens: " + listaTodosOsItens.size());
        System.out.println("Deposito: " + listaTodosOsDepositos.size());
        
        AjusteDeEstoque n = new AjusteDeEstoqueBuilder().comObservacao("lalalalalala").comQuantidade(new BigDecimal(4))
                .comItem(listaTodosOsItens.get(0)).comDeposito(listaTodosOsDepositos.get(0)).construir();

        new AdicionaDAO<AjusteDeEstoque>().adiciona(n);


    }
}
