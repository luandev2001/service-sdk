package com.xuanluan.mc.domain.model;

import lombok.*;
import org.springframework.http.HttpStatus;

/**
 * @author Xuan Luan
 * @createdAt 11/12/2022
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WrapperResponse<T> {
    private HttpStatus status;
    private String message;
    private T data;
    private int number;
}
