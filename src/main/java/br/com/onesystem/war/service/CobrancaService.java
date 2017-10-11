package br.com.onesystem.war.service;

import br.com.onesystem.dao.CobrancaDAO;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.CobrancaVariavel;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.MoedaFormatter;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateful;
import javax.inject.Inject;

public class CobrancaService implements Serializable {

    @Inject
    private CobrancaDAO dao;
    @Inject
    private CotacaoService cotacaoService;

    public List<CobrancaVariavel> buscarCobrancas() {
        return dao.listaDeResultados();
    }

    public String getValorNaMoedaPadraoFormatado(Cobranca cobranca) {
        try {
            Cotacao cotacao = cotacaoService.getCotacaoPadrao(cobranca.getEmissao());
            return MoedaFormatter.format(cotacao.getConta().getMoeda(), cobranca.getValorNaMoedaPadrao());
        } catch (DadoInvalidoException ex) {
            return cobranca.getValorNaMoedaPadrao().toString();
        }
    }

}
