
import br.com.onesystem.aop.ClassTypeAdapter;
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.exception.DadoInvalidoException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rafael Fernando Rauber
 */
public class RedisTeste {

    public static void main(String[] args) throws DadoInvalidoException {

        Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new ClassTypeAdapter()).create();

        Marca marca = new ArmazemDeRegistros<Marca>(Marca.class).find(new Long(1));
        
        System.out.println("Marca" + marca);
        
    }

}
