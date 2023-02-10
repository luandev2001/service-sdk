package com.xuanluan.mc.domain.validation.file;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Xuan Luan
 * @createdAt 12/18/2022
 */
@Target({ METHOD, FIELD, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {ImageFileValidator.class})
public @interface ValidImage {
    String message() default "Chỉ chấp nhận ảnh PNG|JPG|JPEG.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
