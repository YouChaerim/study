package com.bookstore.bookstore_dto.repository;

import com.bookstore.bookstore_dto.domain.Cart;
import com.bookstore.bookstore_dto.domain.CartItem;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class CartRepository {
    private final JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    /**
     *  장바구니 조회
     */
    public Cart findByUserId(String user_id) {
        String sql = "select * from cart where user_id = ?";
        List<Cart> query = jdbcTemplate.query(sql, findRowMapperCart(), user_id);
        return query.isEmpty() ? null : query.get(0);
    }

    /**
     * 장바구니 생성
     */
    public void saveCart(String user_id) {
        String sql = "insert into cart(user_id, create_date) values(?, now())";
        jdbcTemplate.update(sql, user_id);
    }

    /**
     * 장바구니 목록 조회
     */
    public List<CartItem> findCartItemsByCartId(int cart_id) {
        String sql = "select cartitem.* from cartitem inner join cart on cartitem.cart_id = cart.cart_id where cartItem.cart_id = ?";
        return jdbcTemplate.query(sql, findRowMapperCartItem(), cart_id);
    }

    /**
     * 장바구니에 아이템 추가
     */
    public void addCartItem(int cart_id, int book_id, int quantity) {
        String sql = "insert into cartitem(cart_id, book_id, quantity) values (?, ?, ?)";
        jdbcTemplate.update(sql, cart_id, book_id, quantity);
    }

    /**
     * 장바구니 아이템 수량 증가
     */
    public void updateCartItemQuantity (int cart_id, int book_id, int quantity) {
        String sql = "update cartitem set quantity = ? where cart_id = ? and book_id = ?";
        jdbcTemplate.update(sql, quantity, cart_id, book_id);
    }

    /**
     * 장바구니 삭제
     */
    public void deleteCart(int cart_id) {
        String sql = "DELETE FROM cart WHERE cart_id=?";
        jdbcTemplate.update(sql, cart_id);
    }

    private RowMapper<Cart> findRowMapperCart() {
        return (rs, rowNum) -> {
            Cart cart = new Cart();
            cart.setCart_id(rs.getInt("cart_id"));
            cart.setUser_id(rs.getString("user_id"));
            return cart;
        };
    }

    private RowMapper<CartItem> findRowMapperCartItem() {
        return (rs, rowNum) -> {
            CartItem cartItem = new CartItem();
            cartItem.setCartItem_id(rs.getInt("cartItem_id"));
            cartItem.setCart_id(rs.getInt("cart_id"));
            cartItem.setBook_id(rs.getInt("book_id"));
            cartItem.setQuantity(rs.getInt("quantity"));
            return cartItem;
        };
    }
}
