
import br.com.onesystem.dao.ArmazemDeRegistrosNaMemoria;
import br.com.onesystem.dao.ArmazemDeRegistrosConsole;
import br.com.onesystem.domain.Movimento;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.Condicional;
import br.com.onesystem.util.ImpressoraDeLayoutTexto;
import br.com.onesystem.util.StringAlignUtils;
import br.com.onesystem.util.StringAlignUtils.Alignment;
import br.com.onesystem.war.view.selecao.SelecaoItemView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import org.apache.commons.lang.StringUtils;

@Named
public class Teste {

    public static void main(String[] args) {

        Movimento titulo = new ArmazemDeRegistrosConsole<Movimento>(Movimento.class).find(new Long(45));
        ImpressoraDeLayoutTexto gerenciador = new ImpressoraDeLayoutTexto("recebimento", Movimento.class, titulo);

        gerenciador.exibirNoConsole();

//        gerenciador.imprimir("\\\\localhost\\epson");
        System.exit(0);

    }
}
