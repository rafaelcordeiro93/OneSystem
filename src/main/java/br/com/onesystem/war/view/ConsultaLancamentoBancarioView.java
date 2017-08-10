/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.dao.LancamentoBancarioDAO;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.LancamentoBancario;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.TipoLancamentoBancario;
import br.com.onesystem.war.builder.LancamentoBancarioBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael Cordeiro
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ConsultaLancamentoBancarioView extends BasicMBImpl<LancamentoBancario, LancamentoBancarioBV> implements Serializable {

    private LancamentoBancarioBV lancamentoEstonado;
    private List<Cotacao> cotacaoBancariaLista;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void inicializar() {
        cotacaoBancariaLista = new CotacaoDAO().naUltimaEmissao(e.getEmissao()).porCotacaoBancaria().listaDeResultados();
    }

    @Override
    public void limparJanela() {
        e = new LancamentoBancarioBV();
        lancamentoEstonado = new LancamentoBancarioBV();
        cotacaoBancariaLista = new ArrayList<>();
    }

    @Override
    public void selecionar(SelectEvent event) {
        e = new LancamentoBancarioBV((LancamentoBancario) event.getObject());
        inicializar();
    }

    public void estorno() {
        try {
            lancamentoEstonado = new LancamentoBancarioBV(e.construirComID());
            selecionaCotacao();
            lancamentoEstonado.setId(null);
            lancamentoEstonado.setBaixas(null);
            lancamentoEstonado.setEmissao(new Date());
            lancamentoEstonado.setTipoLancamentoBancario(TipoLancamentoBancario.ESTORNO);
            lancamentoEstonado.setEstornado(true);
            lancamentoEstonado.setIdRelacaoEstorno(e.getId());
            LancamentoBancario d = lancamentoEstonado.construir();
            d.geraEstornoDoLancamentoCom(lancamentoEstonado.getCotacaoDeConta());
            new AdicionaDAO<>().adiciona(d);
            atualizaLancamento(d);
            InfoMessage.atualizado();
            limparJanela();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void selecionaCotacao() {
        if (lancamentoEstonado.getConta() != null) {
            lancamentoEstonado.setCotacaoDeConta(cotacaoBancariaLista.stream().filter(c -> c.getConta().equals(e.getConta())).findFirst().get());
        }
    }

    private void atualizaLancamento(LancamentoBancario d) {
        try {
            e.setEstornado(true);
            t = e.construirComID();
            t.setIdRelacaoEstorno(d);
            new AtualizaDAO<>().atualiza(t);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void delete() {
        try {
            t = e.construirComID();
            if (t.getTipoLancamentoBancario().equals(TipoLancamentoBancario.ESTORNO)) {
                cancelaEstorno();
            }
            deleteNoBanco(t, t.getId());
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private void cancelaEstorno() throws ConstraintViolationException, DadoInvalidoException {
        LancamentoBancarioBV de = new LancamentoBancarioBV(new LancamentoBancarioDAO().porId(t.getIdRelacaoEstorno()).resultado());
        de.setIdRelacaoEstorno(null);
        de.setEstornado(false);
        new AtualizaDAO<>().atualiza(de.construirComID());
    }

}
