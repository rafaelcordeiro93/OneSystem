package br.com.onesystem.services;

import br.com.onesystem.exception.DadoInvalidoException;

/**
 * @author Rafael Fernando Rauber
 * @data 22/03/2013
 * @funcao Esta interface deve ser implementada para documentos como CNPJ, CNH,
 * CPF, RG entre outros documentos de identificação de pessoa física ou de
 * pessoa jurídica.
 */

public interface Documento {

    /**
     * @author Rafael Fernando Rauber
     * @data 22/03/2013
     * @funcao Este método deve ser implementado para validar o documento.
     */
    public boolean ehValido(String valor) throws DadoInvalidoException;

    /**
     * @author Rafael Fernando Rauber
     * @data 22/03/2013
     * @return Deve retornar o número de identificação do documento.
     */
    public String getValor();
}
