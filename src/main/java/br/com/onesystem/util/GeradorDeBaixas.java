/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import br.com.onesystem.domain.CobrancaVariavel;
import br.com.onesystem.domain.FormaDeCobranca;
import br.com.onesystem.domain.Movimento;
import br.com.onesystem.domain.Recebimento;
import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.domain.ValorPorCotacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.GeradorDeBaixaDeTipoCobrancaFixa;
import br.com.onesystem.util.GeradorDeBaixaDeTipoCobrancaVariavel;
import br.com.onesystem.util.GeradorDeBaixaDeFormaCobranca;
import java.io.Serializable;
import javax.inject.Inject;

/**
 *
 * @author Rafael
 */
public class GeradorDeBaixas implements Serializable {

    @Inject
    private GeradorDeBaixaDeTipoCobrancaVariavel geradorDeBaixaDeTipoCobrancaVariavel;

    @Inject
    private GeradorDeBaixaDeTipoCobrancaFixa geradorDeBaixaDeTipoCobrancaFixa;

    @Inject
    private GeradorDeBaixaDeFormaCobranca geradorDeFormaDeCobranca;

    public void geraBaixasDe(Movimento movimento) throws DadoInvalidoException {
        if (movimento.getTipoDeCobranca() != null && !movimento.getTipoDeCobranca().isEmpty()) {
            for (TipoDeCobranca t : movimento.getTipoDeCobranca()) {
                geraBaixasDe(t);
            }
        }
        if (movimento.getFormasDeCobranca() != null && !movimento.getFormasDeCobranca().isEmpty()) {
            for (FormaDeCobranca f : movimento.getFormasDeCobranca()) {
                geraBaixasDe(f);
            }
        }
        if (movimento.getValorPorCotacao() != null && !movimento.getValorPorCotacao().isEmpty()) {
            for (ValorPorCotacao v : movimento.getValorPorCotacao()) {
                v.geraBaixaPor(movimento);
            }
        }
    }

    private void geraBaixasDe(TipoDeCobranca tipoDeCobranca) throws DadoInvalidoException {
        if (tipoDeCobranca.getCobranca() instanceof CobrancaVariavel) {
            geradorDeBaixaDeTipoCobrancaVariavel.geraBaixas(tipoDeCobranca);
        } else {
            geradorDeBaixaDeTipoCobrancaFixa.geraBaixas(tipoDeCobranca);
        }
    }

    private void geraBaixasDe(FormaDeCobranca formaDeCobranca) throws DadoInvalidoException {
        if (formaDeCobranca.getCobranca() != null) {
            geradorDeFormaDeCobranca.geraBaixas(formaDeCobranca);
        }
    }

}
