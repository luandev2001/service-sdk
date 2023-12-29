package com.xuanluan.mc.sdk.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JpaConverterException extends RuntimeException {
    private String from;
    private String to;

    public JpaConverterException(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public JpaConverterException(Throwable cause, String from, String to) {
        super(cause);
        this.from = from;
        this.to = to;
    }
}
