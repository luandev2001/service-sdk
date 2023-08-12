package com.xuanluan.mc.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Entity
public class Configuration extends BaseEntity {
    @Column(nullable = false, updatable = false)
    private String clientId;
    @ElementCollection
    private Set<String> orgIds;
    @ElementCollection
    private Map<String, Object> value;
    @Column(nullable = false, updatable = false)
    private String name;
    @Column(nullable = false, updatable = false)
    private String type;
}
