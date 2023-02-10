package com.xuanluan.mc.domain.validation.file;


import com.xuanluan.mc.domain.model.request.FileRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Xuan Luan
 * @createdAt 12/18/2022
 */
public class ImageFileValidator implements ConstraintValidator<ValidImage, FileRequest> {

    @Override
    public boolean isValid(FileRequest value, ConstraintValidatorContext context) {
        return value == null || isSupportedContentType(value.getType());
    }

    public static boolean isSupportedContentType(String contentType) {
        return "image/png".equals(contentType)
                || "image/jpg".equals(contentType)
                || "image/jpeg".equals(contentType);
    }
}
