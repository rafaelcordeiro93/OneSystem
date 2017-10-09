
import br.com.onesystem.dao.Armazem;
import br.com.onesystem.dao.ArmazemDeRegistrosConsole;
import br.com.onesystem.domain.Movimento;
import br.com.onesystem.util.GerenciadorDeImpressoraDeTexto;
import br.com.onesystem.util.StringAlignUtils;
import br.com.onesystem.util.StringAlignUtils.Alignment;
import br.com.onesystem.war.view.selecao.SelecaoItemView;
import javax.inject.Named;
import org.apache.commons.lang.StringUtils;

@Named
public class Teste {

    public static void main(String[] args) {

        Movimento movimento = new ArmazemDeRegistrosConsole<Movimento>(Movimento.class).find(new Long(24));
        GerenciadorDeImpressoraDeTexto gerenciador = new GerenciadorDeImpressoraDeTexto("recebimento.json", Movimento.class, movimento);

        gerenciador.imprime();
     
        System.exit(0);
    }
}
