
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

        Condicional titulo = new ArmazemDeRegistrosConsole<Condicional>(Condicional.class).find(new Long(1));
        ImpressoraDeLayoutTexto gerenciador = new ImpressoraDeLayoutTexto("condicional.json", Condicional.class, titulo);

        gerenciador.exibirNoConsole();

//        gerenciador.imprimir("\\\\localhost\\epson");
        System.exit(0);

    }
}
