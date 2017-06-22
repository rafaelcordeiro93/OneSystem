
import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.EstoqueDAO;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.IVA;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.domain.builder.ConfiguracaoContabilBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoItem;
import br.com.onesystem.war.builder.ConfiguracaoContabilBV;
import br.com.onesystem.war.service.ConfiguracaoContabilService;
import java.math.BigDecimal;
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

        // Unidade De Medida Item
        // ---------------------------------------------------------------------
        UnidadeMedidaItem unidade = new ArmazemDeRegistros<UnidadeMedidaItem>(UnidadeMedidaItem.class).find(new Long(1));
      

        // IVA
        // ---------------------------------------------------------------------
        IVA iva = new ArmazemDeRegistros<IVA>(IVA.class).find(new Long(1));
       

        // Grupo Fiscal
        // ---------------------------------------------------------------------
        GrupoFiscal grupoFiscal = new ArmazemDeRegistros<GrupoFiscal>(GrupoFiscal.class).find(new Long(1));
       

        // Deposito
        // ---------------------------------------------------------------------
        Deposito deposito = new ArmazemDeRegistros<Deposito>(Deposito.class).find(new Long(1));
        
        
        
        
//        
//        List<Cotacao> cotacaoLista = new CotacaoDAO().buscarCotacoes().naUltimaEmissao().porCotacaoBancaria().listaDeResultados();
//        System.out.println(new ContaDAO().buscarContaW().comBanco().ePorMoedas(cotacaoLista.stream().map(c -> c.getConta().getMoeda()).collect(Collectors.toList())).getConsulta());
//        List<Conta> contaComCotacao = new ContaDAO().buscarContaW().comBanco().ePorMoedas(cotacaoLista.stream().map(c -> c.getConta().getMoeda()).collect(Collectors.toList())).listaDeResultados();
//        
//        cotacaoLista.forEach(System.out::println);
//        contaComCotacao.forEach(System.out::println);
//        
//        System.out.println("Concluiu");
    }

}