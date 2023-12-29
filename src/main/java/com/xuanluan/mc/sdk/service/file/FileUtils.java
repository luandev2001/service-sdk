package com.xuanluan.mc.sdk.service.file;

import com.xuanluan.mc.sdk.domain.entity.FileStorage;
import com.xuanluan.mc.sdk.domain.model.request.FileRequest;
import com.xuanluan.mc.sdk.exception.ServiceException;
import com.xuanluan.mc.sdk.utils.StringUtils;
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
        Assert.notNull(file, "request must not null");
        Assert.isTrue(StringUtils.checkSuffixImage(file.getType()), "error.unsupported.file");
        return "data:" + file.getType() + ";base64, " + new String(Base64.encodeBase64(file.getData()));
    }
}
