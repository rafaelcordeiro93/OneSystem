
import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.LayoutDeImpressao;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.domain.Recebimento;
import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.builder.MarcaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.ImpressoraDeLayout;
import br.com.onesystem.util.ImpressoraDeLayoutConsole;
import br.com.onesystem.valueobjects.TipoLayout;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.CotacaoService;
import br.com.onesystem.war.service.LayoutDeImpressaoService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

        Marca m = new MarcaBuilder().comNome("Lacoste").construir();

        new AdicionaDAO<>().adiciona(m);

    }
}
