package com.bookstore.bookstore_dto.service;

import com.bookstore.bookstore_dto.domain.Book;
import com.bookstore.bookstore_dto.dto.book.BookResponseDto;
import com.bookstore.bookstore_dto.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    /**
     * 책 목록 조회
     */
    public List<BookResponseDto> findAllBooks() throws SQLException {
        List<Book> books = bookRepository.findAll();
        List<BookResponseDto> bookResponseDtos = new ArrayList<>();
        for(int i = 0; i < books.size(); i++) {
            BookResponseDto bookResponseDto = new BookResponseDto();
            bookResponseDto.setBook_id(books.get(i).getBook_id());
            bookResponseDto.setBook_name(books.get(i).getBook_name());
            bookResponseDto.setPrice(books.get(i).getPrice());
            bookResponseDto.setStock(books.get(i).getStock());
            bookResponseDtos.add(bookResponseDto);
        }
        return bookResponseDtos;
    }

    /**
     * 책 상세페이지 조회
     */
    public BookResponseDto getDetailBook(int book_id) throws SQLException {
        Book book = bookRepository.findById(book_id);
        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setBook_id(book.getBook_id());
        bookResponseDto.setBook_name(book.getBook_name());
        bookResponseDto.setPrice(book.getPrice());
        bookResponseDto.setStock(book.getStock());
        return bookResponseDto;
    }
}
