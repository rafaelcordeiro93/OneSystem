package br.com.onesystem.war.view;

import br.com.onesystem.dao.ContratoDeCambioDAO;
import br.com.onesystem.domain.ContratoDeCambio;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ImpressoraDeRelatorio;
import br.com.onesystem.war.builder.RelatorioDeContratoDeCambioBV;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class RelatorioDeContratoDeCambioView implements Serializable {

    private RelatorioDeContratoDeCambioBV relatorio;
    private ContratoDeCambioDAO dao;
    private ImpressoraDeRelatorio impressora;

    @PostConstruct
    public void init() {
        relatorio = new RelatorioDeContratoDeCambioBV();
        dao = new ContratoDeCambioDAO();
        impressora = new ImpressoraDeRelatorio();
    }

    public void imprimir() {
        try {
            validarDados();

            List<ContratoDeCambio> lista = dao.buscarContratoDeCambioPor(relatorio.getDataInicial(),
                    relatorio.getDataFinal(),
                    relatorio.getPessoa(),
                    relatorio.getStatus());

            if (lista.isEmpty()) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Nao_existem_dados"));
            }

            impressora.imprimir(lista, "RelatorioDeContratoDeCambio");

        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }
    
    private void validarDados() throws EDadoInvalidoException {
        if (relatorio.getDataInicial() == null || relatorio.getDataFinal() == null) {
            throw new EDadoInvalidoException("As datas devem ser informadas!");
        }

        Calendar dataAtual = Calendar.getInstance();
        dataAtual.setTime(relatorio.getDataInicial() != null ? relatorio.getDataInicial() : Calendar.getInstance().getTime());
        dataAtual.set(Calendar.HOUR_OF_DAY, 0);
        dataAtual.set(Calendar.MINUTE, 0);
        dataAtual.set(Calendar.SECOND, 0);
        relatorio.setDataInicial(dataAtual.getTime());

        Calendar dataFinal = Calendar.getInstance();
        dataFinal.setTime(relatorio.getDataFinal() != null ? relatorio.getDataFinal() : Calendar.getInstance().getTime());
        dataFinal.set(Calendar.HOUR_OF_DAY, 23);
        dataFinal.set(Calendar.MINUTE, 59);
        dataFinal.set(Calendar.SECOND, 59);
        relatorio.setDataFinal(dataFinal.getTime());
    }

    public void selecionaPessoa(SelectEvent event) {
        Pessoa pessoa = (Pessoa) event.getObject();
        relatorio.setPessoa(pessoa);
    }

    public void removerPessoa() {
        relatorio.setPessoa(null);
    }

    public RelatorioDeContratoDeCambioBV getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(RelatorioDeContratoDeCambioBV relatorio) {
        this.relatorio = relatorio;
    }

}
