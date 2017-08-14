
import br.com.onesystem.util.BundleUtil;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.MissingResourceException;

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

    public static void main(String[] args) {

        try{
       new BundleUtil().getLabel("sadfdafd");
        }catch(MissingResourceException mre){
            System.out.println("teste");
        }
    }
}
