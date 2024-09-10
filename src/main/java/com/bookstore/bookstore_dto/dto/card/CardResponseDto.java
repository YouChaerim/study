package com.bookstore.bookstore_dto.dto.card;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CardResponseDto {
    private String card_num;
    private String card_valid;
    private String card_company;
    private String user_id;
}
