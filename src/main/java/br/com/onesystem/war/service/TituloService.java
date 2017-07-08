package br.com.onesystem.war.service;

import br.com.onesystem.dao.TituloDAO;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.reportTemplate.SomaSaldoDeTituloPorMoedaReportTemplate;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TituloService implements Serializable {

    public List<Titulo> buscarTitulos(){
        return new TituloDAO().buscarTitulos().listaDeResultados();
    }
    
    public List<Titulo> buscarTitulosAReceber() {
        return new TituloDAO().buscarTitulos().wAReceber().eAbertas().listaDeResultados();
    }
    
    public List<Titulo> buscarTitulosAPagar() {
        return new TituloDAO().buscarTitulos().wAPagar().eAbertas().listaDeResultados();
    }
    
    public List<Titulo> buscarTitulosAPagarComVencimentoEntre(Date dataInicial, Date dataFinal) {
        return new TituloDAO().buscarTitulos().wAPagar().eAbertas().ePorVencimento(dataInicial, dataFinal).listaDeResultados();
    }

    public List<SomaSaldoDeTituloPorMoedaReportTemplate> buscarSaldoDeTitulosAPagarDeRecepcaoPara(Pessoa pessoa) {
        return new TituloDAO().buscarSaldoPorMoedaDeTitulos().wAPagar().eComRecepcao().ePorPessoa(pessoa).agrupadoPorMoeda().resultadoSomaPorMoeda();
    }

}
