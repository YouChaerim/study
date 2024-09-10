package com.bookstore.bookstore_dto.repository;

import com.bookstore.bookstore_dto.domain.Card;
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
public class CardRepository {
    private final JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    /**
     * 카드 저장
     */
    public void saveCard(Card card, String user_id) {
        String sql = "insert into card (card_num, card_valid, card_company, user_id) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, card.getCard_num(), card.getCard_valid(), card.getCard_company(), user_id);
    }

    /**
     * 카드 목록 조회
     */
    public List<Card> findByUserId(String user_id) {
        String sql = "select card_num, card_valid, card_company from card where user_id = ?";
        return jdbcTemplate.query(sql, findRowMapper(), user_id);
    }

    public String findCardNumberById(String card_num) {
        String sql = "SELECT card_num FROM card WHERE card_num = ?";
        return jdbcTemplate.queryForObject(sql, String.class, card_num);
    }

    public String findCardCompanyById(String card_num) {
        String sql = "SELECT card_company FROM card WHERE card_num = ?";
        return jdbcTemplate.queryForObject(sql, String.class, card_num);
    }

    public String findCardValidityById(String card_num) {
        String sql = "SELECT card_valid FROM card WHERE card_num = ?";
        return jdbcTemplate.queryForObject(sql, String.class, card_num);
    }

    private RowMapper<Card> findRowMapper() {
        return (rs, rowNum) -> {
            Card card = new Card();
            card.setCard_num(rs.getString("card_num"));
            card.setCard_valid(rs.getString("card_valid"));
            card.setCard_company(rs.getString("card_company"));
            return card;
        };
    }
}
