package com.xuanluan.mc.domain.model.request.email;

import com.xuanluan.mc.domain.validation.email.EmailCollection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * @author Xuan Luan
 * @createdAt 2/2/2023
 */
@Getter
@Setter
@Builder
public class EmailMultipleSender {
    @NotEmpty(message = "Danh sách email người nhận không được để trống")
    @EmailCollection
    private Set<String> toEmails;
    @NotBlank(message = "Nhập thông tin vào trường `nội dung`")
    private String messageBody;
    @NotBlank(message = "Nhập thông tin vào trường `chủ đề`")
    private String subject;
}
