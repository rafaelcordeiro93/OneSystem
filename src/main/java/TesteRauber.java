
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Baixa;
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
         List<Baixa> resultados = new ArmazemDeRegistros<Baixa>(Baixa.class).listaTodosOsRegistros();

    }
    
}
