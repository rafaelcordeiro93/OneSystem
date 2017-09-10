package br.com.onesystem.war.service;

import br.com.onesystem.dao.TituloDAO;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.reportTemplate.SomaSaldoDeTituloPorMoedaReportTemplate;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

public class TituloService implements Serializable {

    @Inject
    private TituloDAO dao;
    
    public List<Titulo> buscarTitulos() {
        return dao.listaDeResultados();
    }

    public List<Titulo> buscarTitulosAReceber() {
        return dao.aReceber().eAbertas().listaDeResultados();
    }

    public List<Titulo> buscarTitulosAPagar() {
        return dao.aPagar().eAbertas().listaDeResultados();
    }

    public List<Titulo> buscarTitulosComVencimentoEntre(Date dataInicial, Date dataFinal) {
        return dao.eAbertas().ePorVencimento(dataInicial, dataFinal).listaDeResultados();
    }

    public List<Titulo> buscarTitulosAPagarComVencimentoEntre(Date dataInicial, Date dataFinal) {
        return dao.aPagar().eAbertas().ePorVencimento(dataInicial, dataFinal).listaDeResultados();
    }

    public List<Titulo> buscarTitulosAReceberComVencimentoEntre(Date dataInicial, Date dataFinal) {
        return dao.aReceber().eAbertas().ePorVencimento(dataInicial, dataFinal).listaDeResultados();
    }

    public List<SomaSaldoDeTituloPorMoedaReportTemplate> buscarSaldoDeTitulosAPagarDeRecepcaoPara(Pessoa pessoa) {
        //return dao.buscarSaldoPorMoedaDeTitulos().aPagar().eComRecepcao().ePorPessoa(pessoa).agrupadoPorMoeda().resultadoSomaPorMoeda();
        return null;
    }

}
