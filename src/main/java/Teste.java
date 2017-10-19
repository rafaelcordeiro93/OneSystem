
import br.com.onesystem.dao.ArmazemDeRegistrosConsole;
import br.com.onesystem.dao.EstoqueDAO;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.valueobjects.OperacaoFisica;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Named;

@Named
public class Teste {

    public static void main(String[] args) {

        Item item = (Item) new ArmazemDeRegistrosConsole<Item>().daClasse(Item.class).find(new Long(1));
        ConfiguracaoEstoque configuracaoEstoque = (ConfiguracaoEstoque) new ArmazemDeRegistrosConsole<ConfiguracaoEstoque>().daClasse(ConfiguracaoEstoque.class).find(new Long(1));

        List<Deposito> depositos = new ArmazemDeRegistrosConsole<Deposito>().daClasse(Deposito.class).listaTodosOsRegistros();

        EstoqueDAO dao = new EstoqueDAO().porItem(item).ateEmissao(new Date()).porContaDeEstoque(configuracaoEstoque.getContaDeEstoqueEmpresa())
                .porEstoqueAlterado().porNaoCancelado();

        System.out.println("Consulta: " + dao.getConsulta());

        List<Estoque> estoque = dao.listaDeResultados();

        estoque.forEach(System.out::println);
        System.out.println("\n");

        List<SaldoDeEstoque> saldos = new ArrayList<>();

        // Itera sobre todos os estoques e é constituido por 3 fazes.
        for (Estoque e : estoque) {
            /* Fase 1
             * Instancia nova operacao - Variável de controle para saber se 
             * houve operação na fase 2. */
            boolean realizouOperacao = false;

            /* Fase 2 
             * Itera sobre os saldos de estoque. A primeira passagem não será efetuada
             * devido a lista de saldo estar vazia, assim será efetuada a fase 3 que tem o
             * objetivo de incluir um saldo dentro da lista de saldos.
             * 
             * Após a primeira passagem a lista de saldo não está mais vazia, e 
             * é realizado a primeira condição verificando se o id do depósito do estoque
             * é o mesmo do id do depósito no saldo. Se isso for verdadeiro, a segunda e 
             * terceita condição irão verificar se a operação será de entrada ou saída de estoque
             * e realizarão a operação. Por fim será setado a variável de controle realizouOperacao para verdadeiro
             * a fim de não criar um novo saldo, pois o mesmo já foi alterado.
             */
            for (SaldoDeEstoque saldo : saldos) {
                // Primeira condição
                if (e.getDeposito().getId().equals(saldo.getDeposito().getId())) {

                    // Segunda condição
                    if (e.getOperacaoDeEstoque().getOperacaoFisica().equals(OperacaoFisica.ENTRADA)) {
                        saldo.setSaldo(saldo.getSaldo().add(e.getQuantidade()));
                        realizouOperacao = true;
                    } // Terceira condição
                    else if (e.getOperacaoDeEstoque().getOperacaoFisica().equals(OperacaoFisica.SAIDA)) {
                        saldo.setSaldo(saldo.getSaldo().subtract(e.getQuantidade()));
                        realizouOperacao = true;
                    }
                }
            }

            /* Fase 3
             * Cria um saldo novo se a operação não foi realizada devido a inexistência de um saldo
             * com o mesmo depósito do elemento de Estoque.
             * Realiza somente uma comparação para verifica se a operação fisica deve ser de saída ou de entrada.
             */
            if (!realizouOperacao) {
                BigDecimal saldo;
                if (e.getOperacaoDeEstoque().getOperacaoFisica().equals(OperacaoFisica.SAIDA)) {
                    saldo = BigDecimal.ZERO.subtract(e.getQuantidade());
                } else {
                    saldo = e.getQuantidade();
                }
                saldos.add(new SaldoDeEstoque((new Long(saldos.size() + 1)), e.getDeposito(), saldo));
            }

        }

        System.out.println("\n");
        
        saldos.forEach(System.out::println);
        
        System.exit(0);

    }

}
