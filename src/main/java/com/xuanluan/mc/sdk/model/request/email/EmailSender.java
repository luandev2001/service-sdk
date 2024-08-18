package com.xuanluan.mc.sdk.model.request.email;

import lombok.*;

/**
 * @author Xuan Luan
 * @createdAt 2/2/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailSender {
    private String toEmail;
    private String messageBody;
    private String subject;
}
