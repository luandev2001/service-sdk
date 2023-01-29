package com.xuanluan.mc.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    private String address1;
    private String address2;
    private String country;
    private String countryCode;
    private String province;
    private String provinceCode;
    private String district;
    private String districtCode;
    private String ward;
    private String wardCode;
}
