
import br.com.onesystem.dao.ArmazemDeRegistrosConsole;
import br.com.onesystem.dao.NotaEmitidaDAO;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.CobrancaVariavel;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.util.ImpressoraDeTexto;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.reportTemplate.CaminhoDeClasse;
import br.com.onesystem.util.GenericLayout;
import br.com.onesystem.util.LeitoraDeCaminhoDeClassesJSON;
import br.com.onesystem.war.service.NotaEmitidaService;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private static final String diretorio = System.getProperty("user.dir") + "\\src\\main\\resources\\layoutsTexto\\recebimentoDeTitulo.json";

    public static void main(String[] args) throws DadoInvalidoException, JRException, FileNotFoundException, UnsupportedEncodingException, IOException, ParseException, ClassNotFoundException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {

        ImpressoraDeTexto t = new ImpressoraDeTexto();

        //lin x col
        t.setOutSize(70, 162);

        //  JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader(diretorio));
        //JSONObject jsonObject = (JSONObject) obj;

        JSONArray dados = (JSONArray) jsonObject.get("dados");

        JSONArray layout = (JSONArray) jsonObject.get("layout");

        t.carregaLayout(layout);

        Class clazz = Nota.class;

        List<CaminhoDeClasse> clazzes = new LeitoraDeCaminhoDeClassesJSON().getCaminhos(Nota.class);

        NotaEmitida nota = new ArmazemDeRegistrosConsole<NotaEmitida>(NotaEmitida.class).find(new Long(1));

        for (GenericLayout g : t.criaListaGenericLayout(dados)) {
            Class clazzDado = Class.forName(g.getTabela());
            CaminhoDeClasse caminhoDeClasse = null;

            Object obj = null;

            try {
                if (clazz.equals(clazzDado) || clazz.equals(clazzDado.getSuperclass())) {

                    if (g.getColuna().contains("/")) {
                        String[] col = g.getColuna().split("/");
                        String str = "";
                        for (String s : col) {
                            Method method = Class.forName(g.getTabela()).getMethod("get" + s.substring(0, 1).toUpperCase() + s.substring(1), null);
                            str += ", " + method.invoke(nota).toString();
                        }
                        t.insertTextoNaLinhaColuna(g.getTop(), g.getLeft(), str.substring(2));
                    } else {
                        Method field = Class.forName(g.getTabela()).getMethod("get" + g.getColuna().substring(0, 1).toUpperCase()
                                + g.getColuna().substring(1), null);
                        obj = field.invoke(nota);
                        t.insertTextoNaLinhaColuna(g.getTop(), g.getLeft(), obj.toString());
                    }
                } else {
                    Class cl = null;
                    for (CaminhoDeClasse c : clazzes) {
                        if (c.getClasseDeDestino() == clazzDado) {
                            cl = clazzDado;
                            caminhoDeClasse = c;
                        }
                    }
                    if (cl == null) {
                        throw new RuntimeException("Configuracao nao definida no arquivo de configuracao Extra Class: " + clazzDado);
                    } else {
                        String[] split = caminhoDeClasse.getCaminho().split("\\.");

                        Method method = null;
                        Class clazzIterada = caminhoDeClasse.getClasseDeOrigem();
                        obj = nota;

                        for (String s : split) {
                            method = clazzIterada.getMethod("get" + s.substring(0, 1).toUpperCase() + s.substring(1), null);
                            obj = method.invoke(obj);
                            clazzIterada = obj.getClass();
                        }
                        if (clazzIterada == caminhoDeClasse.getClasseDeDestino() || clazzIterada.getSuperclass() == caminhoDeClasse.getClasseDeDestino()) {
                            if (g.getColuna().contains("/")) {
                                String[] col = g.getColuna().split("/");
                                String str = "";
                                for (String s : col) {
                                    method = clazzIterada.getMethod("get" + s.substring(0, 1).toUpperCase() + s.substring(1), null);
                                    str += ", " + method.invoke(obj).toString();
                                }
                                t.insertTextoNaLinhaColuna(g.getTop(), g.getLeft(), str.substring(2));
                            } else {
                                method = clazzIterada.getMethod("get" + g.getColuna().substring(0, 1).toUpperCase() + g.getColuna().substring(1), null);
                                t.insertTextoNaLinhaColuna(g.getTop(), g.getLeft(), method.invoke(obj).toString());
                            }
                        } else {
                            throw new RuntimeException("Classe " + clazzIterada + " nao encontrada!");
                        }
                    }
                }
            } catch (NoSuchMethodException nsm) {
                throw new RuntimeException("Metodo nao encontrado " + nsm.getMessage());
            }

//            
        }
        //  t.toPrinterMatricial();
        t.show();
    }
}
