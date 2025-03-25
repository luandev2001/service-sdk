package com.xuanluan.mc.sdk.model.request.page;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeywordParameter {
    private Set<String> names;
    private boolean prefix;
    private boolean suffix;
    private boolean ignoreCase;
}
