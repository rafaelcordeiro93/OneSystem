
import br.com.onesystem.dao.BaixaDAO;
import br.com.onesystem.dao.MoedaDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.reportTemplate.ResumoDeMoeda;
import br.com.onesystem.util.ImpressoraDeRelatorioConsole;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;

public class Main {

    public static void main(String[] args) throws ParseException, DadoInvalidoException, JRException, IOException, Exception {

        BaixaDAO baixaDAO = new BaixaDAO();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Calendar dataAtual = Calendar.getInstance();
        dataAtual.setTime(sdf.parse("01/11/2015"));
        dataAtual.set(Calendar.HOUR_OF_DAY, 0);
        dataAtual.set(Calendar.MINUTE, 0);
        dataAtual.set(Calendar.SECOND, 0);
        Date dataInicial = dataAtual.getTime();
        dataAtual = Calendar.getInstance();
        dataAtual.set(Calendar.HOUR_OF_DAY, 23);
        dataAtual.set(Calendar.MINUTE, 59);
        dataAtual.set(Calendar.SECOND, 59);
        Date dataFinal = dataAtual.getTime();

        List<Baixa> resultados = baixaDAO.ePorEmissaoEntre(dataInicial, dataFinal).eSaida().orderByMoeda().listaDeResultados();

        System.out.println("Res: " + resultados.size());

        List<Moeda> listaMoeda = new MoedaDAO().listaDeResultados();

        List<ResumoDeMoeda> resumo = new ArrayList<ResumoDeMoeda>();

        for (Moeda moeda : listaMoeda) {
            BigDecimal valor = new BigDecimal(0);
            boolean passou = false;
            for (Baixa b : resultados) {
                if (moeda.getId().equals(b.getCotacao().getConta().getMoeda().getId())) {
                    valor = valor.add(b.getValor());
                    passou = true;
                }
            }
            if (passou) {
                resumo.add(new ResumoDeMoeda(moeda, valor, null, null));
            }
        }

        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("LISTA_RESUMO_MOEDA", resumo);

        ImpressoraDeRelatorioConsole impressora = new ImpressoraDeRelatorioConsole();
        impressora.comParametros(parametros).imprimir(resultados, "RelatorioDeContasAPagarPagas");
        impressora.visualizar();

    }

}
