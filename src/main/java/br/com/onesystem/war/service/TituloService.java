package br.com.onesystem.war.service;

import br.com.onesystem.dao.TituloDAO;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.reportTemplate.SomaSaldoDeTituloPorMoedaReportTemplate;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TituloService implements Serializable {

    public List<Titulo> buscarTitulos() {
        return new TituloDAO().listaDeResultados();
    }

    public List<Titulo> buscarTitulosAReceber() {
        return new TituloDAO().aReceber().eAbertas().listaDeResultados();
    }

    public List<Titulo> buscarTitulosAPagar() {
        return new TituloDAO().aPagar().eAbertas().listaDeResultados();
    }

    public List<Titulo> buscarTitulosComVencimentoEntre(Date dataInicial, Date dataFinal) {
        return new TituloDAO().eAbertas().ePorVencimento(dataInicial, dataFinal).listaDeResultados();
    }

    public List<Titulo> buscarTitulosAPagarComVencimentoEntre(Date dataInicial, Date dataFinal) {
        return new TituloDAO().aPagar().eAbertas().ePorVencimento(dataInicial, dataFinal).listaDeResultados();
    }

    public List<Titulo> buscarTitulosAReceberComVencimentoEntre(Date dataInicial, Date dataFinal) {
        return new TituloDAO().aReceber().eAbertas().ePorVencimento(dataInicial, dataFinal).listaDeResultados();
    }

    public List<SomaSaldoDeTituloPorMoedaReportTemplate> buscarSaldoDeTitulosAPagarDeRecepcaoPara(Pessoa pessoa) {
        return new TituloDAO().buscarSaldoPorMoedaDeTitulos().aPagar().eComRecepcao().ePorPessoa(pessoa).agrupadoPorMoeda().resultadoSomaPorMoeda();
    }

}
