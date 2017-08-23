package br.com.onesystem.services.impl;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.ADadoInvalidoException;
import br.com.onesystem.exception.impl.IDadoInvalidoException;
import br.com.onesystem.services.Documento;
import java.io.Serializable;

/**
 * @author Rafael Fernando Rauber
 * @Data: 18/04/2013
 * @funcao
 */
public final class Cnpj implements Documento, Serializable {

    private String valor;

    public Cnpj() {
    }

    public Cnpj(String valor) throws DadoInvalidoException {
        if (!ehValido(valor)) {
            throw new ADadoInvalidoException("Cnpj Inválido!");
        }
        this.valor = valor;
    }

    //Valida o Cnpj
    @Override
    public boolean ehValido(String valor) throws DadoInvalidoException {
        if(valor == null){
            throw new IDadoInvalidoException("Cnpj deve ser informado!");
        }
        valor = valor.trim();
        if (valor.length() > 0) {
            if (contemQuatorzeDigitos(valor) && ehSomenteDigitos(valor)) {
                return primeiroDigitoVerificador(valor) == primeiroDigitoCorreto(valor)
                        && segundoDigitoVerificador(valor) == segundoDigitoCorreto(valor);
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * @author Rafael Fernando Rauber
     * @data 15/04/2013
     * @param valor Valor do cnpj a ser validado. Se houver erro envia o erro
     * para esta classe.
     *
     * Verifica se o dado informado contém 14 dígitos.
     */
    public boolean contemQuatorzeDigitos(String valor) throws DadoInvalidoException {
        if (valor.length() != 0 && valor.length() != 14 ) {
            throw new ADadoInvalidoException("O Cnpj deve conter 14 dígitos!");
        }
        return true;
    }

    /**
     * @author Rafael Fernando Rauber
     * @data 15/04/2013
     * @param valor Valor do cnpj a ser validado
     *
     * Verifica se o dado informado é somente dígitos.
     */
    public boolean ehSomenteDigitos(String valor) throws DadoInvalidoException {
        for (int i = 0; i < valor.length(); i++) {
            char caractere = valor.charAt(i);
            //Se o caractere não for um digito  
            if (!Character.isDigit(caractere)) {
                throw new ADadoInvalidoException("O Cnpj deve conter apenas números!");
            }
        }
        return true;
    }

    public int primeiroDigitoVerificador(String valor) {
        //Extrai o primeiro digito verificador
        return Integer.parseInt(valor.substring(12, 13));
    }

    public int segundoDigitoVerificador(String valor) {
        //Extrai o segundo digito verificador
        return Integer.parseInt(valor.substring(13, 14));
    }

    /**
     * @author Rafael Fernando Rauber
     * @data 23/05/2013
     *
     * Este método retornará o primeiro digito verificador do cnpj. Para isso
     * este dígito verificador deve ser validado. A validação consiste em duas
     * etapas usando para isto o módulo da divisão 11. Primeira parte - Cada um
     * dos 12 primeiros números é multiplicado por um número que começa de 5 e
     * que vai sendo diminuído de 1 a cada passo, quando este número se igualar
     * a 2 o mesmo se tornará 9, e continuará sendo diminuído de 1 a cada passo,
     * até que todos os 12 primeiros números sejam calculados. Segunda Parte -
     * Calcula-se o dígito verificador através da seguinte expressão: total %
     * 11, se este resultado for igual a 0 ou igual a 1 então o dígito
     * verificador será 0. Caso contrário deverá se calcular a expressao
     * anterior e calcular também a expressão seguinte 11 - resto da expressao
     * anterior.
     */
    public int primeiroDigitoCorreto(String valor) {

        //declaração das variáveis total, peso e expressao.
        int total;
        int peso;
        int expressao;

        //instanciação das variáveis total, peso e expressao. 
        total = 0;
        peso = 5;
        expressao = 0;

        //Primeira Parte
        for (int i = 0; i < 12; i++) {
            int numero = Integer.valueOf(valor.substring(i, (i + 1)));

            total += peso * numero;
            peso--;
            if (peso == 1) {
                peso = 9;
            }
        }

        //Segunda Parte
        expressao = total % 11;
        if (expressao == 0 || expressao == 1) {
            return 0;
        } else {
            return 11 - expressao;
        }
    }

    /**
     * @author Rafael Fernando Rauber
     * @data 23/05/2013
     *
     * Este método retornará o segundo digito verificador do cnpj. Para isso
     * este dígito verificador deve ser validado. A validação consiste em duas
     * etapas usando para isto o módulo da divisão 11. Primeira parte - Cada um
     * dos 13 primeiros números é multiplicado por um número que começa de 6 e
     * que vai sendo diminuído de 1 a cada passo, quando este número se igualar
     * a 2 o mesmo se tornará 9, e continuará sendo diminuído de 1 a cada passo,
     * até que todos os 13 primeiros números sejam calculados. Segunda Parte -
     * Calcula-se o dígito verificador através da seguinte expressão: total %
     * 11, se este resultado for igual a 0 ou igual a 1 então o dígito
     * verificador será 0. Caso contrário deverá se calcular a expressao
     * anterior e calcular também a expressão seguinte 11 - resto da expressao
     * anterior.
     */
    public int segundoDigitoCorreto(String valor) {

        //declaração das variáveis total, peso e expressão.
        int total;
        int peso;
        int expressao;

        //instanciação das variáveis total, peso e expressão.
        total = 0;
        peso = 6;
        expressao = 0;

        //Primeira Parte
        for (int i = 0; i < 13; i++) {
            int numero = Integer.parseInt(valor.substring(i, (i + 1)));
            total += peso * numero;
            peso--;
            if (peso == 1) {
                peso = 9;
            }
        }

        //Segunda Parte
        expressao = total % 11;
        if (expressao == 0 || expressao == 1) {
            return 0;
        } else {
            return 11 - expressao;
        }
    }

    //Retorna o valor do Cnpj
    @Override
    public String getValor() {
        return valor;
    }
}
