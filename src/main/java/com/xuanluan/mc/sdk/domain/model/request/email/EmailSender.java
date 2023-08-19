package com.xuanluan.mc.sdk.domain.model.request.email;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author Xuan Luan
 * @createdAt 2/2/2023
 */
@Getter
@Setter
@Builder
public class EmailSender {
    @NotBlank(message = "Người nhận không được để trống")
    @Email(message = "Đây không phải là địa chỉ email")
    private String toEmail;
    @NotBlank(message = "Nhập thông tin vào trường `nội dung`")
    private String messageBody;
    @NotBlank(message = "Nhập thông tin vào trường `chủ đề`")
    private String subject;
}
