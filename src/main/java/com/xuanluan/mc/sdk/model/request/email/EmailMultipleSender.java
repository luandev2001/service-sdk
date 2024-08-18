package com.xuanluan.mc.sdk.model.request.email;

import lombok.*;

import java.util.Set;

/**
 * @author Xuan Luan
 * @createdAt 2/2/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailMultipleSender {
    private Set<String> toEmails;
    private String messageBody;
    private String subject;
}
