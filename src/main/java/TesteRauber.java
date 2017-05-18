
import br.com.onesystem.dao.EstoqueDAO;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoLancamento;
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

    public static void main(String[] args) throws DadoInvalidoException, NoSuchFieldException {

        EstoqueDAO dao = new EstoqueDAO();

        List<Estoque> listaDeResultados = dao.porTipoDeLancamentoDeNota(TipoLancamento.EMITIDA).listaResultados();


    }

}
