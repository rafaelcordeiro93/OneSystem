package br.com.onesystem.war.service;

import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

public class CotacaoService implements Serializable {

    @Inject
    private CotacaoDAO dao;

    @Inject
    private Configuracao configuracao;

    public CotacaoService() {
    }

    public List<Cotacao> buscarCotacoes() {
        return dao.listaDeResultados();
    }

    public List<Cotacao> buscarCotacoesDoDiaAtual() {
        return dao.naEmissao(new Date()).porCotacaoEmpresa().listaDeResultados();
    }

    public List<Cotacao> buscarTodasCotacoesDoDiaAtual() {
        return dao.naEmissao(new Date()).listaDeResultados();
    }

    public Cotacao getCotacaoPadrao(Date emissao) throws DadoInvalidoException {
        return dao.porMoeda(configuracao.getMoedaPadrao()).porCotacaoEmpresa().naMaiorEmissao(emissao).resultado();
    }

    public Cotacao getCotacaoNaUltimaEmissaoPor(Conta conta, Date emissao) throws DadoInvalidoException {
        return dao.porConta(conta).porCotacaoBancaria().naUltimaEmissao(emissao).resultado();
    }

    public List<Cotacao> buscarCotacoesDaEmpresaNaEmissao(Date emissao) {
        return dao.naMaiorEmissao(emissao).porCotacaoEmpresa().listaDeResultados();
    }

}
