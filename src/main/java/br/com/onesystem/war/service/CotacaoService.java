package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Configuracao;
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
        return new CotacaoDAO().buscarCotacoes().naEmissao(new Date()).porCotacaoEmpresa().listaDeResultados();
    }

    public Cotacao getCotacaoPadrao(Date emissao) throws DadoInvalidoException {
        Configuracao configuracao = new ConfiguracaoService().buscar();
        return new CotacaoDAO().buscarCotacoes().porCotacaoEmpresa().porMoeda(configuracao.getMoedaPadrao()).naMaiorEmissao(emissao).resultado();
    }

}
