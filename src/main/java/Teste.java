
import br.com.onesystem.dao.ArmazemDeRegistrosNaMemoria;
import br.com.onesystem.dao.ArmazemDeRegistrosConsole;
import br.com.onesystem.domain.Movimento;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.util.GerenciadorDeImpressoraDeTexto;
import br.com.onesystem.util.MatrixPrinter_OLD;
import br.com.onesystem.util.StringAlignUtils;
import br.com.onesystem.util.StringAlignUtils.Alignment;
import br.com.onesystem.war.view.selecao.SelecaoItemView;
import javax.inject.Named;
import org.apache.commons.lang.StringUtils;

@Named
public class Teste {

    public static void main(String[] args) {

        NotaEmitida titulo = new ArmazemDeRegistrosConsole<NotaEmitida>(NotaEmitida.class).find(new Long(2));
        GerenciadorDeImpressoraDeTexto gerenciador = new GerenciadorDeImpressoraDeTexto("atendimento.json", Nota.class, titulo);

        gerenciador.exibirNoConsole();

//        gerenciador.imprimir("\\\\localhost\\epson");
        System.exit(0);

    }
}
