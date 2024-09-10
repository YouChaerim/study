package com.bookstore.bookstore_dto.service;

import com.bookstore.bookstore_dto.domain.*;
import com.bookstore.bookstore_dto.dto.book.BookResponseDto;
import com.bookstore.bookstore_dto.dto.order.OrderRequestDto;
import com.bookstore.bookstore_dto.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final CartRepository cartRepository;
    private final CardRepository cardRepository;
    private final AddressRepository addressRepository;
    private final BookService bookService;

    /**
     * 주문 등록 - 바로 주문하기
     */
    @Transactional
    public void placeOrder(OrderRequestDto orderRequestDto, int book_id, int quantity) throws Exception {
        var book = bookService.getDetailBook(book_id);
        if (book == null) {
            throw new RuntimeException("책을 찾을 수 없습니다.");
        }

        Orders order = new Orders();
        order.setPrice(book.getPrice() * quantity);
        order.setCard_number(orderRequestDto.getCard_number());
        order.setCard_company(cardRepository.findCardCompanyById(orderRequestDto.getCard_number()));
        order.setCard_valid(cardRepository.findCardValidityById(orderRequestDto.getCard_number()));
        order.setPostal_code(orderRequestDto.getPostal_code());
        List<Address> basicAddressList = addressRepository.findBasicAddressById(orderRequestDto.getPostal_code());
        List<Address> detailAddressList = addressRepository.findDetailAddressById(orderRequestDto.getPostal_code());

        if (!basicAddressList.isEmpty()) {
            order.setBasic_address(basicAddressList.get(0).getBasic_address());
        }
        if (!detailAddressList.isEmpty()) {
            order.setDetail_address(detailAddressList.get(0).getDetail_address());
        }

        order.setUser_id(orderRequestDto.getUser_id());

        log.info(order.getCard_company());
        log.info("Card_number: {}, Postal_code: {}, User_id: {}", order.getCard_number(), order.getPostal_code(), order.getUser_id());
        Orders savedOrder = orderRepository.saveOrder(order);
        placeOrderItem(savedOrder.getOrder_id(), book_id, quantity);
    }

    /**
     * 주문 항목 등록하기
     */
    public void placeOrderItem(int order_id, int book_id, int quantity) throws Exception {
        OrderItem orderItem = new OrderItem();
        orderItem.setBook_id(book_id);
        orderItem.setQuantity(quantity);
        orderItem.setOrder_id(order_id);
        orderRepository.saveOrderItem(orderItem);
    }

    /**
     * 주문 항목 등록 - 장바구니 주문하기
     */
//    @Transactional
//    public void placeCartOrder(OrderRequestDto orderRequestDto, String user_id) {
//
//    }
    public void placeCartOrder(OrderRequestDto orderRequestDto, String user_id) throws Exception {
        Cart cart = cartRepository.findByUserId(user_id);
        if (cart == null) {
            throw new RuntimeException("장바구니를 찾을 수 없습니다.");
        }

        List<CartItem> cartItems = cartRepository.findCartItemsByCartId(cart.getCart_id());
        if (cartItems.isEmpty()) {
            throw new RuntimeException("카트에 항목이 없습니다.");
        }

        Orders order = new Orders();
        order.setCard_number(orderRequestDto.getCard_number());
        order.setCard_company(cardRepository.findCardCompanyById(orderRequestDto.getCard_number()));
        order.setCard_valid(cardRepository.findCardValidityById(orderRequestDto.getCard_number()));
        order.setPostal_code(orderRequestDto.getPostal_code());

        List<Address> basicAddressList = addressRepository.findBasicAddressById(orderRequestDto.getPostal_code());
        List<Address> detailAddressList = addressRepository.findDetailAddressById(orderRequestDto.getPostal_code());

        if (!basicAddressList.isEmpty()) {
            order.setBasic_address(basicAddressList.get(0).getBasic_address());
        } else {
            throw new RuntimeException("기본 주소를 찾을 수 없습니다.");
        }

        if (!detailAddressList.isEmpty()) {
            order.setDetail_address(detailAddressList.get(0).getDetail_address());
        } else {
            throw new RuntimeException("상세 주소를 찾을 수 없습니다.");
        }

        order.setUser_id(orderRequestDto.getUser_id());

        int totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            BookResponseDto book = bookService.getDetailBook(cartItem.getBook_id());
            if (book == null) {
                throw new RuntimeException("책을 찾을 수 없습니다.");
            }
            totalPrice += cartItem.getQuantity() * book.getPrice();
        }

        order.setPrice(totalPrice);
        Orders savedOrder = orderRepository.saveOrder(order);

        for (CartItem cartItem : cartItems) {
            placeOrderItem(savedOrder.getOrder_id(), cartItem.getBook_id(), cartItem.getQuantity());
        }

        cartRepository.deleteCart(cart.getCart_id());
    }

    /**
     * 주문 목록 조회
     */
    public List<Map<String, Object>> findOrderItemsByUserId(String user_id) throws Exception {
        return orderRepository.findOrderItemsByUserId(user_id);
    }

}
