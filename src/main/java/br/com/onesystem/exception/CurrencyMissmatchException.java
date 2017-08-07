package br.com.onesystem.exception;

import java.util.Currency;

/**
 * @author Rafael Fernando Rauber
 * @Data: 13/11/2013
 * @funcao
 */
public class CurrencyMissmatchException extends Exception {

    public CurrencyMissmatchException() {
        super("Tipo de moedas diferentes para a operação!");
    }

    public CurrencyMissmatchException(Currency currency, Currency otherCurrency) {
        super("Tipo de moedas diferentes para a operação!\n"
                + "Moeda 1 - " + currency
                + "Moeda 2 - " + otherCurrency);
    }
}
