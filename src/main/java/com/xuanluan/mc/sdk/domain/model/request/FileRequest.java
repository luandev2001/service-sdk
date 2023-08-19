package com.xuanluan.mc.sdk.domain.model.request;


import lombok.*;

/**
 * @author Xuan Luan
 * @createdAt 12/18/2022
 */
@Getter
@ToString
@Builder
public class FileRequest {
    private final String name;
    private final String originalFile;
    private final String type;
    private final long size;
    private final String data; //type byte[]
}
