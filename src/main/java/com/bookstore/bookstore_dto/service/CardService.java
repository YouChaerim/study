package com.bookstore.bookstore_dto.service;

import com.bookstore.bookstore_dto.domain.Card;
import com.bookstore.bookstore_dto.dto.card.CardRequestDto;
import com.bookstore.bookstore_dto.dto.card.CardResponseDto;
import com.bookstore.bookstore_dto.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    /**
     * 카드 목록 조회
     */
    public List<CardResponseDto> findAllCard(String user_id) throws SQLException {
        List<Card> card = cardRepository.findByUserId(user_id);
        List<CardResponseDto> cardResponseDtos = new ArrayList<>();
        for (int i=0; i < card.size(); i++) {
            CardResponseDto cardResponseDto = new CardResponseDto();
            cardResponseDto.setCard_num(card.get(i).getCard_num());
            cardResponseDto.setCard_valid(card.get(i).getCard_valid());
            cardResponseDto.setCard_company(card.get(i).getCard_company());
            cardResponseDto.setUser_id(user_id);
            cardResponseDtos.add(cardResponseDto);
        }
        return cardResponseDtos;
    }

    /**
     * 카드 등록하기
     */
    public void registerCard(CardRequestDto cardRequestDto, String user_id) throws SQLException {
        Card card = new Card();
        card.setCard_num(cardRequestDto.getCard_num());
        card.setCard_valid(cardRequestDto.getCard_valid());
        card.setCard_company(cardRequestDto.getCard_company());

        cardRepository.saveCard(card, user_id);
        log.info("카드가 성공적으로 등록되었습니다: {}", cardRequestDto);
    }
}
