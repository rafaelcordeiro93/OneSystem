package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CotacaoService implements Serializable {

    public List<Cotacao> buscarCotacoes() {
        return new ArmazemDeRegistros<Cotacao>(Cotacao.class).listaTodosOsRegistros();
    }

    public List<Cotacao> buscarCotacoesDoDiaAtual() {
        return new CotacaoDAO().naEmissao(new Date()).porCotacaoEmpresa().listaDeResultados();
    }

    public List<Cotacao> buscarTodasCotacoesDoDiaAtual() {
        return new CotacaoDAO().naEmissao(new Date()).listaDeResultados();
    }

    public Cotacao getCotacaoPadrao(Date emissao) throws DadoInvalidoException {
        return new CotacaoDAO().porMoeda(new ConfiguracaoService().buscar().getMoedaPadrao()).porCotacaoEmpresa().naMaiorEmissao(emissao).resultado();
    }

}
