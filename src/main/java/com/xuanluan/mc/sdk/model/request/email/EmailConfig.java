package com.xuanluan.mc.sdk.model.request.email;

import lombok.*;

/**
 * @author Xuan Luan
 * @createdAt 2/3/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailConfig {
    private boolean isConnectMail;
    private String hostMail;
    private String usernameMail;
    private String passwordMail;
    private Integer portMail;
    private String protocolMail;
}
