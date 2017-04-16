/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.DespesaProvisionadaDAO;
import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.ImpressoraDeRelatorio;
import br.com.onesystem.war.builder.RelatorioDeSaldoDeDivisaoDeLucroBV;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class RelatorioDeSaldoDeDivisaoDeLucroView implements Serializable {

    private RelatorioDeSaldoDeDivisaoDeLucroBV relatorio;
    private ImpressoraDeRelatorio impressora;

    @PostConstruct
    public void init() {
        relatorio = new RelatorioDeSaldoDeDivisaoDeLucroBV();
        impressora = new ImpressoraDeRelatorio();
    }

    public void imprimir() {
        try {
            DespesaProvisionadaDAO dao = new DespesaProvisionadaDAO();
            List<DespesaProvisionada> dados = dao.buscarDespesasProvisionadas().wAPagar().eComDivisaoDeLucro()
                    .ePorPessoa(relatorio.getPessoa()).ePorEmissao(relatorio.getDataInicial(), relatorio.getDataFinal())
                    .groupByPessoa().orderByMoeda().gerarDados();
            impressora.imprimir(dados, "RelatorioDeSaldoDeDivisaoDeLucroCN");
        } catch (DadoInvalidoException die) {
            ErrorMessage.print("Erro ao gerar o Relatório: " + die.getMessage());
        }
    }

    public void selecionaPessoa(SelectEvent event) {
        Pessoa pessoa = (Pessoa) event.getObject();
        relatorio.setPessoa(pessoa);
    }

    public void removePessoa() {
        relatorio.setPessoa(null);
    }

    public RelatorioDeSaldoDeDivisaoDeLucroBV getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(RelatorioDeSaldoDeDivisaoDeLucroBV relatorio) {
        this.relatorio = relatorio;
    }

    public ImpressoraDeRelatorio getImpressora() {
        return impressora;
    }

    public void setImpressora(ImpressoraDeRelatorio impressora) {
        this.impressora = impressora;
    }

}
