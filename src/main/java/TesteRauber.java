
import br.com.onesystem.dao.ContaDAO;
import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        Date emissao = new Date();

        List<Cotacao> cotacaoLista = new CotacaoDAO().buscarCotacoes().naEmissao(emissao).porCotacaoEmpresa().listaDeResultados();
        List<Conta> contaComCotacao = new ContaDAO().buscarContaW().comBanco().ePorMoedas(cotacaoLista.stream().map(c -> c.getConta().getMoeda()).collect(Collectors.toList())).listaDeResultados();

        contaComCotacao.forEach(System.out::println);

        System.out.println("Concluiu");
    }

}
