
import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.Janela;
import br.com.onesystem.domain.Modulo;
import br.com.onesystem.domain.Privilegio;
import br.com.onesystem.exception.DadoInvalidoException;

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

    public static void main(String[] args) throws DadoInvalidoException {

        Modulo modulo = new ArmazemDeRegistros<Modulo>(Modulo.class).find(new Long(2));
        
        GrupoDePrivilegio grupoPrivilegio = new ArmazemDeRegistros<GrupoDePrivilegio>(GrupoDePrivilegio.class).find(new Long(1));
        
        AdicionaDAO<Janela> daoJanela = new AdicionaDAO<>();
        Janela janela = new Janela(null, "Aprovação Coletiva de Orçamento", "/aprovacaoColetivaOrcamento.xhtml", modulo);
        
        daoJanela.adiciona(janela);
        
        AdicionaDAO<Privilegio> daoPrivilegio = new AdicionaDAO<>();
        Privilegio privilegio = new Privilegio(null, janela, true, true, true, true, grupoPrivilegio);
     
        daoPrivilegio.adiciona(privilegio);
        
        System.out.println("Concluído");
    }

}
