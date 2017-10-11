package br.com.onesystem.war.service;

import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.LoteNotaFiscalDAO;
import br.com.onesystem.dao.NumeracaoDeNotaFiscalDAO;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.LoteNotaFiscal;
import br.com.onesystem.domain.NumeracaoDeNotaFiscal;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.war.builder.NumeracaoDeNotaFiscalBV;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class NumeracaoDeNotaFiscalService implements Serializable {

    @Inject
    private NumeracaoDeNotaFiscalDAO dao;

    @Inject
    private AtualizaDAO<NumeracaoDeNotaFiscal> atualiza;

    @Inject
    private LoteNotaFiscalService loteNotaFiscalService;

    public List<NumeracaoDeNotaFiscal> buscarNumeracaoDeNotasFiscais() {
        return dao.listaDeResultados();
    }

    public List<NumeracaoDeNotaFiscal> buscaNumeracaoPorLote(LoteNotaFiscal lote) {
        return dao.porLote(lote).listaDeResultados();
    }

    public void atualizaNumeracao(Operacao op, Filial filial) {
        try {
            for (NumeracaoDeNotaFiscal numeracao : loteNotaFiscalService.buscaLoteNotaFiscalDa(op).getNumeracaoDeNotaFiscal()) {
                if (numeracao.getFilial().equals(filial)) {
                    NumeracaoDeNotaFiscalBV bv = new NumeracaoDeNotaFiscalBV(numeracao);
                    bv.setNumeroNF(bv.getNumeroNF() + 1);
                    atualiza.atualiza(bv.construirComID());
                }
            }

        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

}
