package com.bookstore.bookstore_dto.dto.order;

import com.bookstore.bookstore_dto.domain.OrderItem;
import com.bookstore.bookstore_dto.domain.Orders;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
public class OrderRequestDto {
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
