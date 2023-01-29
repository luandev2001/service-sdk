package com.xuanluan.mc.domain.model.logs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xuanluan.mc.domain.enums.ActivityLogType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.Date;

public class ActivityLog {
    @Id
    private String id;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createdAt;
    private String userId;
    private String requestId;
    private String requestType;
    @Enumerated(EnumType.STRING)
    private ActivityLogType type;
    private String description;
    private String partnerId;
}
