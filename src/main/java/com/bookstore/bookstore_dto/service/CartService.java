package com.bookstore.bookstore_dto.service;

import com.bookstore.bookstore_dto.domain.Book;
import com.bookstore.bookstore_dto.domain.Cart;
import com.bookstore.bookstore_dto.domain.CartItem;
import com.bookstore.bookstore_dto.domain.User;
import com.bookstore.bookstore_dto.dto.cartitem.CartItemResponseDto;
import com.bookstore.bookstore_dto.dto.user.UserResponseDto;
import com.bookstore.bookstore_dto.repository.BookRepository;
import com.bookstore.bookstore_dto.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final BookRepository bookRepository;

    /**
     * 장바구니 조회
     * request - Front에서 요청받는 것
     */
    public Cart getCartById(User user) {
        log.info("user: {}", user.toString());
        Cart cart = cartRepository.findByUserId(user.getUser_id());
        if (cart == null) {
            createCart(user.getUser_id());
            return cartRepository.findByUserId(user.getUser_id());
        }
        return cart;
    }

    /**
     * 장바구니 생성
     * - 프론트에서 요청받는 것 X -> request사용 X, 그냥 User 사용
     */
    public void createCart(String user_id) {
        cartRepository.saveCart(user_id);
    }

    /**
     * 장바구니 아이템 목록 조회
     */
    public List<CartItemResponseDto> findCartItems(Cart cart) {
        List<CartItem> cartItems = cartRepository.findCartItemsByCartId(cart.getCart_id());
        List<CartItemResponseDto> cartItemResponseDtos = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            Book book = bookRepository.findById(cartItem.getBook_id());
            if (book != null) {
                CartItemResponseDto cartItemResponseDto = CartItemResponseDto.toDto(cartItem, book, cart);

                cartItemResponseDtos.add(cartItemResponseDto);
            }
        }

        return cartItemResponseDtos;
    }

    /**
     * 장바구니 아이템 추가
     */
    public void addToCart(User user, int book_id) {
        Cart cart = getCartById(user);
        if (cart != null) {
            List<CartItem> cartItems = cartRepository.findCartItemsByCartId(cart.getCart_id());
            for (CartItem cartItem : cartItems) {
                if (cartItem.getBook_id() == book_id) {
                    cartRepository.updateCartItemQuantity(cart.getCart_id(), cartItem.getBook_id(), cartItem.getQuantity() + 1);
                    return;
                }
            }
        }
        cartRepository.addCartItem(cart.getCart_id(), book_id, 1);
    }

    /**
     * 책 수량 변경
     */
    public void updateCartItemQuantity(User user, int book_id, int quantity) {
        Cart cart = getCartById(user);
        if (cart != null) {
            cartRepository.updateCartItemQuantity(cart.getCart_id(), book_id, quantity);
        }
    }

    /**
     * 총 가격 계산
     */
    public long calculateTotalPrice(User user) {
        Cart cart = getCartById(user);
        long totalPrice = 0;

        if (cart != null) {
            List<CartItem> cartItems = cartRepository.findCartItemsByCartId(cart.getCart_id());
            for (CartItem cartItem : cartItems) {
                Book book = bookRepository.findById(cartItem.getBook_id());
                if (book != null) {
                    totalPrice += (long) book.getPrice() * cartItem.getQuantity();
                }
            }
        }
        return totalPrice;
    }
}
