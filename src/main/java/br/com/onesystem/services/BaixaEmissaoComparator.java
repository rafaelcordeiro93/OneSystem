package br.com.onesystem.services;

import br.com.onesystem.domain.Baixa;
import java.util.Comparator;

public class BaixaEmissaoComparator implements Comparator<Baixa> {

    @Override
    public int compare(Baixa baixa, Baixa outraBaixa) {
        return baixa.getEmissao().compareTo(outraBaixa.getEmissao());
    }

}
