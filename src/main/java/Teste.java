
import br.com.onesystem.dao.ArmazemDeRegistrosConsole;
import br.com.onesystem.dao.NotaEmitidaDAO;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.util.ImpressoraDeTexto;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.GenericLayout;
import br.com.onesystem.war.service.NotaEmitidaService;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import net.sf.jasperreports.engine.JRException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Named
public class Teste {

    @Inject
    public NotaEmitidaService service;
    private static final String diretorio = System.getProperty("user.dir") + "\\src\\main\\resources\\layoutsTexto\\layoutNotaEmitidaDesenhada.json";


    public static void main(String[] args) throws DadoInvalidoException, JRException, FileNotFoundException, UnsupportedEncodingException, IOException, ParseException {

        ImpressoraDeTexto t = new ImpressoraDeTexto();

        //lin x col
        t.setOutSize(70, 70);

        //  JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader(diretorio));
        //JSONObject jsonObject = (JSONObject) obj;

        JSONArray dados = (JSONArray) jsonObject.get("dados");

        JSONArray layout = (JSONArray) jsonObject.get("layout");

        t.carregaLayout(layout);

        List<NotaEmitida> listaImpressao = new ArmazemDeRegistrosConsole<NotaEmitida>(NotaEmitida.class).listaTodosOsRegistros();
        for (NotaEmitida p : listaImpressao) {

            for (GenericLayout g : t.criaListaGenericLayout(dados)) {

                if (g.getColuna().equals("dia")) {
                    t.printTextLinCol(g.getTop(), g.getLeft(), new Date().toString());
                    continue;
                }
                if (g.getColuna().equals("mes")) {
                    t.printTextLinCol(g.getTop(), g.getLeft(), "");
                    continue;
                }
                if (g.getColuna().equals("ano")) {
                    t.printTextLinCol(g.getTop(), g.getLeft(), "");
                    continue;
                }
                if (g.getColuna().equals("nome")) {
                    t.printTextLinCol(g.getTop(), g.getLeft(), p.getPessoa().getNome());
                    continue;
                }
                if (g.getColuna().equals("ruc")) {
                    t.printTextLinCol(g.getTop(), g.getLeft(), p.getPessoa().getRuc());
                    continue;
                }
                if (g.getColuna().equals("endereco")) {
                    t.printTextLinCol(g.getTop(), g.getLeft(), p.getPessoa().getEndereco());
                    continue;
                }
                if (g.getColuna().equals("telefone")) {
                    t.printTextLinCol(g.getTop(), g.getLeft(), p.getPessoa().getTelefone());
                    continue;
                }
                if (g.getColuna().equals("notaDeRemicao")) {
                    t.printTextLinCol(g.getTop(), g.getLeft(), "");
                    continue;
                }
                if (g.getColuna().equals("vendedor")) {
                    t.printTextLinCol(g.getTop(), g.getLeft(), "");
                    continue;
                }
                if (g.getColuna().equals("condicaoVendaAVista")) {
                    t.printTextLinCol(g.getTop(), g.getLeft(), "");
                    continue;
                }
                if (g.getColuna().equals("condicaoVendaAPrazo")) {
                    t.printTextLinCol(g.getTop(), g.getLeft(), "");
                    continue;
                }

                Integer linhaItens = 0;
                for (ItemDeNota i : p.getItens()) {

                    for (int j = linhaItens; j < p.getItens().size(); j++) {

                        if (g.getColuna().equals("it")) {
                            t.printTextLinCol(g.getTop() + j, g.getLeft(), "");
                        }
                        if (g.getColuna().equals("item")) {
                            t.printTextLinCol(g.getTop() + j, g.getLeft(), "");

                        }
                        if (g.getColuna().equals("quantidade")) {
                            t.printTextLinCol(g.getTop() + j, g.getLeft(), i.getQuantidade().toString());

                        }
                        if (g.getColuna().equals("tipo")) {
                            t.printTextLinCol(g.getTop() + j, g.getLeft(), i.getItem().getTipoItem().toString());

                        }
                        if (g.getColuna().equals("codigoItem")) {
                            t.printTextLinCol(g.getTop() + j, g.getLeft(), i.getItem().getId().toString());

                        }
                        if (g.getColuna().equals("nomeItem")) {
                            t.printTextLinCol(g.getTop() + j, g.getLeft(), i.getItem().getNome());

                        }
                        if (g.getColuna().equals("precoUnitario")) {
                            t.printTextLinCol(g.getTop() + j, g.getLeft(), i.getUnitario().toString());
                        }
                    }
                    linhaItens++;
                }

                if (g.getColuna().equals("ivaIsento")) {
                    t.printTextLinCol(g.getTop(), g.getLeft(), "");
                    continue;
                }
                if (g.getColuna().equals("iva")) {
                    t.printTextLinCol(g.getTop(), g.getLeft(), "");
                    continue;
                }
                if (g.getColuna().equals("totalBruto")) {
                    t.printTextLinCol(g.getTop(), g.getLeft(), "");
                    continue;
                }
                if (g.getColuna().equals("totalIsento")) {
                    t.printTextLinCol(g.getTop(), g.getLeft(), "");
                    continue;
                }

                if (g.getColuna().equals("descontos")) {
                    t.printTextLinCol(g.getTop(), g.getLeft(), "");
                    continue;
                }

                if (g.getColuna().equals("totalLiquido")) {
                    t.printTextLinCol(g.getTop(), g.getLeft(), "");
                    continue;
                }

                if (g.getColuna().equals("totalIva")) {
                    t.printTextLinCol(g.getTop(), g.getLeft(), "");
                    continue;
                }

                //t.printTextLinCol(6, 1, t.centralizar(70, "CENTRALIZAR"));
            }
        }
        //  t.toPrinterMatricial();
        t.show();
    }
}
