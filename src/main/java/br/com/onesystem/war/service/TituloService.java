package br.com.onesystem.war.service;

import br.com.onesystem.dao.TituloDAO;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.reportTemplate.SomaSaldoDeTituloPorMoedaReportTemplate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "tituloService")
@ApplicationScoped
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

    public List<SomaSaldoDeTituloPorMoedaReportTemplate> buscarSaldoDeTitulosAPagarDeRecepcaoPara(Pessoa pessoa) {
        return new TituloDAO().buscarSaldoPorMoedaDeTitulos().wAPagar().eComRecepcao().ePorPessoa(pessoa).agrupadoPorMoeda().resultadoSomaPorMoeda();
    }

}
