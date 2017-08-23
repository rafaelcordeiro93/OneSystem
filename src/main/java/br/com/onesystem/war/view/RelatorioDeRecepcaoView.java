package br.com.onesystem.war.view;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.RecepcaoDAO;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.reportTemplate.ResumoDeMoeda;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ImpressoraDeRelatorio;
import br.com.onesystem.war.builder.RelatorioDeRecepcaoBV;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class RelatorioDeRecepcaoView implements Serializable {

    private RelatorioDeRecepcaoBV relatorio;
    private RecepcaoDAO dao;
    private ImpressoraDeRelatorio impressora;

    @PostConstruct
    public void init() {
        relatorio = new RelatorioDeRecepcaoBV();
        dao = new RecepcaoDAO();
        impressora = new ImpressoraDeRelatorio();
    }

    public void imprimir() {
        try {
            validarDados();

            List<Recepcao> lista
                    = dao.buscarRecepcoesPor(relatorio.getDataInicial(),
                            relatorio.getDataFinal(),
                            relatorio.getPessoa(),
                            relatorio.getConta());

            if (lista.isEmpty()) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Nao_existem_dados"));
            }
            Map<String, Object> totais = criarListaDeTotais(lista);
            impressora.comParametros(totais).imprimir(lista, "RelatorioDeRecepcao");

        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private Map<String, Object> criarListaDeTotais(List<Recepcao> lista) {
        List<Moeda> listaMoeda = new ArmazemDeRegistros<Moeda>(Moeda.class).listaTodosOsRegistros();
        List<ResumoDeMoeda> resumo = new ArrayList<ResumoDeMoeda>();

        for (Moeda moeda : listaMoeda) {
            BigDecimal saldo = new BigDecimal(0);
            BigDecimal valor = new BigDecimal(0);
            BigDecimal valorBaixado = new BigDecimal(0);
            boolean passou = false;
            for (Recepcao b : lista) {
                if (moeda.getId().equals(b.getCotacao().getConta().getMoeda().getId())) {
                    valor = valor.add(b.getValor());
                    passou = true;
                }
            }
            if (passou) {
                resumo.add(new ResumoDeMoeda(moeda, valor, saldo, valorBaixado));
            }
        }

        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("LISTA_RESUMO_MOEDA", resumo);

        return parametros;
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

    public void selecionaConta(SelectEvent event) {
        Conta conta = (Conta) event.getObject();
        relatorio.setConta(conta);
    }

    public void removerConta() {
        relatorio.setConta(null);
    }

    public RelatorioDeRecepcaoBV getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(RelatorioDeRecepcaoBV relatorio) {
        this.relatorio = relatorio;
    }

}
