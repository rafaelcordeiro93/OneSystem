import br.com.onesystem.dao.NotaEmitidaDAO;
import br.com.onesystem.domain.ItemEmitido;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.ValoresAVista;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.GenericLayout;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Teste {

    public static void main(String[] args) throws DadoInvalidoException, JRException, FileNotFoundException, UnsupportedEncodingException, IOException, ParseException {
        PrinterMatrix t = new PrinterMatrix();

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("d:\\layoutNotaEmitida.json"));
        JSONObject jsonObject = (JSONObject) obj;

        List<GenericLayout> listag = new ArrayList<>();

        JSONArray lista = (JSONArray) jsonObject.get("layout");

        List<GenericLayout> listaLayout = new ArrayList<>();
        for (Object o : lista) {
            JSONObject j = (JSONObject) o;

            String tabela = (String) j.get("tabela");
            String coluna = (String) j.get("coluna");
            Integer left = ((Long) j.get("left")).intValue();
            Integer top = ((Long) j.get("top")).intValue();

            GenericLayout g = new GenericLayout(tabela, coluna, left, top);

            listaLayout.add(g);
        }

        t.setOutSize(70, 70);

        List<NotaEmitida> listaImpressao = new NotaEmitidaDAO().buscarNotaEmitidaW().porId(new Long(2)).listaDeResultados();
        for (NotaEmitida p : listaImpressao) {

            for (GenericLayout g : listaLayout) {

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
                    t.printTextLinCol(g.getTop(), g.getLeft(), p.getPessoa().getDirecao());
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
                for (ItemEmitido i : p.getItensEmitidos()) {

                    for (int j = linhaItens; j < p.getItensEmitidos().size(); j++) {

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

        t.toPrinterMatricial();
    }
}