package com.bookstore.bookstore_dto.dto.cartitem;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemRequestDto {
    private int book_id;
    private int cart_id;
    private int quantity;
}
