
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.ContaDAO;
import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.ConfiguracaoContabil;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.domain.builder.ConfiguracaoContabilBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.war.builder.ConfiguracaoContabilBV;
import br.com.onesystem.war.service.ConfiguracaoContabilService;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

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

        List<Cotacao> cotacaoLista = new CotacaoDAO().buscarCotacoes().naUltimaEmissao().porCotacaoBancaria().listaDeResultados();
        System.out.println(new ContaDAO().buscarContaW().comBanco().ePorMoedas(cotacaoLista.stream().map(c -> c.getConta().getMoeda()).collect(Collectors.toList())).getConsulta());
        List<Conta> contaComCotacao = new ContaDAO().buscarContaW().comBanco().ePorMoedas(cotacaoLista.stream().map(c -> c.getConta().getMoeda()).collect(Collectors.toList())).listaDeResultados();
        
        cotacaoLista.forEach(System.out::println);
        contaComCotacao.forEach(System.out::println);
        
        System.out.println("Concluiu");
    }

}
