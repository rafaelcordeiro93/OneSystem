
import br.com.onesystem.dao.BaixaDAO;
import br.com.onesystem.dao.TituloDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.PessoaFisica;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.reportTemplate.SomaSaldoDeTituloPorMoedaReportTemplate;
import br.com.onesystem.services.Documento;
import br.com.onesystem.services.impl.Cnpj;
import br.com.onesystem.valueobjects.TipoPessoa;
import java.util.List;
import static javax.swing.JOptionPane.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rafael
 */
public class Teste {

    public static void main(String[] args) throws DadoInvalidoException {

        BaixaDAO baixaDAO = new BaixaDAO();
        List<Baixa> lista;

        //fadfaddsfsaf
        //Adicionado pois faltava
        
//        lista =  fsasfdfdsafasd baixaDAO.buscarBaixasW().eComTitulo().eComTituloPagoRecebido().eSaida().ePorEmissaoEntre(relatorio.getDataInicial(), relatorio.getDataFinal())
//                .ePorPessoa(relatorio.getPessoa()).ePorConta(relatorio.getConta()).orderByMoeda().listaDeResultados();
    }

}
