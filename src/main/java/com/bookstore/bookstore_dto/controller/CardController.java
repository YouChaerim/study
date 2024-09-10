package com.bookstore.bookstore_dto.controller;

import com.bookstore.bookstore_dto.domain.User;
import com.bookstore.bookstore_dto.dto.card.CardRequestDto;
import com.bookstore.bookstore_dto.dto.card.CardResponseDto;
import com.bookstore.bookstore_dto.service.CardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CardController {
    private final CardService cardService;

    /**
     * 카드 목록 페이지
     */
    @GetMapping(value = "/card/cardlist")
    public String cardList(Model model, HttpSession session) {
        try {
            User user = (User) session.getAttribute("userInfo");
            List<CardResponseDto> cardList = cardService.findAllCard(user.getUser_id());
            model.addAttribute("cardList", cardList);
        } catch (SQLException e) {
            log.info("errorMessage", e);
            model.addAttribute("errorMessage", "카드 목록을 불러오지 못했습니다.");
        }
        return "card/cardlist";
    }

    /**
     * 카드 등록 페이지
     */
    @GetMapping(value = "/card/cardregister")
    public String cardForm() { return  "card/cardregister"; }

    @PostMapping(value = "/card/cardregister")
    public String saveCard(@ModelAttribute CardRequestDto cardRequestDto, HttpSession session) {
        User user = (User) session.getAttribute("userInfo");
        try {
            cardService.registerCard(cardRequestDto, user.getUser_id());
        } catch (SQLException e) {
            log.info("errorMessage: ", e);
            return "redirect:/card/cardlist";
        }
        return "redirect:/card/cardlist";
    }
}
