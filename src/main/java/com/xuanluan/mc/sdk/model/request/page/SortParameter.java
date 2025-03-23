package com.xuanluan.mc.sdk.model.request.page;

import lombok.*;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SortParameter {
    private String name;
    private Sort.Direction direction = Sort.Direction.DESC;
}
