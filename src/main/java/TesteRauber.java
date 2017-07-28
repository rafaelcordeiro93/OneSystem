
import br.com.onesystem.dao.CobrancaDAO;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.FieldModel;
import br.com.onesystem.valueobjects.TipoDeBusca;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    public static void main(String[] args) throws DadoInvalidoException, ClassNotFoundException, NoSuchFieldException, NoSuchMethodException {

        Class c = Cobranca.class;
        System.out.println(c.getSimpleName().toLowerCase().substring(0, 1) + c.getSimpleName().substring(1));


//        FieldModel vencimento = new FieldModel("Vencimento (Cobrança)", "Cobrança", "vencimento", Cobranca.class);
//        FieldModel pessoa = new FieldModel(null, null, "pessoa", "nome", Cobranca.class);
//
//        CobrancaDAO dao = new CobrancaDAO();
//
//        dao.filter(TipoDeBusca.CONTENDO, pessoa, Arrays.asList("cordeiro","rauber")).listaDeResultados().stream().forEach(System.out::println);
    }

}
