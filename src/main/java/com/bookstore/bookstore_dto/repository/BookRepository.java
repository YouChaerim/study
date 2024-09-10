package com.bookstore.bookstore_dto.repository;

import com.bookstore.bookstore_dto.domain.Book;
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
public class BookRepository {

    private final JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    /**
     * 책 목록 조회
     */
    public List<Book> findAll() {
        String sql = "SELECT * FROM book";
        return jdbcTemplate.query(sql, bookRowMapper());
    }

    /**
     * 책 상세페이지
     */
    public Book findById(int book_id) {
        String sql = "SELECT * FROM book WHERE book_id = ?";
        List<Book> books = jdbcTemplate.query(sql, bookRowMapper(), book_id);
        if (books.isEmpty()) {
            throw new RuntimeException("책을 찾을 수 없습니다. bookId: " + book_id);
        }
        return books.get(0);
    }

    private RowMapper<Book> bookRowMapper() {
        return (rs, rowNum) -> {
            Book book = new Book();
            book.setBook_id(rs.getInt("book_id"));
            book.setBook_name(rs.getString("book_name"));
            book.setStock(rs.getInt("stock"));
            book.setPrice(rs.getInt("price"));
            return book;
        };
    }
}
