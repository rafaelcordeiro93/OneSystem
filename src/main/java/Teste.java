
import br.com.onesystem.domain.Item;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.reportTemplate.BalancoFisico;
import br.com.onesystem.util.ImportadorRUC;
import br.com.onesystem.util.ImpressoraDeRelatorio;
import br.com.onesystem.util.ImpressoraDeRelatorioConsole;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.ItemService;
import br.com.onesystem.war.service.MoedaService;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.sf.jasperreports.engine.JRException;

public class Teste {

    public static void main(String[] args) throws DadoInvalidoException, JRException {

        PrintStream ps;
        try {
            // Abre o Stream da impressora
            FileOutputStream fos = new FileOutputStream("LPT1");
            ps = new PrintStream(fos);
            // Imprime o texto
            ps.print("testando a impressao");
            // Quebra linha
            ps.print("\f");
            // Fecha o Stream da impressora
            ps.close();
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

//     imp.imprimir(lista, "Relatorio Estoque");
    }
}
