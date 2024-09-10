package com.bookstore.bookstore_dto.domain;

import com.bookstore.bookstore_dto.dto.cartitem.CartItemResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CartItem {
    private int cartItem_id;
    private int book_id;
    private int cart_id;
    private int quantity;
}
