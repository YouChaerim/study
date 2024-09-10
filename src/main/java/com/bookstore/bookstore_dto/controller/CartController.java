package com.bookstore.bookstore_dto.controller;

import com.bookstore.bookstore_dto.domain.Cart;
import com.bookstore.bookstore_dto.domain.User;
import com.bookstore.bookstore_dto.dto.cartitem.CartItemRequestDto;
import com.bookstore.bookstore_dto.dto.cartitem.CartItemResponseDto;
import com.bookstore.bookstore_dto.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;

    /**
     * 유저의 장바구니 검색
     */
    @GetMapping("/cart/create")
    public Cart getCart(HttpSession session) {
        User user = (User) session.getAttribute("userInfo");
        log.info("userId: {}", user);
        return cartService.getCartById(user);
    }

    /**
     * 장바구니 보기
     */
    @GetMapping("/cart/cart")
    public String listCartItems(HttpSession session, Model model) throws Exception {
        User user = (User) session.getAttribute("userInfo");
        if (user != null) {
            Cart cart = cartService.getCartById(user);
            List<CartItemResponseDto> cartItems = cartService.findCartItems(cart);
            long totalPrice = cartService.calculateTotalPrice(user);
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("totalPrice", totalPrice);
            return "cart/cart"; // 장바구니 목록 페이지
        }
        return "redirect:/users/signin"; // 로그인 페이지로 리다이렉트
    }

    /**
     * 장바구니에 책 추가
     */
    @PostMapping(value = "/cart/add")
    public ResponseEntity<?> addToCart(@RequestParam(name = "book_id") int book_id,
                                       HttpSession session) {
        User user = (User) session.getAttribute("userInfo");
        if (user != null) {
            cartService.addToCart(user, book_id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(401).build();
    }

    /**
     * 책의 수량 변경 및 가격 변경
     */
    @PostMapping(value = "/cart/updateQuantity")
    public String updateQuantity(HttpSession session, Model model, @ModelAttribute CartItemRequestDto cartItemRequestDto) throws Exception {
        User user = (User) session.getAttribute("userInfo");
        if (user != null) {
            cartService.updateCartItemQuantity(user, cartItemRequestDto.getBook_id(), cartItemRequestDto.getQuantity());

            Cart cart = cartService.getCartById(user);
            List<CartItemResponseDto> cartItems = cartService.findCartItems(cart);
            long totalPrice = cartService.calculateTotalPrice(user);

            model.addAttribute("cartItems", cartItems);
            model.addAttribute("totalPrice", totalPrice);
        }

        // cart/cart 페이지로 리다이렉트
        return "redirect:/cart/cart";
    }
}
