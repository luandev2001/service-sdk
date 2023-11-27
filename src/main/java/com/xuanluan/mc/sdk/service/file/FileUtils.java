package com.xuanluan.mc.sdk.service.file;

import com.xuanluan.mc.sdk.domain.entity.FileStorage;
import com.xuanluan.mc.sdk.domain.model.request.FileRequest;
import com.xuanluan.mc.sdk.exception.ServiceException;
import com.xuanluan.mc.sdk.utils.AssertUtils;
import com.xuanluan.mc.sdk.utils.BaseStringUtils;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Xuan Luan
 * @createdAt 12/20/2022
 */
public class FileUtils {
    public static FileRequest uploadFile(MultipartFile file) {
        Assert.notNull(file, "file must not be null!");
        try {
            return FileRequest
                    .builder()
                    .name(file.getName())
                    .type(file.getContentType())
                    .originalFile(file.getOriginalFilename())
                    .size(file.getSize())
                    .data(new String(Base64.encodeBase64(file.getBytes())))
                    .build();
        } catch (IOException e) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "Error upload file", "Có lỗi trong quá trình tải file lên");
        }
    }

    public static String getImage(FileStorage file) {
        AssertUtils.notNull(file, "request");
        AssertUtils.isTrue(BaseStringUtils.checkSuffixImage(file.getType()), "error.unsupported.file", "");
        return "data:" + file.getType() + ";base64, " + new String(Base64.encodeBase64(file.getData()));
    }
}
