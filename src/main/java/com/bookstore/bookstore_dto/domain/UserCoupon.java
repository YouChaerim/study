package com.bookstore.bookstore_dto.domain;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class UserCoupon {
    private int user_coupon_id;
    private String user_id;
    private int coupon_id;
    private Date issued_at;
    private Date expires_at;
    private boolean used;
    private Date used_at;
}
