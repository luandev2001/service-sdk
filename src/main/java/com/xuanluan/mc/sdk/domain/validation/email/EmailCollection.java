package com.xuanluan.mc.sdk.domain.validation.email;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Xuan Luan
 * @createdAt 2/3/2023
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailCollectionValidator.class)
@Documented
public @interface EmailCollection {
    String message() default "Danh sách có chưa giá trị không phải là địa chỉ email!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
