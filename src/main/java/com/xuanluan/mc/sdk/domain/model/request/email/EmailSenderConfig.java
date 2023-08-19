package com.xuanluan.mc.sdk.domain.model.request.email;

import com.xuanluan.mc.sdk.domain.model.EmailConfig;
import lombok.Builder;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Xuan Luan
 * @createdAt 2/3/2023
 */
@Getter
@Builder
public class EmailSenderConfig {
    @NotNull(message = "Thông tin cấu hình không được để trống!")
    @Valid
    private final EmailConfig config;
    @NotNull(message = "Nội dung mail không được để trống!")
    @Valid
    private final EmailSender sender;
}
