package com.bookstore.bookstore_dto.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Orders {
    private int order_id;
    private int price;

    private String card_number;
    private String card_company;
    private String card_valid;

    private int postal_code;
    private String basic_address;
    private String detail_address;
    private LocalDateTime order_date; // 날짜와 시간을 모두 포함

    private String user_id;
}