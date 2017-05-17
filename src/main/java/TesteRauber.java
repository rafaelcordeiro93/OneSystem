
import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.EstoqueDAO;
import br.com.onesystem.dao.OrcamentoDAO;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Janela;
import br.com.onesystem.domain.Modulo;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.domain.Privilegio;
import br.com.onesystem.domain.builder.ItemBuilder;
import br.com.onesystem.domain.builder.NotaEmitidaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.valueobjects.EstadoDeOrcamento;
import br.com.onesystem.valueobjects.OperacaoFisica;
import br.com.onesystem.valueobjects.TipoOperacao;
import br.com.onesystem.war.service.ConfiguracaoEstoqueService;
import br.com.onesystem.war.service.EstoqueService;
import br.com.onesystem.war.service.OrcamentoService;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        try {
            ConfiguracaoEstoqueService serv = new ConfiguracaoEstoqueService();
            ConfiguracaoEstoque conf = serv.buscar();
            Item item = new ItemBuilder().comId(new Long(1)).construir();
            Nota nota = new NotaEmitidaBuilder().comId(new Long(1)).construir();
            List<SaldoDeEstoque> saldoDeEstoque = new ArrayList<SaldoDeEstoque>();
            List<Estoque> estoque = new EstoqueDAO().buscarEstoques().porItem(item).porContaDeEstoque(conf.getContaDeEstoqueEmpresa()).
                    porNotaDeOrigem(nota).porTipoDeOperacaoDeNota(TipoOperacao.DEVOLUCAO_CLIENTE).listaDeResultados();

            for (Estoque e : estoque) {
                boolean operacao = false;
                for (SaldoDeEstoque saldo : saldoDeEstoque) {
                    if (e.getDeposito().getId().equals(saldo.getDeposito().getId())) {
                        if (e.getOperacaoDeEstoque().getOperacaoFisica().equals(OperacaoFisica.ENTRADA)) {
                            saldo.setSaldo(saldo.getSaldo().add(e.getQuantidade()));
                            operacao = true;
                        } else if (e.getOperacaoDeEstoque().getOperacaoFisica().equals(OperacaoFisica.SAIDA)) {
                            saldo.setSaldo(saldo.getSaldo().subtract(e.getQuantidade()));
                            operacao = true;
                        }
                    }
                }
                if (!operacao) {
                    saldoDeEstoque.add(new SaldoDeEstoque((new Long(saldoDeEstoque.size() + 1)), e.getDeposito(), e.getQuantidade()));
                }
            }
            
            saldoDeEstoque.forEach(System.out::println);
            

        } catch (DadoInvalidoException ex) {
            Logger.getLogger(EstoqueService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
