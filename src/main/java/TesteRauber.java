
import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.FiltroDeRelatorio;
import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoDeBusca;
import br.com.onesystem.valueobjects.TipoRelatorio;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        try {
            Coluna c = new Coluna("coluna", "tabela", "nome", Pessoa.class, String.class);
            Coluna c2 = new Coluna("coluna", "tabela", "sobreNome", Pessoa.class, String.class);

            FiltroDeRelatorio f = new FiltroDeRelatorio(null, c2, TipoDeBusca.IGUAL_A);

            ModeloDeRelatorio m;
            m = new ModeloDeRelatorio(null, "Modelo", TipoRelatorio.PESSOAS);
            m.adicionaColunaExibida(c);
            m.adicionaFiltro(f);

            System.out.println("M: " + m);

            new AdicionaDAO<>().adiciona(m);

            System.out.println("Adicionado: " + m);

        } catch (DadoInvalidoException ex) {
            ex.printConsole();
        }
    }
}
