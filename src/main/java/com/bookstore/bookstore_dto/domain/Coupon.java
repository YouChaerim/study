package com.bookstore.bookstore_dto.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Coupon {
    private int coupon_id;
    private String coupon_type;
    private BigDecimal discount_amount; //금액이나 비율과 같은 정밀한 숫자 계산을 할 때 사용됨
    private BigDecimal discount_percentage;
}
