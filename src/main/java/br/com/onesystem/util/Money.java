package br.com.onesystem.util;

import br.com.onesystem.exception.CurrencyMissmatchException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;

/**
 *
 * @author Rauber
 */
public final class Money {

    private long value;
    private Currency currency;

    // contrutor privado
    private Money(long value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    // métodos de criação
    public static Money valueOf(String value, String currencyIsoCode) {
        return valueOf(new BigDecimal(value), currencyIsoCode);
    }

    public static Money valueOf(BigDecimal value, String currencyIsoCode) {
        return valueOf(value, Currency.getInstance(currencyIsoCode));
    }

    public static Money valueOf(BigDecimal value, Currency currency) {
        return new Money(toLongRepresentation(value, currency), currency);
    }

    // métodos para redução de e para long
    private static long toLongRepresentation(BigDecimal value, Currency currency) {
        return value.movePointRight(currency.getDefaultFractionDigits()).longValue();
    }

    private static BigDecimal fromLongRepresentation(long amount, Currency currency) {
        BigDecimal value = new BigDecimal(amount);
        return value.movePointLeft(currency.getDefaultFractionDigits());
    }

    public BigDecimal getAmount() {
        return fromLongRepresentation(value, currency);
    }

    public Currency getCurrency() {
        return currency;
    }

    // o resto dos métodos
    public Money plus(Money other) throws CurrencyMissmatchException {
        if (!other.currency.equals(this.currency)) {
            throw new CurrencyMissmatchException(this.currency, other.currency);
        }
        // é a mesma moeda. Money é imutável. Cria outro com novo valor
        return new Money(this.value + other.value, this.currency);
    }

    public Money subtract(Money other) throws CurrencyMissmatchException {
        if (!other.currency.equals(this.currency)) {
            throw new CurrencyMissmatchException(this.currency, other.currency);
        }
        // é a mesma moeda. Money é imutável. Cria outro com novo valor
        return new Money(this.value - other.value, this.currency);

    }

    public Money multiply(Number factor) {
        BigDecimal bigFactor;
        if (factor instanceof BigDecimal) {
            bigFactor = (BigDecimal) factor;
        } else {
            bigFactor = new BigDecimal(factor.toString());
        }

        long result = bigFactor.multiply(new BigDecimal(this.value)).longValue();

        return new Money(result, currency);
    }

    public Money[] distribute(int n) throws CurrencyMissmatchException {
        BigInteger bigValue = BigInteger.valueOf(this.value);
        BigInteger[] result = bigValue.divideAndRemainder(BigInteger.valueOf(n));
        
        Money[] distribution = new Money[n];

        // todos os valores são iguais
        Arrays.fill(distribution, new Money(result[0].longValue(), currency));

        // adiciona o resto no primeiro
        // substituindo o valor atual nessa posição
        distribution[0] = distribution[0].plus(new Money(result[1].longValue(), this.currency));

        return distribution;
    }
    
}
