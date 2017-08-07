/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.services;

import br.com.onesystem.services.impl.CheckCharacterTypeValidator;
import br.com.onesystem.valueobjects.CaseType;
import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author Rafael Fernando Rauber
 * @version
 * @since
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CheckCharacterTypeValidator.class)
@Documented
public @interface CharacterType {

    public String message() default "{br.com.prime.sentinela.valueobjects.CaseType.message}";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public CaseType value();
}
