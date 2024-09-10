package com.bookstore.bookstore_dto.controller;

import com.bookstore.bookstore_dto.domain.User;
import com.bookstore.bookstore_dto.dto.book.BookResponseDto;
import com.bookstore.bookstore_dto.service.BookService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    /**
     * list 페이지
     */
    @GetMapping(value = "/book/list")
    public String bookList(Model model, HttpSession session) {
        try {
            List<BookResponseDto> bookList = bookService.findAllBooks();
            model.addAttribute("bookList", bookList);

            User user = (User) session.getAttribute("userInfo");
            if (user != null) {
                model.addAttribute("user_id", user.getUser_id());
            } else {
                model.addAttribute("user_id", "");
            }

        } catch (Exception e){
            log.info("errorMessage", e);
            model.addAttribute("errorMessage", "책 목록을 불러오는 데 실패했습니다."); // 에러 메시지 추가
        }
        return "book/list";
    }

    /**
     * 책 상세페이지
     */
    @GetMapping(value = "/book/detail/{book_id}")
    public String getBookDetail(@PathVariable(name = "book_id") int book_id, Model model) {
        try {
            BookResponseDto bookResponseDto = bookService.getDetailBook(book_id);
            model.addAttribute("book", bookResponseDto);
            log.info("책 아이디: {}", bookResponseDto.getBook_id());
        } catch (Exception e) {
            log.info("errorMessage", e);
            model.addAttribute("errorMessage", "책의 상세 내용을 불러오는 데 실패했습니다.");
        }
        return "book/detail";
    }
}
