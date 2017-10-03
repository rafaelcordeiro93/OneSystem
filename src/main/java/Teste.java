
import br.com.onesystem.dao.ArmazemDeRegistrosConsole;
import br.com.onesystem.domain.Movimento;
import br.com.onesystem.util.GerenciadorDeImpressoraDeTexto;
import br.com.onesystem.util.StringAlignUtils;
import br.com.onesystem.util.StringAlignUtils.Alignment;
import javax.inject.Named;
import org.apache.commons.lang.StringUtils;

@Named
public class Teste {

    public static void main(String[] args) {

////        System.out.println(Cobranca.class.isAssignableFrom(Titulo.class));
//        String sampleText = "Olá";
//
//        StringAlignUtils util = new StringAlignUtils(50, Alignment.CENTER);
//        System.out.println(util.format(sampleText));

        Movimento movimento = new ArmazemDeRegistrosConsole<Movimento>(Movimento.class).find(new Long(24));
        GerenciadorDeImpressoraDeTexto gerenciador = new GerenciadorDeImpressoraDeTexto("recebimento.json", Movimento.class, movimento);

        gerenciador.paraArquivo();
    }
}
