
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Titulo;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public static void main(String[] args) throws Exception {

        TesteRauber testeRauber = new TesteRauber();

        List<Titulo> lista = new ArmazemDeRegistros<Titulo>(Titulo.class).listaTodosOsRegistros();
        lista.forEach(System.out::println);
        List<Moeda> moedas = new ArrayList<>();

        for (Object o : lista) {
            Moeda m = testeRauber.getMoeda(o);
            if (!moedas.contains(m)) {
                moedas.add(m);
            }
        }

        List[] listas = new ArrayList[moedas.size()];

        for (int i = 0; i < moedas.size(); i++) {
            listas[i] = new ArrayList();
            for (Object o : lista) {
                Moeda m = testeRauber.getMoeda(o);
                if (m == moedas.get(i)) {
                    listas[i].add(o);
                }
            }
        }

        for (int i = 0; i < listas.length; i++) {
            System.out.println("==================================================");
            listas[i].forEach(System.out::println);
        }

    }

    private Moeda getMoeda(Object obj) {
        try {
            String caminho = "cotacao.conta.moeda";
            Class c = Titulo.class;
            String[] split = caminho.split("\\.");

            Method m = c.getMethod("get" + split[0].substring(0, 1).toUpperCase() + split[0].substring(1), null);
            Object o = null;
            o = m.invoke(obj, null);
            for (int i = 1; i < split.length; i++) {
                Class<?> clazz = o.getClass();
                Method n = clazz.getMethod("get" + split[i].substring(0, 1).toUpperCase() + split[i].substring(1), null);
                o = n.invoke(o, null);
            }
            return (Moeda) o;
        } catch (Exception ex) {
            return null;
        }
    }

}
