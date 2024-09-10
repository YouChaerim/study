package com.bookstore.bookstore_dto.dto.coupon;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class CouponRequestDto {
    private int coupon_id;
    private Date issued_at;
    private Date expires_at;
}
