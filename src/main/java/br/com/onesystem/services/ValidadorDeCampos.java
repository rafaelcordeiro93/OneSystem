/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.services;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Responsável por realizar a validação de todos os campos do sistema.
 *
 * @author Rafael-Pc
 */
public class ValidadorDeCampos<T> {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    public boolean valida(T t, List<String> campos) throws DadoInvalidoException {
        for (String campo : campos) {
            valida(campo, t);
        }
        return true;
    }

    private boolean valida(String atributo, T t) throws DadoInvalidoException {

        try {
            Set<ConstraintViolation<T>> violationContraint = validator.validateProperty(t, atributo);

            if (violationContraint.size() > 0) {
                for (ConstraintViolation<T> constraint : violationContraint) {
                    throw new EDadoInvalidoException("{" + t.getClass().toString().substring(t.getClass().toString().lastIndexOf(".") + 1, t.getClass().toString().length()) + "} "
                            + constraint.getMessage());
                }
            }
            return true;
        } catch (IllegalArgumentException iae) {
            throw new EDadoInvalidoException("{" + t.getClass().toString().substring(t.getClass().toString().lastIndexOf(".") + 1, t.getClass().toString().length()) + "} " + new BundleUtil().getMessage("erro_ao_validar_campos"));
        }
    }
}
