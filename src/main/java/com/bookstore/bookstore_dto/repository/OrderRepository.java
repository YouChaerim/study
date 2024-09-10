package com.bookstore.bookstore_dto.repository;

import com.bookstore.bookstore_dto.domain.OrderItem;
import com.bookstore.bookstore_dto.domain.Orders;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
@AllArgsConstructor
public class OrderRepository {
    private final JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

//    /**
//     * 주문 ID로 특정 주문 조회
//     */
//    public Orders findByOrderId (int order_id) {
//        String sql = "select * from orders where order_id = ?";
//        List<Orders> query = jdbcTemplate.query(sql, findRowMapperOrder(), order_id);
//        return query.isEmpty() ? null : query.get(0);
//    }

    /**
     * user_id로 조회한 주문 중에 가장 최근에 insert된 order_id 조회
     */
    public Orders findByUserId(String user_id) {
        String sql = "select * from orders where user_id = ? order by order_date desc";
        List<Orders> query = jdbcTemplate.query(sql, findRowMapperOrder(), user_id);
        return query.isEmpty() ? null : query.get(0);
    }

    /**
     * 주문 생성
     */
    public Orders saveOrder(Orders order) {
        String sql = "insert into orders (price, card_number, card_company, card_valid, postal_code, basic_address, detail_address, order_date, user_id) values (?, ?, ?, ?, ?, ?, ?, now(), ?)";
        jdbcTemplate.update(sql, order.getPrice(), order.getCard_number(), order.getCard_company(), order.getCard_valid(),
                order.getPostal_code(), order.getBasic_address(), order.getDetail_address(), order.getUser_id());

        Orders savedOrder = findByUserId(order.getUser_id());

        // 디버깅 로그 추가
        if (savedOrder == null) {
            log.error("주문 저장 후 조회된 주문이 없습니다. user_id: {}", order.getUser_id());
        } else {
            log.info("저장된 주문: {}", savedOrder);
        }

        return savedOrder;
    }

    /**x
     * 주문 목록 조회
     */
    public List<Map<String, Object>>  findOrderItemsByUserId (String user_id) {
        String sql = "SELECT orderitem.order_id, orders.order_date, book.book_name, orderitem.quantity " +
                "FROM orderitem " +
                "JOIN orders ON orderitem.order_id = orders.order_id " +
                "JOIN book ON orderitem.book_id = book.book_id " +
                "WHERE orders.user_id = ? " +
                "ORDER BY orderitem.order_id ASC";
        return jdbcTemplate.queryForList(sql, user_id);
    }

    /**
     * 주문 항목 저장
     */
    public void saveOrderItem (OrderItem orderItem) {
        String sql = "INSERT INTO orderitem (order_id, book_id, quantity) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, orderItem.getOrder_id(), orderItem.getBook_id(), orderItem.getQuantity());
    }

    private RowMapper<Orders> findRowMapperOrder() {
        return (rs, rowNum) -> {
            Orders order = new Orders();
            order.setOrder_id(rs.getInt("order_id"));
            order.setPrice(rs.getInt("price"));
            order.setCard_number(rs.getString("card_number"));
            order.setCard_company(rs.getString("card_company"));
            order.setCard_valid(rs.getString("card_valid"));
            order.setPostal_code(rs.getInt("postal_code"));
            order.setBasic_address(rs.getString("basic_address"));
            order.setDetail_address(rs.getString("detail_address"));
            order.setOrder_date(rs.getTimestamp("order_date").toLocalDateTime());
            return order;
        };
    }

    private RowMapper<OrderItem> findRowMapperOrderItem() {
        return (rs, rowNum) -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderItem_id(rs.getInt("orderItem_id"));
            orderItem.setOrder_id(rs.getInt("order_id"));
            orderItem.setBook_id(rs.getInt("book_id"));
            orderItem.setQuantity(rs.getInt("quantity"));
            return orderItem;
        };
    }
}
