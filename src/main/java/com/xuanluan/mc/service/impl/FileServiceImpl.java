package com.xuanluan.mc.service.impl;

import com.xuanluan.mc.domain.model.request.FileImageRequest;
import com.xuanluan.mc.domain.model.request.FileRequest;
import com.xuanluan.mc.exception.ServiceException;
import com.xuanluan.mc.utils.BaseStringUtils;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Xuan Luan
 * @createdAt 12/20/2022
 */
@Service
public class FileServiceImpl {
    private final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public FileRequest uploadFile(MultipartFile file) throws ServiceException {
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
        } catch (Exception e) {
            logger.error("Error upload file: " + e.getMessage(), e.getCause());
            throw new ServiceException(
                    HttpStatus.BAD_REQUEST,
                    "Error upload file",
                    "Có lỗi trong quá trình tải file lên"
            );
        }
    }

    public String showImage(FileImageRequest request) {
        Assert.notNull(request, "request must not be null!");
        if (BaseStringUtils.checkSuffixImage(request.getUrl())) {
            return request.getUrl();
        } else {
            assert request.getType() != null;
            assert request.getBase64() != null;
            return "data:" + request.getType() + ";base64, " + request.getBase64();
        }
    }
}
