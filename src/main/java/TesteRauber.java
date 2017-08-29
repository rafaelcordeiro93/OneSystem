
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.PrecoDeItemDAO;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.LayoutDeImpressao;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.domain.PrecoDeItem;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ImpressoraDeRelatorio;
import br.com.onesystem.util.ImpressoraDeLayoutConsole;
import br.com.onesystem.valueobjects.TipoLayout;
import br.com.onesystem.war.service.LayoutDeImpressaoService;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;

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

    public static void main(String[] args) throws EDadoInvalidoException, DadoInvalidoException, JRException {

        Titulo titulo = new ArmazemDeRegistros<>(Titulo.class).find(new Long(5));
        Titulo titulon = new ArmazemDeRegistros<>(Titulo.class).find(new Long(6));
        
        LayoutDeImpressao layout = new LayoutDeImpressaoService().getLayoutPorTipoDeLayout(TipoLayout.TITULO);
        
        new ImpressoraDeLayoutConsole(Arrays.asList(titulo,titulon), layout).visualizar();

    }
}
