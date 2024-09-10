package com.bookstore.bookstore_dto.controller;

import com.bookstore.bookstore_dto.domain.Cart;
import com.bookstore.bookstore_dto.domain.OrderItem;
import com.bookstore.bookstore_dto.domain.User;
import com.bookstore.bookstore_dto.dto.book.BookResponseDto;
import com.bookstore.bookstore_dto.dto.cartitem.CartItemResponseDto;
import com.bookstore.bookstore_dto.dto.order.OrderRequestDto;
import com.bookstore.bookstore_dto.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;
    private final BookService bookService;
    private final AddressService addressService;
    private final CardService cardService;

    /**
     * 책 상세페이지에서 바로 주문하기
     */
    @GetMapping(value = "/order/order/{book_id}")
    public String orderForm(@PathVariable(name = "book_id") int book_id, @RequestParam("quantity") int quantity, Model model, HttpSession session) {
        try {
            log.info("book_id: {}", book_id);
            BookResponseDto bookResponseDto =  bookService.getDetailBook(book_id);
            log.info("bookResponseDto: {}", bookResponseDto);

            User user = (User) session.getAttribute("userInfo");

            model.addAttribute("book", bookResponseDto);
            model.addAttribute("addresses", addressService.findAllAddress(user.getUser_id()));
            model.addAttribute("cards", cardService.findAllCard(user.getUser_id()));
            model.addAttribute("quantity", quantity);
        } catch (Exception e) {
            log.info("errorMessage: ", e);
        }
        return "order/order";
    }

    @PostMapping(value = "/order/add")
    public String placeOrder(@ModelAttribute OrderRequestDto orderRequestDto,
                             @RequestParam(name = "book_id") int book_id,
                             @RequestParam(name = "quantity") int quantity,
                             HttpSession session) {

        User user = (User) session.getAttribute("userInfo");

        String cardNum = orderRequestDto.getCard_number();
        int postalCode = orderRequestDto.getPostal_code();

        log.info("주문 요청 정보: 카드 번호: {}, 우편 번호: {}, 사용자 ID: {}",
                cardNum, postalCode, user.getUser_id());

        orderRequestDto.setUser_id(user.getUser_id());

        try {
            orderService.placeOrder(orderRequestDto, book_id, quantity);
        } catch (Exception e) {
            log.info("errorMessage: ", e);
        }
        return "redirect:/book/list";
    }

    /**
     * 장바구니에서 주문하기
     */
    @GetMapping(value = "/order/cartorder")
    public String cartOrder(Model model, HttpSession session) {
        User user = (User) session.getAttribute("userInfo");

        Cart cart = cartService.getCartById(user);
        List<CartItemResponseDto> cartItemResponseDto = cartService.findCartItems(cart);

        try {
            model.addAttribute("cartItems", cartItemResponseDto);
            model.addAttribute("addresses", addressService.findAllAddress(user.getUser_id()));
            model.addAttribute("cards", cardService.findAllCard(user.getUser_id()));
            model.addAttribute("totalPrice", cartService.calculateTotalPrice(user));
        } catch (Exception e) {
            log.info("errorMessage: ", e);
        }
        return "order/cartorder";
    }

    @PostMapping(value = "/order/cartorder/add")
    public String placeCartOrder(@ModelAttribute OrderRequestDto orderRequestDto, HttpSession session) {
        User user = (User) session.getAttribute("userInfo");
        log.info("orderRequestDto: {}", orderRequestDto.toString());
        try {
            orderService.placeCartOrder(orderRequestDto, user.getUser_id());
        } catch (Exception e) {
            log.info("!!errorMessage: ", e);
        }
        return "redirect:/book/list";
    }

    /**
     * 주문 목록 조회
     */
    @GetMapping(value = "/order/orderlist")
    public String orderList(Model model, HttpSession session) {
        try {
            User user = (User) session.getAttribute("userInfo");
            List<Map<String, Object>> orderItems = orderService.findOrderItemsByUserId(user.getUser_id());
            model.addAttribute("orderItems", orderItems);
            log.info("orderItemList: {}", orderItems);
            return "order/orderlist";
        } catch (Exception e) {
            log.info("errorMessage: ", e);
        }
        return "order/orderlist";
    }
}
