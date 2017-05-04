
import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.OrcamentoDAO;
import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.Janela;
import br.com.onesystem.domain.Modulo;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.domain.Privilegio;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.EstadoDeOrcamento;
import br.com.onesystem.war.service.OrcamentoService;
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

    public static void main(String[] args) throws DadoInvalidoException {

        OrcamentoService o = new OrcamentoService();
        System.out.println(o.buscarOrcamentosNo(EstadoDeOrcamento.APROVADO).size());

        System.out.println("Concluído");
    }

}
