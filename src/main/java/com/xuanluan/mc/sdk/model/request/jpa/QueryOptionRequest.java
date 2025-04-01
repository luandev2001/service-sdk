package com.xuanluan.mc.sdk.model.request.jpa;

import lombok.*;
import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.LockModeType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryOptionRequest {
    private EntityGraph.EntityGraphType graphType;
    private String graphName;
    private LockModeType lockMode;
}
