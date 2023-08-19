package com.xuanluan.mc.sdk.domain.validation.email;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

/**
 * @author Xuan Luan
 * @createdAt 2/3/2023
 */
public class EmailCollectionValidator implements ConstraintValidator<EmailCollection, Collection<String>> {
    @Override
    public boolean isValid(Collection<String> value, ConstraintValidatorContext context) {
        return check(value, context);
    }

    private boolean check(Collection<String> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        EmailValidator validator = new EmailValidator();
        for (String s : value) {
            if (!validator.isValid(s, context)) {
                return false;
            }
        }
        return true;
    }
}
