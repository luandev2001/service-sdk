package com.xuanluan.mc.sdk.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Xuan Luan
 * @createdAt 2/3/2023
 */
@Getter
@Setter
@Builder
public class EmailConfig {
    private boolean isConnectMail;
    private String hostMail;
    private String usernameMail;
    private String passwordMail;
    private Integer portMail;
    private String protocolMail;
}
