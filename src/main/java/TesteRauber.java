
import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Cotacao;
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

    public static void main(String[] args) {
        
        List<Cotacao> listaCotacao = new CotacaoDAO().buscarCotacoes().naMaiorEmissao(new Date()).listaDeResultados();
        
        listaCotacao.forEach(System.out::println);
        
        System.out.println("Concluiu");
    }

}
