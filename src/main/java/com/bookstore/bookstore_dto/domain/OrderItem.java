package com.bookstore.bookstore_dto.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class OrderItem {
    private int orderItem_id;
    private int order_id;
    private int book_id;
    private int quantity;
}
