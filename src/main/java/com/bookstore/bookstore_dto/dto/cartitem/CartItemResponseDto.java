package com.bookstore.bookstore_dto.dto.cartitem;

import com.bookstore.bookstore_dto.domain.Book;
import com.bookstore.bookstore_dto.domain.Cart;
import com.bookstore.bookstore_dto.domain.CartItem;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class CartItemResponseDto {
    private int cartItem_id;
    private int book_id;
    private int cart_id;
    private int quantity;
    private String book_name;
    private int stock;
    private int price;
    private int total_price;


    /**
     * static - 메모리에 자동으로 객체 등록
     */
    public static CartItemResponseDto toDto(CartItem cartItem, Book book, Cart cart) {
        CartItemResponseDto cartItemResponseDto = new CartItemResponseDto();
        cartItemResponseDto.setCartItem_id(cartItem.getCartItem_id());
        cartItemResponseDto.setBook_id(book.getBook_id());
        cartItemResponseDto.setCart_id(cart.getCart_id());
        cartItemResponseDto.setQuantity(cartItem.getQuantity());
        cartItemResponseDto.setBook_name(book.getBook_name());
        cartItemResponseDto.setStock(book.getStock());
        cartItemResponseDto.setPrice(book.getPrice());
        cartItemResponseDto.setTotal_price(book.getPrice() * cartItem.getQuantity());

        return cartItemResponseDto;
    }
}
