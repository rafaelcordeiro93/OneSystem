/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.services.impl;

import br.com.onesystem.services.CharacterType;
import br.com.onesystem.valueobjects.CaseType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Rafael Fernando Rauber
 * @version
 * @since
 */
public class CheckCharacterTypeValidator implements ConstraintValidator<CharacterType, Object> {

    private CaseType characterType;

    @Override
    public void initialize(CharacterType characterType) {
        this.characterType = characterType.value();
    }

    @Override
    public boolean isValid(Object valor, ConstraintValidatorContext context) {
        if (valor == null) {
            return true;
        }
        String value = valor.toString();

        if (characterType == CaseType.LETTER) {
            for (int i = 0; i < value.length(); i++) {
                char b = value.charAt(i);
                if (!Character.isLetter(b)) {
                    return false;
                }
            }
            return true;
        } else if (characterType == CaseType.DIGIT) {
            for (int i = 0; i < value.length(); i++) {
                char b = value.charAt(i);
                if (!Character.isDigit(b)) {
                    return false;
                }
            }
            return true;
        } else if (characterType == CaseType.LETTER_SPACE) {
            for (int i = 0; i < value.length(); i++) {
                char b = value.charAt(i);
                if (!Character.isLetter(b)) {
                    if (!Character.isSpaceChar(b)) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
