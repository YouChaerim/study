package com.bookstore.bookstore_dto.dto.coupon;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter @Setter
public class CouponResponseDto {
    private int user_coupon_id;
    private String user_id;
    private int coupon_id;
    private Date issued_at;
    private Date expires_at;
    private boolean used;
    private Date used_at;

    private BigDecimal discount_amount;
    private BigDecimal discount_percentage;
}
