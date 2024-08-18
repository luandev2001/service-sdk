package com.xuanluan.mc.sdk.model.request.email;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Xuan Luan
 * @createdAt 2/3/2023
 */
@Getter
@Builder
public class EmailSenderConfig {
    private final EmailConfig config;
    private final EmailSender sender;
}
