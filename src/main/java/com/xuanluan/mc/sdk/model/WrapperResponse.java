package com.xuanluan.mc.sdk.model;

import lombok.*;

/**
 * @author Xuan Luan
 * @createdAt 11/12/2022
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WrapperResponse<T> {
    private String code;
    private String message;
    private T data;
}
