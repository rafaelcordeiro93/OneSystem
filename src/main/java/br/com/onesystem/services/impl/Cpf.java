package br.com.onesystem.services.impl;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.ADadoInvalidoException;
import br.com.onesystem.exception.impl.IDadoInvalidoException;
import br.com.onesystem.services.Documento;
import java.io.Serializable;

/**
 * @author Rafael Fernando Rauber
 * @data 15/04/2013
 *
 * Esta classe deverá validar o Cpf e armazená-lo, deve ser utilizada apenas
 * pela interface <code>Documento</code>.
 */
public final class Cpf implements Documento, Serializable {

    private String valor;

    public Cpf() {
    }

    public Cpf(String valor) throws DadoInvalidoException {
        if (!ehValido(valor)) {
            throw new ADadoInvalidoException("Cpf Inválido!");
        }
        this.valor = valor;
    }

    @Override
    public boolean ehValido(String valor) throws DadoInvalidoException {
        if (valor == null) {
            throw new IDadoInvalidoException("Cpf deve ser informado!");
        }
        valor = valor.trim();
        if (valor.length() > 0) {
            if (ehSomenteDigitos(valor) && contemOnzeDigitos(valor) && todosDigitosIdenticos(valor)) {
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
     * @param valor Valor do cpf a ser validado. Se houver erro envia o erro
     * para esta classe.
     *
     * Verifica se o dado informado contém 11 dígitos.
     */
    public boolean contemOnzeDigitos(String valor) throws DadoInvalidoException {
        if (valor.length() != 11 && valor.length() != 0) {
            throw new ADadoInvalidoException("O Cpf deve conter 11 dígitos!");
        }
        return true;
    }

    /**
     * @author Rafael Fernando Rauber
     * @data 17/04/2013
     *
     * Verifica se todos os dígitos são identicos.
     */
    public boolean todosDigitosIdenticos(String valor) throws DadoInvalidoException {
        if ("00000000000".equals(valor) || "11111111111".equals(valor)
                || "22222222222".equals(valor) || "33333333333".equals(valor)
                || "44444444444".equals(valor) || "55555555555".equals(valor)
                || "66666666666".equals(valor) || "77777777777".equals(valor)
                || "88888888888".equals(valor) || "99999999999".equals(valor)
                || "00000000000".equals(valor)) {
            throw new ADadoInvalidoException("O Cpf não pode ter todos dígitos iguais.");
        }
        return true;
    }

    /**
     * @author Rafael Fernando Rauber
     * @data 15/04/2013
     * @param valor Valor do cpf a ser validado
     *
     * Verifica se o dado informado é somente dígitos.
     */
    public boolean ehSomenteDigitos(String valor) throws DadoInvalidoException {
        for (int i = 0; i < valor.length(); i++) {
            char caractere = valor.charAt(i);
            //Se o caractere não for um digito  
            if (!Character.isDigit(caractere)) {
                throw new ADadoInvalidoException("O Cpf deve conter apenas números!");
            }
        }
        return true;
    }

    public int primeiroDigitoVerificador(String valor) {
        //Extrai o primeiro dígito verificador.
        return Integer.parseInt(valor.substring(9, 10));
    }

    public int segundoDigitoVerificador(String valor) {
        //Extrai o segundo dígito verificador.
        return Integer.parseInt(valor.substring(10, 11));
    }

    /**
     * @author Rafael Fernando Rauber
     * @data 15/04/2013
     *
     * Este método retornará o primeiro digito verificador do cpf. Para isso
     * este dígito verificador deve ser validado. A validação consiste em duas
     * etapas usando para isto o módulo da divisão 11. Primeira parte - Cada um
     * dos 9 primeiros números é multiplicado por um número que começa de 10 e
     * que vai sendo diminuído de 1 a cada passo, somando-se as partes
     * calculadas e atribuindo isto a uma variável total. Segunda Parte -
     * Calcula-se o dígito verificador através da seguinte expressão: 11 -
     * (total % 11) == 11 - (total % 11) == 11 - 7. Observação: Se o resto da
     * divisão calculado for 10 ou 11 o dígito verificador será 0, nos outros
     * casos o dígito verificador é o próprio resto.
     */
    public int primeiroDigitoCorreto(String valor) {
        //declaracao da variável total, peso e expressao.
        int total;
        int peso;
        int expressao;
        //instanciação da variável total, peso e expressao.
        total = 0;
        peso = 10;
        expressao = 0;

        //Primeira Parte
        for (int i = 0; i < 9; i++) {
            int numero = Integer.parseInt(valor.substring(i, (i + 1)));
            total += peso * numero;
            peso--;
        }

        //Segunda Parte
        expressao = 11 - (total % 11);
        if ((expressao == 10) || (expressao == 11)) {
            return 0;
        } else {
            return expressao;
        }
    }

    /**
     * @author Rafael Fernando Rauber
     * @data 15/04/2013
     *
     * Este método retornará o segundo digito verificador do cpf. Para isso este
     * dígito verificador deve ser validado. A validação consiste em duas
     * etapas. A Primeira Parte consiste em que cada um dos 10 primeiros números
     * do cpf, incluindo o primeiro dígito verificador, é multiplicado por um
     * peso que começa de 11 e que vai sendo diminuído de 1 a cada passo,
     * somado-se as partes calculadas e atribuindo a uma variável total. A
     * Segunda Parte calcula-se o dígito conforme a expressão: 11 - (total % 11)
     * == 11 - (total % 11) == 11 - 2. Observação: Se o resto da divisão
     * caculado for 10 ou 11, o dígito verificador será 0, nos outros casos,
     * será o próprio resto.
     */
    public int segundoDigitoCorreto(String valor) {
        //declaracao da variável total, peso e expressao.
        int total;
        int peso;
        int expressao;
        //instanciação da variável total, peso e expressao.
        total = 0;
        peso = 11;
        expressao = 0;

        //Primeira Parte
        for (int i = 0; i < 10; i++) {
            int numero = Integer.parseInt(valor.substring(i, (i + 1)));
            total += peso * numero;
            peso--;
        }

        //Segunda Parte
        expressao = 11 - (total % 11);
        if ((expressao == 10) || (expressao == 11)) {
            return 0;
        } else {
            return expressao;
        }
    }

    @Override
    public String getValor() {
        return valor;
    }
}
