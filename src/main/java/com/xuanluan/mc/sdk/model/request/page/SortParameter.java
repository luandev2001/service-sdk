package com.xuanluan.mc.sdk.model.request.page;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class SortParameter {
    private String name;
    private Sort.Direction direction = Sort.Direction.DESC;
}
