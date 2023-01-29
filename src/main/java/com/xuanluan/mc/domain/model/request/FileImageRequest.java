package com.xuanluan.mc.domain.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Xuan Luan
 * @createdAt 12/20/2022
 */
@Getter
@ToString
@Builder
public class FileImageRequest {
    private final String type;
    private final String base64;
    private final String url;
}
