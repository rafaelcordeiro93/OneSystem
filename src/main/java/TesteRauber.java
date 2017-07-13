
import br.com.onesystem.dao.TituloDAO;
import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.war.service.DespesaProvisionadaService;
import br.com.onesystem.war.service.TituloService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
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

    public static void main(String[] args) throws DadoInvalidoException {

        Date hoje = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date proximosSeisMeses = Date.from(LocalDate.now().plusMonths(5).atStartOfDay(ZoneId.systemDefault()).toInstant());

        System.out.println("Hoje: " + hoje);
        System.out.println("Proximos 6 meses: " + proximosSeisMeses);
        System.out.println("Consulta: " + new TituloDAO().aPagar().eAbertas().ePorVencimento(hoje, proximosSeisMeses).getConsulta());
        
        List<Titulo> titulosAPagar = new TituloDAO().aPagar().eAbertas().ePorVencimento(hoje, proximosSeisMeses).listaDeResultados();
        List<DespesaProvisionada> despesasAPagar = new DespesaProvisionadaService().buscarDespesaProvisionadasAPagarComVencimentoEntre(hoje, proximosSeisMeses);
 
        System.out.println("Titulos: " + titulosAPagar.size());
        titulosAPagar.forEach(System.out::println);
        despesasAPagar.forEach(System.out::println);
        
        System.out.println("Consulta finalizada com sucesso!");

    }

}
